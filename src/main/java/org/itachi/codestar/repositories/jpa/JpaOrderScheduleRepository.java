package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.OrderSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:45
 */
@Repository
public interface JpaOrderScheduleRepository extends JpaRepository<OrderSchedule, Long> {

    /***
     * 根据订单id和预排期状态查询记录数
     * @param orderId 订单id
     * @param status 预排期状态
     * @return 记录数
     */
    int countByOrderIdAndStatus(Long orderId, String status);

    /**
     * 根据订单id批量删除明细
     * @param orderId 订单id
     */
    void deleteByOrOrderId(Long orderId);
}
