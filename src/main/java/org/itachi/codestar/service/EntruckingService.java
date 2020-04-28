package org.itachi.codestar.service;

import org.itachi.codestar.domain.Assembling;
import org.itachi.codestar.domain.Entrucking;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.OrderMapper;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.*;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 装车发货逻辑
 * Created by kyo on 2018/3/18.
 * @author zhuzhidong
 */
@Service
public class EntruckingService {

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String PAGER = "pager";
    private static final String ENTRUCKINGS = "entruckings";

    @Autowired
    private PartMapper mapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private JpaEntruckingRepository repository;

    @Autowired
    private JpaAssemblingRepository assemblingRepository;
    @Autowired
    private JpaPlanDeviceRepository planDeviceRepository;
    @Autowired
    private JpaOrderDeviceRepository deviceRepository;
    @Autowired
    private JpaOrdersRepository orderRepository;

    /**
     * 装车保存数据
     * @param ids
     * @return
     */
    public String save(List<Long> ids) {
        String da = df.format(new Date());
        Integer number = loadingNumber(da);
        String loadingNumber = "zc"+ number;
        for(Long v :ids){
            Assembling a = assemblingRepository.getOne(v);
            Entrucking e = new Entrucking();
            e.setDeviceId(a.getDeviceId());
            e.setOrderId(a.getOrderId());
            e.setLoadingTime(da);
            e.setStorageTime(da);
            e.setIncrement(number);
            e.setPlanCode(a.getPlanCode());
            e.setAssemblingId(a.getId());
            e.setLoadingNumber(loadingNumber);
            repository.save(e);
        }
        return loadingNumber;
    }

    /**
     * 装车发货单查询
     * @param loadingNumber
     * @param pager
     * @return
     */
    public Map<String,Object> entrucking(String loadingNumber,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("loadingNumber",loadingNumber);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Entrucking> entrucking = mapper.findIds(map);
        map.put(ENTRUCKINGS, entrucking);
        map.put(PAGER, pager);
        return map;
    }

    public Integer loadingNumber(String da) {
        Integer ii = mapper.findLoadingNumber(da);
        if(ii==null||ii<=0){
            String d = df.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void delete(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void create(Entrucking entrucking) throws Exception {
        repository.save(entrucking);
    }

    public Entrucking entrucking(Long id) throws ServiceException {
        return repository.getOne(id);
    }

    public void modify(Entrucking entrucking) throws Exception {
        repository.save(entrucking);
    }


    /**
     * 确认装车发货
     * @param loadingNumber
     * @return
     */
    public void entruckingDone(String loadingNumber) {
        //根据装车单号查询装车单据信息
        List<Entrucking> entruckingList = repository.findByLoadingNumber(loadingNumber);
        String loadingTime = SDF_DATETIME.format(new Date());
        for(Entrucking entrucking:entruckingList){
            /******更新装车信息******/
            //装车发货已完成
            entrucking.setStatus(1);
            //2018-03-25 15:26:43
            entrucking.setLoadingTime(loadingTime);
            repository.save(entrucking);

            /******更新装配信息******/
            //装配id
            Long assemblingId = entrucking.getAssemblingId();
            //通过装配id获取装配信息
            Assembling assembling = assemblingRepository.getOne(assemblingId);
            //发车状态
            assembling.setLoadingState(1);
            assembling.setLoadingTime(loadingTime);
            assembling.setLoadingNumber(loadingNumber);
            assemblingRepository.save(assembling);
            int ii = orderRepository.countByIdAndOrderStatus(assembling.getOrderId(),0);
            if(ii > 0){
                continue;
            }
            int li = assemblingRepository.countByOrderIdAndLoadingState(assembling.getOrderId(), 0);
            if(li > 0){
                continue;
            }
            orderMapper.updateOrderStatus(assembling.getOrderId(),"2");
        }
    }

//
//    public Map<String,Object> repair(@Valid String orderNumber, Pager pager) {
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("orderNumber",orderNumber);
//        map.put("offset", (pager.getPage() - 1) * pager.getRows());
//        map.put("rows", pager.getRows());
//        List<Entrucking> entrucking = mapper.findRepair(map);
//        map.put(ENTRUCKINGS, entrucking);
//        map.put(PAGER, pager);
//        return map;
//    }
}

