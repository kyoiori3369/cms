package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.*;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.DeviceMapper;
import org.itachi.codestar.mapper.OrderMapper;
import org.itachi.codestar.mapper.PurchaseMapper;
import org.itachi.codestar.repositories.jpa.JpaDeviceStandardRepository;
import org.itachi.codestar.repositories.jpa.JpaOrderDeviceRepository;
import org.itachi.codestar.repositories.jpa.JpaPlanDeviceRepository;
import org.itachi.codestar.repositories.jpa.JpaPlannedSchedulingRepository;
import org.itachi.codestar.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:54
 *
 * @author itachi
 */
@Service
@Slf4j
public class OrderDeviceService {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat df_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private JpaOrderDeviceRepository repository;
    @Autowired
    private JpaPlanDeviceRepository repositoryPlan;
    @Autowired
    private JpaDeviceStandardRepository standardRepository;
    @Autowired
    private JpaPlannedSchedulingRepository plannedSchedulingRepository;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;


    public Map<String, Object> findManageService(String orderId, Pager pager) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(orderId)) {
            return null;
        }
        pager.setTotal(repository.countByOrderIdAndDeleted(Long.valueOf(orderId),0));
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put("orderId",orderId);
        List<OrderDevice> service = deviceMapper.findOrderManage(map);
        map.put("orderDevices", service);
        map.put("pager", pager);
        return map;
    }

    /**
     * 查询出货订单
     * @param orderNumber
     * @param pager
     * @return
     * @throws Exception
     */
    public Map<String, Object> findManageShipmentService(String orderNumber,Pager pager)throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(orderNumber)) {
            pager.setTotal((int) repository.count());
        } else {
            pager.setTotal(repository.countByOrderId(Long.valueOf(orderNumber)));
        }
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put("status",1);
        map.put("orderDevices", deviceMapper.findPlanManageOut(map));
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findTestDevicd(String orderNumber,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("orderNumber",orderNumber);
        int count = deviceMapper.countOrder(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put("status",1);
        List<PlanDevice> assembling = deviceMapper.findPlanManageOut(map);
        map.put("orderDevices", assembling);
        map.put("pager", pager);
        return map;
    }
    /**
     * 生成计划
     */
    public void addPlan(OrderDevice orderDevice){
        PlanDevice pa = new PlanDevice();
        String da = df_.format(new Date());
        pa.setPlanDeviceId(orderDevice.getId());
        Integer integer = orderNumber(da);
        pa.setIncrement(integer);
        pa.setPlanCode("J"+integer);
        pa.setOrderId(orderDevice.getOrderId());
        pa.setDeviceId(orderDevice.getDeviceId());
        pa.setCreateTime(da);
        pa.setStatus(Constants.INDEX_ZERO);
        pa.setOutStatus(Constants.INDEX_ZERO);
        repositoryPlan.save(pa);
        this.addDeviceStandard(orderDevice.getDeviceId(),pa.getId());
    }

    /**
     * 添加排期计划零件
     */
    public void addDeviceStandard(Long deviceId,Long id){
        List<DeviceStandard> ss = standardRepository.findByDeviceId(deviceId);
        for(DeviceStandard vi : ss){
            PlannedScheduling pl = new PlannedScheduling();
            pl.setPlanDeviceId(id);
            pl.setPartId(vi.getPartId());
            pl.setNumberParts(vi.getPartCount());
            pl.setRemarks(vi.getPartRemarks());
            plannedSchedulingRepository.save(pl);
        }
    }

    /**
     * 创建设备跟订单
     *
     * @param orderDevice
     */
    public void customerSave(OrderDevice orderDevice) throws Exception {
          repository.save(orderDevice);
          for(int i = 0 ; i < orderDevice.getCount();i++){
              this.addPlan(orderDevice);
          };
        orderMapper.updateOrderDate(orderDevice.getOrderId());
    }
    //生成订单编号
    public Integer orderNumber(String da) {
        Integer ii =  deviceMapper.findPlanNumber(da);
        if(ii==null||ii<=0){
            String d = df.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }
    /**
     * 设备更新
     *
     * @param orderDevice
     */
    public void modifySave(OrderDevice orderDevice) throws Exception {
        OrderDevice or = repository.getOne(orderDevice.getId());
        int ii =orderDevice.getCount() - or.getCount();
        if(ii > 0)
        {
            for(int p = 0 ; p < ii ; p++){
                addPlan(orderDevice);
            }
        }else if(ii < 0){
           int count= repositoryPlan.countByPlanDeviceIdAndStatus(orderDevice.getId(),"0");
           int k = (ii*(-1));
           int y = count - k;
           if(y >= 0){
               for(int i = 0 ; i < k ; i++){
                   PlanDevice planDevice = deviceMapper.fidPlanOne(orderDevice.getId());
                   planDevice.setStatus("2");
                   repositoryPlan.save(planDevice);
               }
           } else {
               throw new ServiceException(ServiceError.Error.CHILD_PLAN);
           }
        }
        if(orderDevice.getDeviceId()!=or.getDeviceId()){
            int countDevice= repositoryPlan.countByPlanDeviceIdAndStatus(orderDevice.getId(),"1");
            if(countDevice==0){
                deviceMapper.updateDeviceId(orderDevice.getDeviceId(),orderDevice.getId());
                List<PlanDevice>  planDevice =repositoryPlan.findByPlanDeviceIdAndStatus(orderDevice.getId(),"0");
                for (PlanDevice vi:planDevice){
                    purchaseMapper.deleteByPlanDeviceId(vi.getId());
                   this.addDeviceStandard(orderDevice.getDeviceId(),vi.getId());
                }
            }else {
               throw new ServiceException(ServiceError.Error.CHILD_DEVICE_STATUS);
            }
        }
        repository.save(orderDevice);


    }

    /**
     * 批量删除客户
     *
     * @param ids
     */
    public void customerDelete(List<Long> ids) throws Exception {
        repository.deleteByIdIn(ids);
        deviceMapper.updatePlan(ids);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    public void deleteById(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * 根据ID查询
     *
     * @param id
     */
    public OrderDevice findCustomerOne(Long id) throws ServiceException {
        OrderDevice orderDevice = repository.getOne(id);
        if (orderDevice != null) {
            return orderDevice;
        }
        throw new ServiceException(ServiceError.Error.DATA_ONE);
    }

}
