package org.itachi.codestar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.codestar.domain.Customer;
import org.itachi.codestar.domain.UserPhone;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Mapper
public interface CustomerMapper {

    List<Customer> findCustomerList();

    //模糊查询订单编号
    int countCustomer(Map<String, Object> parameters);

    List<Customer> findCustomer(Map<String, Object> parameters);


    List<UserPhone> findUser(Map<String, Object> parameters);

}
