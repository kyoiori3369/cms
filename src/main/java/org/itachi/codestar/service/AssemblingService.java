package org.itachi.codestar.service;

import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Assembling;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.PartConfiguration;
import org.itachi.codestar.domain.PlannedScheduling;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.JpaAssemblingRepository;
import org.itachi.codestar.repositories.jpa.JpaPartConfigurationRepository;
import org.itachi.codestar.repositories.jpa.JpaPlannedSchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备装配服务
 */
@Service
public class AssemblingService {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String PAGER = "pager";
    private static final String CONFIGURATION = "configs";
    private static final String ASSEMBLING = "assemblings";
    private static final String TESTDEVICES = "testDevices";
    private static final String ENTRUCKINGS = "entruckings";

    @Autowired
    private JpaPlannedSchedulingRepository repository;
    @Autowired
    private JpaPartConfigurationRepository patrepository;
    @Autowired
    private JpaAssemblingRepository assemblingRepository;
    @Autowired
    private PartMapper partMapper;

    public Map<String, Object> assemblingPages(String status, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
          if(StringUtils.isBlank(status)){
             pager.setTotal((int) assemblingRepository.count());
          }else{
             pager.setTotal((int) assemblingRepository.countByStatus(status));
          }
            map.put("status",status);
            map.put("offset", (pager.getPage() - 1) * pager.getRows());
            map.put("rows", pager.getRows());
            List<Assembling> assembling = partMapper.findAssembling(map);
            map.put(ASSEMBLING, assembling);
            map.put(PAGER, pager);
            return map;
    }

    public Map<String, Object> findTestDevicd(String status, String orderNumber,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("orderNumber",orderNumber);
        int count = partMapper.countTestDevice(map);
        pager.setTotal(count);
        map.put("status",status);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Assembling> assembling = partMapper.findTestDevicd(map);
        map.put(TESTDEVICES, assembling);
        map.put(PAGER, pager);
        return map;
    }

    /**
     * 设备零件查询
     * @param partName
     * @param pager
     * @return
     */
    public Map<String, Object> partsPages(String partName, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(partName)) {
            pager.setTotal((int) patrepository.count());
            map.put(CONFIGURATION, patrepository.findAll(PageRequest.of(pager.getPage() - 1, pager.getRows())).getContent());
        } else {
            String key = partName.trim();
            pager.setTotal((int) patrepository.countByPartName(key));
            Page<PartConfiguration> configurations = patrepository.findByPartName(key, PageRequest.of(pager.getPage() - 1, pager.getRows()));
            map.put(CONFIGURATION, configurations.getContent());
        }
        map.put(PAGER, pager);
        return map;
    }

    /**
     * 装配操作更新
     * @param ids
     * @throws Exception
     */
    public void updateCompleteModify(List<Long> ids,Long id) throws Exception{
        partMapper.updateCompleteModifyIdIn(ids);
        Assembling pl = assemblingRepository.getOne(id);
        int i = repository.countByPlanDeviceIdAndAmStatusAndAssemblingStatusIsNot(pl.getPlanDeviceId(),0,-1);
        if (i<=0) {
            pl.setStatus("1");
        }
        pl.setAssemblyTime(df.format(new Date()));
        assemblingRepository.save(pl);
    }
    @Transactional(rollbackOn = Throwable.class)
    public void updateComplete(List<Long> ids) throws ServiceException {
        for (Long vi :ids){
            Assembling pl = assemblingRepository.getOne(vi);
            if(!"1".equals(pl.getStatus())){
                throw new ServiceException(ServiceError.Error.CHILD_ASSEMBLING);
            }
            pl.setStatus("2");
            assemblingRepository.save(pl);
        }
    }
    /**
     * 装配明细
     */
    public Map<String, Object> detailedPages(String planDeviceId,String partName,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if(StringUtils.isBlank("planDeviceId")){
            return null;
        }
        map.put("planDeviceId",planDeviceId);
        map.put("partName",partName);
        pager.setTotal(partMapper.findAssemblingPlanDeviceIdCount(map));
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<PlannedScheduling> plannedSchedulings = partMapper.findAssemblingPlanDeviceId(map);
        map.put("schedulings", plannedSchedulings);
        map.put(PAGER, pager);
        return map;
    }

    public void testDevicesSave(Assembling assembling)throws Exception {
        String da = df.format(new Date());
        assembling.setLoadingTime(df.format(new Date()));
        Integer number = loadingNumber(da);
        assembling.setIncrement(number);
        assembling.setLoadingNumber("zc"+ number);
        assemblingRepository.save(assembling);
    }

    public Integer loadingNumber(String da) {
        Integer ii = partMapper.findLoadingNumber(da);
        if(ii==null||ii<=0){
            String d = dateFormat.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }

    public Assembling assembling(Long id) throws ServiceException {
        return assemblingRepository.getOne(id);
    }

    public void modify(Assembling assembling) {
        assemblingRepository.save(assembling);
    }

/*    public Map<String,Object> entrucking(@Valid List<Long> ids, Pager pager) {

        Map<String, Object> map = new HashMap<>(16);
        if(ids.size() < 0){
            pager.setTotal((int) assemblingRepository.count());
        }else{
            pager.setTotal((int) assemblingRepository.countByIdIn(ids));
        }
        map.put("ids",ids);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Assembling> assembling = partMapper.findIds(map);
        map.put(ENTRUCKINGS, assembling);
        map.put(PAGER, pager);
        return map;
}*/
}
