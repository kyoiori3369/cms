package org.itachi.codestar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itachi.codestar.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:30
 */
@Mapper
public interface OrderMapper {

    List<Order> findOrder(Map<String, Object> parameters);

    Integer findOrderNumber(String da);

    void deleteAllByOrderIdIn(Long id);
    void updateOrderDate(Long id);

    //模糊查询订单编号
    int countOrder(Map<String, Object> parameters);

    List<OrderManage> findOrderManage(Map<String, Object> parameters);

    List<TestDevice> findTestDevice(Map<String, Object> parameters);

    Integer findSum(String time);

    List<HashMap> findCore(Long id);

    void updateOrderStatus(@Param("id")Long id,@Param("status") String status);




}
