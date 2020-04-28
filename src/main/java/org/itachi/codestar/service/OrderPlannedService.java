package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.*;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PurchaseMapper;
import org.itachi.codestar.repositories.jpa.*;
import org.itachi.codestar.util.Constants;
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
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:54
 *
 * @author itachi
 */
@Service
@Slf4j
public class OrderPlannedService {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private JpaOrderPlannedRepository repository;
    @Autowired
    private JpaHistoricalRecordRepository repositoryHis;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private JpaPurchaseManageRepository purchaseManageRepository;
    @Autowired
    private JpaPartConfigurationRepository configurationRepository;
    @Autowired
    private JpaPurchasePartsRepository purchasePartsRepository;


    public Map<String, Object> findManageService(String planId, Pager pager) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(planId)) {
            return null;
        }
        map.put("planId",planId);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<OrderPlanned> debuggingPager = purchaseMapper.findOrderPlanned(map);
        map.put("orderPlanneds", debuggingPager);
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findPurchasePart(String planId,String status, Pager pager) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(planId)) {
            return null;
        }
        if (StringUtils.isBlank(status)) {
            pager.setTotal((int) purchasePartsRepository.countByPlanId(Long.valueOf(planId)));
        }
        map.put("planId", planId);
        map.put("purchaseStatus", status);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<PurchaseParts> debuggingPager = purchaseMapper.findOrderPlannedDetail(map);
        map.put("orderPlanneds", debuggingPager);
        map.put("pager", pager);
        return map;
    }


    /**
     * 创建采购单
     *
     * @param warehouse
     */
    public void plannedSave(OrderPlanned warehouse) throws Exception {
        repository.save(warehouse);
    }

    /**
     * 更新建采购单
     *
     * @param warehouse
     */
    public void modifySave(OrderPlanned warehouse) throws Exception {
        repository.save(warehouse);
    }

    /**
     * 批量删除临时计划单
     *
     * @param ids
     */
    public void warehouseDelete(List<Long> ids) throws Exception {
        repository.deleteByIdIn(ids);
    }
    /**
     * 批量删除采购单
     *
     * @param ids
     */
    public void purchasePartDelete(List<Long> ids,Long id) throws Exception {
        purchasePartsRepository.deleteByIdIn(ids);
        this.updatePurchase(id);
        this.updateIsStatus(id);
    }

    public void updatePurchase(List<Long> ids,Long id) throws Exception {
        purchaseMapper.updatePurchasePart(ids);
        this.updatePurchase(id);
    }
    public void updatePurchase(Long id){
        int ii = purchasePartsRepository.countByPlanIdAndPurchaseStatus(id,"0");
        if(ii>0){
            return;
        }
        PurchaseManage op= purchaseManageRepository.getOne(id);
        op.setOrderStatus(Constants.INDEX_ONE);
        op.setOrderUpdateTime(df.format(new Date()));
        purchaseManageRepository.save(op);
    }
    //入库操作
    public void updateCount (List<Long> ids) throws Exception {
        Long p = 0l;
        for(Long l :ids){
           PurchaseParts op =  purchasePartsRepository.getOne(l);
           p = op.getPlanId();
           PartConfiguration pa =configurationRepository.getOne(op.getPartId());
           pa.setNumberParts(pa.getNumberParts() + op.getPurchaseCount());
           op.setIntStatus(1);
           purchasePartsRepository.save(op);
           configurationRepository.save(pa);
           historicalRecord(pa,op);
         }
       this.updateIsStatus(p);
    }
    private void updateIsStatus(Long id){
        int ii =  purchasePartsRepository.countByPlanIdAndPurchaseStatus(id,Constants.INDEX_ZERO);
        if(ii>0){
            return;
        }
        purchaseMapper.updateIsStatus(id);
    }
    //存入历史记录
    public void historicalRecord(PartConfiguration op,PurchaseParts orp) throws Exception {
        HistoricalRecord or = new HistoricalRecord();
             or.setPartCode(op.getPartCode());
             or.setPartName(op.getPartName());
             or.setPartBrand(op.getPartBrand());
             or.setProcedureStatus(op.getPartSource());
             or.setRuleModel(op.getRuleModel());
             or.setStatus("1");
             or.setPartPrice(op.getPartPrice());
             or.setPartId(op.getId());
             or.setOutAndEndTime(df.format(new Date()));
             or.setNumber(String.valueOf(orp.getPurchaseCount()));
             or.setPurchaseCode(orp.getPurchaseCode());
            repositoryHis.save(or);
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
    public OrderPlanned findWarehouseOne(Long id) throws ServiceException {
        return repository.getOne(id);
    }

}
