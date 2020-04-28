package org.itachi.codestar.service;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.*;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.DeviceMapper;
import org.itachi.codestar.mapper.OrderMapper;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.*;
import org.itachi.codestar.sql.SqlConstants;
import org.itachi.codestar.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *计划排期管理
 * @author itachi
 * @since 2018/3/16 00:06
 */
@Service
public class PlannedSchedulingService {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat df_ = new SimpleDateFormat("yyyyMMdd");
    private static final String PAGER = "pager";
    private static final String SCHEDULING = "schedulings";
    @Autowired
    private JpaPlannedSchedulingRepository repository;
    @Autowired
    private JpaOrderDeviceRepository deviceRepository;
    @Autowired
    private JpaPlanDeviceRepository planDeviceRepository;
    @Autowired
    private JpaPartConfigurationRepository partConfigurationRepository;
    @Autowired
    private JpaAssemblingRepository assemblingRepository;
    @Autowired
    private JpaHistoricalRecordRepository repositoryHis;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DeviceMapper mapper;
    @Autowired
    private OrderMapper orderManage;
    @Autowired
    private PartMapper partMapper;

    /**
     * @param orderNumber
     * @param pager
     * @return
     * @throws Exception
     */
    public Map<String, Object> planMage(String orderNumber,Pager pager)throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        //查询搜索条数
        map.put("orderNumber",orderNumber);
        int count = mapper.countPlanManage(map);
        pager.setTotal(count);
        //查询搜索数据
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put("orderDevices", mapper.findPlanManage(map));
        map.put("pager", pager);
        return map;
    }
    /**
     * 排期零件查询
     * @param pager
     * @return
     */
    public Map<String, Object> schedulingPages(String id,String partName, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
       if (StringUtils.isBlank(id)) {
               return null;
       }
        pager.setTotal((int) repository.countByPlanDeviceId(Long.valueOf(id)));
        map.put("planDeviceId",id);
        map.put("partName",partName);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<PlannedScheduling> planned = partMapper.findPlanDeviceId(map);
        map.put(SCHEDULING, planned);
        map.put(PAGER, pager);
        return map;
    }
    /**
     * 出库零件查询
     * @param pager
     * @return
     */
    public Map<String, Object> outConfigPages(String id, String partName,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(id)) {
            return null;
        }
         map.put("planDeviceId",id);
         map.put("partName",partName);
         pager.setTotal(partMapper.findOutPlanDeviceIdCount(map));
         map.put("offset", (pager.getPage() - 1) * pager.getRows());
         map.put("rows", pager.getRows());
         List<PlannedScheduling> ps = partMapper.findOutPlanDeviceId(map);
         map.put(SCHEDULING, ps);
         map.put(PAGER, pager);
         return map;
    }
    /**
     *根据id批量删除
     * @param ids
     */
    @Transactional(rollbackOn = Throwable.class)
    public void delete(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }
    /**
     * 计划排期创建零件
     * @param plannedScheduling
     */
    public void create(PlannedScheduling plannedScheduling){
        PlanDevice planDevice = planDeviceRepository.getOne(plannedScheduling.getPlanDeviceId());
        repository.save(plannedScheduling);
        if(planDevice.getStatus().equals("1")){
            this.updatePart(plannedScheduling);
        }
    }

    public PlannedScheduling planScheduling(Long id) throws ServiceException {
        Optional<PlannedScheduling> config = repository.findById(id);
        if (config.isPresent()) {
            return config.get();
        }
        throw new ServiceException(501, "设备信息不存在！");
    }
    /**
     * 确定排期
     */
    @Transactional(rollbackOn = Throwable.class)
    public void updatePlanStatus(List<Long> id){
        mapper.updatePlanStatus(id);
        for (Long v : id){
            List<PlannedScheduling> planDevice =  repository.findByPlanDeviceId(v);
            for(PlannedScheduling p : planDevice){
                this.updatePart(p);
            }
            this.updateOrderDevice(v);
        }
    }
    private void updatePart(PlannedScheduling p){
        PartConfiguration  pa=  partConfigurationRepository.getOne(p.getPartId());
        pa.setNumberParts(pa.getNumberParts() - p.getNumberParts());
        partConfigurationRepository.save(pa);
    }
    //修改设备状态
    public void updateOrderDevice(Long id){
        PlanDevice planDevice = planDeviceRepository.getOne(id);
        int count = planDeviceRepository.countByPlanDeviceIdAndStatus(planDevice.getPlanDeviceId(),"0");
        if(count > 0){
            return;
        }
        mapper.updateStatus(planDevice.getPlanDeviceId());
        this.updateOrder(planDevice.getOrderId());
    }
    //修改订单状态
    public void updateOrder(Long id){
        int count = deviceRepository.countByOrderIdAndStatusAndDeleted(id,0,0);
        if(count > 0){
            return;
        }
        orderManage.updateOrderStatus(id,"1");
    }
    /**
     * 计划管理订单撤销
     * @param ids
     * @throws Exception
     */
    public void modify(List<Long> ids) throws Exception{
        for(Long v : ids){
            PlanDevice op= planDeviceRepository.getOne(v);
            op.setStatus("2");
            planDeviceRepository.save(op);
            OrderDevice od = deviceRepository.getOne(op.getPlanDeviceId());
            if(od.getCount() - 1 <= 0){
                od.setDeleted(1);
            }
            od.setCount(od.getCount() - 1);
            deviceRepository.save(od);
            this.updateOrder(od.getOrderId());
        }
     }
     public void outModify(List<Long> ids,Long id) throws Exception{
        int count = assemblingRepository.countByPlanDeviceId(id);
        PlanDevice planDevice = planDeviceRepository.getOne(id);
        boolean flag = false;
        for(Long l : ids){
            PlannedScheduling plannedScheduling =  repository.getOne(l);
            PartConfiguration op = partConfigurationRepository.getOne(plannedScheduling.getPartId());
            if(op.getNumberParts()<0){
                flag = true;
                continue;
            }
            partMapper.updateAssemblingStatusById(l);
            this.historicalRecord(op,planDevice,plannedScheduling.getNumberParts());
            if(count > 0){
                int st = assemblingRepository.countByPlanDeviceIdAndStatus(id,"0");
                if(st > 0){
                    partMapper.updateAssemblingStatusSingle(l);
                }else{
                    partMapper.updateAssemblingStatusDelSingle(l);
                }
            }else {
                this.addAssembling(planDevice);
                partMapper.updateAssemblingStatusSingle(l);
            }
         }
         if(flag){
            throw new ServiceException(ServiceError.Error.PART_STATUS);
         }
         int i = repository.countByPlanDeviceIdAndIsStatus(id,0);
         if(i==0){
            planDevice.setOutStatus("1");
            planDeviceRepository.save(planDevice);
         }
    }

    //生成装配任务
    public void addAssembling(PlanDevice planDevice){
        String time = df.format(new Date());
        Assembling ab = new Assembling();
        ab.setDeviceId(planDevice.getDeviceId());
        ab.setOrderId(planDevice.getOrderId());
        ab.setPurchaseTime(planDevice.getPlanTime());
        ab.setOutTime(time);
        ab.setPlanCode(planDevice.getPlanCode());
        Integer number =  orderNumber(time);
        ab.setIncrement(number);
        ab.setStatus(Constants.INDEX_ZERO);
        ab.setOutCode("C"+number);
        ab.setPlanDeviceId(planDevice.getId());
        assemblingRepository.save(ab);
    }
    //生成出库编号
    public Integer orderNumber(String da) {
        Integer ii =  partMapper.findOutNumber(da);
        if(ii==null||ii<=0){
            String d = df_.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }

    //存入历史记录
    public void historicalRecord(PartConfiguration op,PlanDevice planDevice,int count) throws ServiceException {
            HistoricalRecord or = new HistoricalRecord();
            or.setPartCode(op.getPartCode());
            or.setPartName(op.getPartName());
            or.setPartBrand(op.getPartBrand());
            or.setProcedureStatus(op.getPartSource());
            or.setRuleModel(op.getRuleModel());
            or.setPartId(op.getId());
            or.setStatus("0");
            or.setPartPrice(op.getPartPrice());
            or.setOutAndEndTime(df.format(new Date()));
            or.setNumber(String.valueOf(count));
            or.setPlanCode(planDevice.getPlanCode());
            repositoryHis.save(or);
     }

    /**
     *计划确定库存量减少
     * @param warehouseId
     * @throws Exception
     */
    public void determine(Long warehouseId) throws Exception {
        if (warehouseId != null) {
            Warehouse warehouse = warehouseService.findWarehouseOne(warehouseId);
            String count = warehouse.getWarehouseCount();
            if (StringUtils.isNotBlank(count)) {
                Integer i = Integer.valueOf(count);
                if (i > 0) {
                    warehouse.setWarehouseCount(String.valueOf(i - 1));
                    warehouseService.modifySave(warehouse);
                }
            }
        }
    }

    /**
     * 订单变更信息
     * @param orderDevice
     * @return
     * @throws Exception
     */
    public OrderDevice modifySave(OrderDevice orderDevice) throws Exception {
        deviceRepository.save(orderDevice);
        return orderDevice;
    }
     //更新零件状态
    public void updateStatus(int status,Long id)throws ServiceException{
        PlannedScheduling ps= repository.getOne(id);
        if(ps.getStatus()==1 || ps.getStatus()==2){
            throw new ServiceException(ServiceError.Error.CHILD_PART_STATUS);
        }
        if(status==1 || status==2){
            PartConfiguration pa = partConfigurationRepository.getOne(ps.getPartId());
            pa.setNumberParts(pa.getNumberParts()+ps.getNumberParts());
            partConfigurationRepository.save(pa);
            if(ps.getIsStatus() !=1){
                ps.setOutStatus(1);
                ps.setAssemblingStatus(-1);
            }
        }
        ps.setStatus(status);
        repository.save(ps);
    }
}
