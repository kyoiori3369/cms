package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:45
 */
@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Long> {

    /**
     * 根据下单日期获取日期编号最大的订单数据
     * @param date 下单日期
     * @return 订单数据
     */
    Order getFirstByCreateDateOrderByDateDesc(String date);
}
