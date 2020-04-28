package org.itachi.codestar.service;

import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.OrderManage;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.OrderMapper;
import org.itachi.codestar.repositories.jpa.JpaOrderDeviceRepository;
import org.itachi.codestar.repositories.jpa.JpaOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class OrderManageService {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat df_ = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private JpaOrdersRepository repository;

    @Autowired
    private JpaOrderDeviceRepository deviceRepository;
    @Autowired
    private OrderMapper mapper;


    public Map<String, Object>  findManageService(String orderNumber, Pager pager)throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(orderNumber)) {
            pager.setTotal((int) repository.countByDeleted(0));
        } else {
            pager.setTotal(repository.countByOrderNumberAndDeleted(orderNumber,0));
        }
        map.put("orderNumber",orderNumber);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put("orderManages", mapper.findOrderManage(map));
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findOrder(String orderNumber,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("orderNumber",orderNumber);
        int count = mapper.countOrder(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put("status",1);
        List<OrderManage> assembling = mapper.findOrderManage(map);
        map.put("orderManages", assembling);
        map.put("pager", pager);
        return map;
    }

    /**
     * 创建订单管理
     * @param orderManage
     */
    public void orderManageSave(OrderManage orderManage)throws ServiceException {
        datavalidation(orderManage);
        String da = df.format(new Date());
        orderManage.setCreateTime(df.format(new Date()));
        orderManage.setDeliveryTime("待定");
        Integer number = orderNumber(da);
        orderManage.setIncrement(number);
        orderManage.setOrderNumber("D"+ number);
        repository.save(orderManage);
    }
    //生成订单编号
    public Integer orderNumber(String da) {
        Integer ii =  mapper.findOrderNumber(da);
        if(ii==null||ii<=0){
            String d = df.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }
    /**
     * 批量删除订单
     * @param ids
     */
    public void orderManageDelete(List<Long> ids)throws Exception  {
        Boolean bo = false;
        for(Long v : ids){
            int i = deviceRepository.countByOrderIdAndDeleted(v,0);
            if(i<=0){
                mapper.deleteAllByOrderIdIn(v);
            }else {
                bo = true;
            }
        }
       if (bo){
           throw new ServiceException(ServiceError.Error.CHILD_UNKNOWN);
       }
    }

    public void modifySave(OrderManage orderManage)throws ServiceException {
        datavalidation(orderManage);
        repository.save(orderManage);
    }

    /**
     * 根据ID删除
     * @param id
     */
    public void  deleteById(Long id)throws Exception {
        repository.deleteById(id);
    };

    public OrderManage findOrderManageOne(Long id)throws Exception {
        return  repository.getOne(id);
    };

    public void datavalidation(OrderManage orderManage) throws ServiceException {
        if (StringUtils.isBlank(orderManage.getExpectDate())) {
            throw new ServiceException(ServiceError.Error.EXPECTDATE_NOT_EMPTY);
        }
        if (StringUtils.isBlank(orderManage.getOrderTime())) {
            throw new ServiceException(ServiceError.Error.ORDERTIME_NOT_EMPTY);
        }
        if ((orderManage.getCustomerId() == null)) {
            throw new ServiceException(ServiceError.Error.CUSTOMER_NOT_EMPTY);
        }
    }

}
