package org.itachi.codestar.service;

import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.*;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PurchaseMapper;
import org.itachi.codestar.repositories.jpa.*;
import org.itachi.codestar.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 采购管理逻辑
 *
 * @author zhuzhidong
 */
@Service
public class PurchaseService {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat df_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String PAGER = "pager";
    private static final String PURCHASES = "purchases";
    @Autowired
    private JpaPurchaseRepository repository;
    @Autowired
    private JpaWarehouseTempRepository repositoryTemp;
    @Autowired
    private JpaOrderPlannedRepository repositoryOrder;
    @Autowired
    private JpaPurchasePartsRepository purchasePartsRepository;
    @Autowired
    private JpaPurchaseManageRepository purchaseManageRepository;
    @Autowired
    private PurchaseMapper purchaseMapper;
    //临时采购单
    public Map<String, Object> purchases(String planNumber, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
            if(StringUtils.isBlank(planNumber)){
                pager.setTotal((int) repository.count());
            }else {
                pager.setTotal((int) repository.countByPlanNumber(planNumber));
            }
            map.put("planNumber",planNumber);
            map.put("offset", (pager.getPage() - 1) * pager.getRows());
            map.put("rows", pager.getRows());
            List<Purchase> debuggingPager = purchaseMapper.findPurchases(map);
            map.put(PURCHASES, debuggingPager);
        map.put(PAGER, pager);
        return map;
    }
    //采购单列表
    public Map<String, Object> purchaseManage(String planNumber, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(planNumber)) {
            pager.setTotal((int) purchaseManageRepository.count());
        }else{
            pager.setTotal((int) purchaseManageRepository.countByPurchaseCode(planNumber));
        }
        map.put("purchaseCode",planNumber);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<PurchaseMapper> debuggingPager = purchaseMapper.findPurchasesManage(map);
        map.put(PURCHASES, debuggingPager);
        map.put(PAGER, pager);
        return map;
    }

    //入库
    public Map<String, Object> purchaseIn(String isStatus, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
       /* if (StringUtils.isBlank(isStatus)) {
            pager.setTotal((int) purchaseManageRepository.count());
        }else{
            pager.setTotal((int) purchaseManageRepository.countByPurchaseCode(isStatus));
        }*/
        map.put("isStatus",isStatus);
        int count = purchaseMapper.countPurchasesIn(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Purchase> debuggingPager = purchaseMapper.findPurchasesIn(map);
        map.put(PURCHASES, debuggingPager);
        map.put(PAGER, pager);
        return map;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void delete(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PurchaseManage addPurchaseManage(String expectDate){
        PurchaseManage  purchaseManage  = new  PurchaseManage();
        String dateTime = df_.format(new Date());
        purchaseManage.setCreateTime(dateTime);
        Integer orderNumber= orderNumberG(dateTime);
        purchaseManage.setPurchaseCode("G"+orderNumber);
        purchaseManage.setPlanTime(dateTime);
        purchaseManage.setOrderUpdateTime(dateTime);
        purchaseManage.setExpectDate(expectDate);
        purchaseManage.setOrderStatus(Constants.INDEX_ZERO);
        purchaseManage.setIncrement(orderNumber);
        purchaseManageRepository.save(purchaseManage);
        return purchaseManage;
    }
    //批量处理
    public void updatePurchase(List<Long> ids){
        purchaseMapper.updatePurchaseManage(ids);
        for(Long vi : ids){
            purchaseMapper.updatePurchasePlanId(vi);
        }
    }
    //生成采购单
    public void addPurchase(List<Long> ids,String expectDate){
        PurchaseManage purchaseManage =addPurchaseManage(expectDate);
        for(Long vi : ids){
            purchaseMapper.updateOrderPlanned(vi);
            List<OrderPlanned> orderPlanned = repositoryOrder.findByPlanId(vi);
            for(OrderPlanned vo : orderPlanned){
                PurchaseParts purchaseParts = new PurchaseParts();
                purchaseParts.setPartId(vo.getPartId());
                purchaseParts.setPurchaseCode(purchaseManage.getPurchaseCode());
                purchaseParts.setPurchaseCount(vo.getPurchaseCount());
                purchaseParts.setPlanId(purchaseManage.getId());
                purchaseParts.setPurchaseStatus(Constants.INDEX_ZERO);
                purchasePartsRepository.save(purchaseParts);
            }
        }
    }
    //生成采购编号
    public Integer orderNumberG(String da) {
        Integer ii = purchaseMapper.findPurchaseNumberG(da);
        if (ii == null || ii <= 0) {
            String d = df.format(new Date());
            ii = (Integer.valueOf(d) * 100) + 1;
        } else {
            ii++;
        }
        return ii;
    }

    //生成临时采购单
    public void planSheetSize(List<Long> ids) {
        if(ids.size() > 0){
            Purchase pc = planNumber(df.format(new Date()));
          for(Long vi : ids){
              WarehouseTemp viList= repositoryTemp.getOne(vi);
              OrderPlanned or = new OrderPlanned();
              or.setPlanId(pc.getId());
              or.setPurchaseCode(pc.getPlanNumber());
              or.setPartId(viList.getPartId());
              or.setPurchaseCount(viList.getPurchaseCount());
              or.setPurchaseStatus(Constants.INDEX_ZERO);
              repositoryOrder.save(or);
              viList.setDeleted(1);
              repositoryTemp.save(viList);
          }
        }
    }

    public void create(Purchase purchase) throws Exception {
        repository.save(purchase);
    }

    public Purchase purchase(Long id) throws ServiceException {
        Optional<Purchase> purchases = repository.findById(id);
        if (purchases.isPresent()) {
            return purchases.get();
        }
        throw new ServiceException(601, "采购信息不存在！");
    }

    public void modify(Purchase purchase) throws Exception {
        repository.save(purchase);
    }

    public Purchase planNumber(String dateTime){
       Purchase purchase = new Purchase();
        Integer orderNumber= orderNumber(dateTime);
        purchase.setPlanNumber("P"+orderNumber);
        purchase.setPlanTime(df_ .format(new Date()));
        purchase.setOrderUpdateTime(df_ .format(new Date()));
        purchase.setOrderStatus(Constants.INDEX_ZERO);
        purchase.setIncrement(orderNumber);
        purchase.setCreateTime(dateTime);
        repository.save(purchase);
        return purchase;
    };
    //生成订单编号
    public Integer orderNumber(String da) {
        Integer ii =  purchaseMapper.findPurchaseNumber(da);
        if(ii==null||ii<=0){
            String d = df.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }
}
