package org.itachi.codestar.service;

import org.itachi.codestar.domain.Customer;
import org.itachi.codestar.domain.Order;
import org.itachi.codestar.repositories.jpa.JpaCustomerRepository;
import org.itachi.codestar.repositories.jpa.JpaOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private JpaCustomerRepository customerRepository;

    @Autowired
    private JpaOrderRepository orderRepository;

    @Autowired
    private OrderService service;

    @Before
    public void setUp() throws Exception {
        Customer customer = new Customer();
        customer.setName("测试");
        customer.setCompanyCode("11111");
        customer.setCompanyName("测试公司");
        customer = customerRepository.save(customer);
        Long id = customer.getId();
        System.out.println("============" + id);
        Order order = new Order();
        order.setCustomerId(id);
        order.setNumber("2018032001");
        order.setStatus("生产中");
        order.setCreateTime(System.currentTimeMillis());
        orderRepository.save(order);
        Order order1 = new Order();
        order1.setCustomerId(id);
        order1.setNumber("2018032002");
        order1.setStatus("待生产");
        order1.setCreateTime(System.currentTimeMillis());
        orderRepository.save(order1);
    }

    @Test
    public void findOrders() {
        List<Order> orders = service.findOrders();
        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).getNumber()).isEqualTo("2018032001");
        assertThat(orders.get(0).getCompanyCode()).isEqualTo("11111");
    }
}