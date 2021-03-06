package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.OrderPlanned;
import org.itachi.codestar.domain.WarehouseTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 15:17
 *
 * @author itachi
 */
@Repository
public interface JpaOrderPlannedRepository extends JpaRepository<OrderPlanned, Long> {

    /**
     * 根据应用名获取记录数
     * @param planId 应用名
     * @return 记录数
     */
    int countByPlanId(Long planId);
    int countByPlanIdAndIntStatus(Long planId,int Status);
    /**
     * 根据应用名获取记录数
     * @param purchaseStatus 应用名
     * @return 记录数
     */
    int countByPlanIdAndPurchaseStatus(Long planId ,String purchaseStatus);

    List<OrderPlanned> findByPlanId(Long planId);
    /**
     * 根据id批量删除应用数据
     * @param ids 应用id集合
     */
    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);

}
