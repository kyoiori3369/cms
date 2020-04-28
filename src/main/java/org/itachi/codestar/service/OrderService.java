package org.itachi.codestar.service;

import org.itachi.codestar.domain.Order;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.OrderMapper;
import org.itachi.codestar.repositories.jpa.JpaOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:45
 */
@Service
public class OrderService {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private JpaOrderRepository repository;

    /*
    @Autowired
    private JpaOrderDetailRepository detailRepository;

    @Autowired
    private JpaOrderScheduleRepository scheduleRepository;
    */

    @Autowired
    private OrderMapper mapper;

    public List<Order> findOrders() {
        return mapper.findOrder(new HashMap<>(16));
    }

    @Transactional(rollbackOn = Throwable.class)
    public void create(Order order) {
        order.setCreateTime(System.currentTimeMillis());
        order.setCreateDate(FORMAT.format(new Date()));
        order.setDate(makeNumber(order.getCreateDate()));
        order.setNumber(MessageFormat.format("D{0}", order.getDate()));
        // 保存订单主体信息
        repository.save(order);
        //order = repository.save(order);
        /*
        for (OrderDetail detail : order.getDevices()) {
            detail.setOrderId(order.getId());
            // 保存订单明细
            detail = detailRepository.save(detail);
            for (int i = 0; i < detail.getCount(); i++) {
                OrderSchedule schedule = new OrderSchedule();
                schedule.setDetailId(detail.getId());
                schedule.setOrderId(detail.getOrderId());
                schedule.setDeviceId(detail.getDeviceId());
                schedule.setDeliveryTime(detail.getDeliveryTime());
                schedule.setStatus("未排期");
                // 生成预排期信息
                scheduleRepository.save(schedule);
            }
        }
        */
    }

    @Transactional(rollbackOn = Throwable.class)
    public void modify(Order order) throws ServiceException {
        if (order.getId() == null || order.getId() < 1L) {
            throw new ServiceException(ServiceError.Error.ORDER_ID_EMPTY);
        }
        repository.save(order);
    }

    @Transactional(rollbackOn = Throwable.class)
    public void remove(Long id) throws ServiceException {

    }

    private Integer makeNumber(String date) {
        Order order = repository.getFirstByCreateDateOrderByDateDesc(date);
        if (order != null && order.getDate() != null && order.getDate() > 0) {
            return order.getDate() + 1;
        } else {
            return Integer.valueOf(date + "01");
        }
    }

}
