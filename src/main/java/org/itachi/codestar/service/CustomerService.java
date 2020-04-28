package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Customer;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.UserPhone;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.CustomerMapper;
import org.itachi.codestar.repositories.jpa.JpaCustomerRepository;
import org.itachi.codestar.repositories.jpa.JpaUserPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:54
 *
 * @author itachi
 */
@Service
@Slf4j
public class CustomerService {
    
    @Autowired
    private JpaCustomerRepository repository;

    @Autowired
    private JpaUserPhoneRepository userPhoneRepository;

    @Autowired
    private CustomerMapper mapper;

    public Map<String, Object> findManageService(String name, Pager pager) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        //按主键最大的ID排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        if (StringUtils.isBlank(name)) {
            pager.setTotal((int) repository.count());
            map.put("customers", repository.findAll(PageRequest.of(pager.getPage() - 1, pager.getRows(),sort)).getContent());
        } else {
            pager.setTotal(repository.countByName(name));
            Page<Customer> servicePage = repository.findByName(name, PageRequest.of(pager.getPage() - 1, pager.getRows(),sort));
            map.put("customers", servicePage.getContent());
            repository.findAll(sort);
        }
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findCustomer(String companyName,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("companyName",companyName);
        int count = mapper.countCustomer(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Customer> assembling = mapper.findCustomer(map);
        map.put("customers", assembling);
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findCustomerUser(Long customerId) {
        Map<String, Object> map = new HashMap<>(16);
        List<UserPhone> userPhones = userPhoneRepository.findByCustomerId(customerId);
        map.put("userPhones", userPhones);
        return map;

    }

    /**
     * 创建客户
     *
     * @param customer
     */
    public void customerSave(Customer customer) throws ServiceException {
        datavalidation(customer);
        Customer cu =repository.save(customer);
        for(UserPhone userPhone : customer.getUserPhone()){
            userPhone.setCustomerId(cu.getId());
            userPhoneRepository.save(userPhone);
        }
    }

    /**
     * 更新客户
     *
     * @param customer
     */
    public void modifySave(Customer customer) throws ServiceException {
        datavalidation(customer);
        repository.save(customer);
        userPhoneRepository.deleteByCustomerId(customer.getId());
        for(UserPhone userPhone : customer.getUserPhone()){
            userPhone.setCustomerId(customer.getId());
            userPhoneRepository.save(userPhone);
        }
    }

    /**
     * 批量删除客户
     *
     * @param ids
     */
    public void customerDelete(List<Long> ids) throws Exception {
        repository.deleteByIdIn(ids);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    public void deleteById(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * 根据ID查询
     *
     * @param id
     */
    public Customer findCustomerOne(Long id) throws ServiceException {
        List<UserPhone> userPhones = userPhoneRepository.findByCustomerId(id);
        Customer customer = repository.getOne(id);
        customer.setUserPhone(userPhones);
        return customer;
    }


    public void datavalidation(Customer customer) throws ServiceException {
       /* if (StringUtils.isBlank(customer.getName())) {
            throw new ServiceException(ServiceError.Error.CUSTOMERNAME_NOT_EMPTY);
        }*/
        /*if (StringUtils.isBlank(customer.getPosition())) {
            throw new ServiceException(ServiceError.Error.POSITION_NOT_EMPTY);
        }*/
       /* if (StringUtils.isBlank(customer.getPhone())) {
            throw new ServiceException(ServiceError.Error.PHONENUMBER_NOT_EMPTY);
        }
        if (StringUtils.isBlank(customer.getGender())) {
            throw new ServiceException(ServiceError.Error.PLEASE_CHOOSE_SEX);
        }*/
        if (StringUtils.isBlank(customer.getCompanyName())) {
            throw new ServiceException(ServiceError.Error.COMPANYNAME_NOT_EMPTY);
        }
        if (StringUtils.isBlank(customer.getCompanyCode())) {
            throw new ServiceException(ServiceError.Error.COMPANYCODE_NOT_EMPTY);
        }
        if (StringUtils.isBlank(customer.getInputProvince())) {
            throw new ServiceException(ServiceError.Error.PROVINCE_NOT_EMPTY);
        }

        if (StringUtils.isBlank(customer.getInputCity())) {
            throw new ServiceException(ServiceError.Error.CITY_NOT_EMPTY);
        }

        /*if (StringUtils.isBlank(customer.getInputArea())) {
            throw new ServiceException(ServiceError.Error.AREA_NOT_EMPTY);
        }*/
        //设置公司区域=省+市+区/县
        customer.setCompanyArea(
                customer.getInputProvince()+customer.getInputCity()
                        +customer.getInputArea());
        //设置公司电话=区号+号码
        customer.setCompanyPhone(
                customer.getAreaCode() == null ? "" : customer.getAreaCode()
                        +customer.getAreaCode() == null ? "" : "-"
                        +customer.getPhoneNumber() == null ? "" : customer.getPhoneNumber()
        );
        //设置公司传真=区号+号码
        customer.setCompanyFax(customer.getAreaCodes() == null ? "" : customer.getAreaCodes()
                +customer.getAreaCodes() == null ? "" : "-"
                +customer.getFaxNumber() == null ? "" : customer.getFaxNumber()
        );
    }

    /**
     * 用于客户名称填充下拉值
     * @return
     */
    public Map<String,Object> findCustomerAllService() {
        Map<String, Object> map = new HashMap<>(16);
        List<Customer> customerList = mapper.findCustomerList();
        map.put("customers",customerList);
        return map;
    }
}
