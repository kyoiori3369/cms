package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.*;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.repositories.jpa.JpaCustomerRepository;
import org.itachi.codestar.repositories.jpa.JpaPartConfigurationRepository;
import org.itachi.codestar.repositories.jpa.JpaWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class WarehouseService {
    
    @Autowired
    private JpaWarehouseRepository repository;
    @Autowired
    private JpaPartConfigurationRepository repositoryPart;

    public Map<String, Object> findManageService(String partCode, Pager pager) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(partCode)) {
            pager.setTotal((int) repository.count());
            map.put("warehouses", repository.findAll(PageRequest.of(pager.getPage() - 1, pager.getRows())).getContent());
        } else {
            pager.setTotal(repository.countByPartCode(partCode));
            Page<Warehouse> servicePage = repository.findByPartCode(partCode, PageRequest.of(pager.getPage() - 1, pager.getRows()));
            map.put("warehouses", servicePage.getContent());
        }
        map.put("pager", pager);
        return map;
    }

    /**
     * 创建客户
     *
     * @param warehouse
     */
    public void customerSave(Warehouse warehouse) throws Exception {
        repository.save(warehouse);
    }

    /**
     * 更新客户
     *
     * @param warehouse
     */
    public void modifySave(Warehouse warehouse) throws Exception {
        repository.save(warehouse);
    }
    /**
     * 更新阀值
     *
     * @param warehouse
     */
    public void updateThreshold(PartConfiguration warehouse) throws Exception {
        PartConfiguration wa =  repositoryPart.getOne(warehouse.getId());
        wa.setThreshold(warehouse.getThreshold());
        repositoryPart.save(wa);
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
    public Warehouse findWarehouseOne(Long id) throws ServiceException {
        return repository.getOne(id);
    }

}
