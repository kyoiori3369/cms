package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:45
 */
@Repository
public interface JpaOrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    /**
     * 根据订单id批量删除明细
     * @param orderId 订单id
     */
    void deleteByOrOrderId(Long orderId);

}
