package org.itachi.codestar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.codestar.domain.*;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:30
 */
@Mapper
public interface PurchaseMapper {
  //计划单号
  Integer findPurchaseNumber(String da);
  //采购单号
  Integer findPurchaseNumberG(String da);

  List<OrderPlanned> findOrderPlanned(Map<String, Object> parameters);

  List<PurchaseParts> findOrderPlannedDetail(Map<String, Object> parameters);
  //更新状态
  void updatePurchase(List<Long> ids);
  //批量更新采购单
  void updatePurchaseManage(List<Long> ids);
  void updatePurchasePlanId(Long id);
  //更新采购状态r
  void updatePurchasePart(List<Long> ids);
  //更新采购状态
  void updateOrderPlanned(Long id);
  //更新入库状态
  void updateIsStatus(Long id);

  List<Purchase> findPurchases(Map<String, Object> parameters);
  
  List<Purchase> findPurchasesIn(Map<String, Object> parameters);
  //采购列表
  List<PurchaseMapper> findPurchasesManage(Map<String, Object> parameters);

  /**
   * 查詢完成狀態數量
   * @param parameters
   * @return
   */
  int countPurchasesIn(Map<String, Object> parameters);

  /**
   * 删除排期零件
   */
  void deleteByPlanDeviceId(Long id);
}
