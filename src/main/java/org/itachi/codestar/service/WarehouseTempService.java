package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.Warehouse;
import org.itachi.codestar.domain.WarehouseTemp;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.JpaWarehouseRepository;
import org.itachi.codestar.repositories.jpa.JpaWarehouseTempRepository;
import org.itachi.codestar.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
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
public class WarehouseTempService {
    
    @Autowired
    private JpaWarehouseTempRepository repository;
    @Autowired
    private JpaWarehouseRepository warehouseRepository;
    @Autowired
    private PartMapper partMapper;

    public Map<String, Object> findManageService(String planSheetCode, Pager pager) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(planSheetCode)) {
          return null;
        }
        map.put("planSheetCode",planSheetCode);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        pager.setTotal(repository.countByPlanSheetCode(planSheetCode));
        List<WarehouseTemp> servicePage = partMapper.findPartTemp(map);
        map.put("warehouseTemps", servicePage);
        map.put("pager", pager);
        return map;
    }

    /**
     * 创建临时计划单
     *
     */
    public void warehouseTempSave( List<Long> ids, String id) throws Exception {
        if(ids==null||ids.size()<=0){
            return;
        }else{
         for(int i =0 ;i<ids.size();i++){
              WarehouseTemp warehouseTemp = new WarehouseTemp();
                warehouseTemp.setPlanSheetCode(id);
                warehouseTemp.setPurchaseCount(1);
                warehouseTemp.setIsStatus(Constants.INDEX_ZERO);
                warehouseTemp.setPartId(ids.get(i));
             repository.save(warehouseTemp);
        }}
    }

    /**
     * 更新临时计划单
     *
     * @param warehouse
     */
    public void modifySave(WarehouseTemp warehouse) throws Exception {
        repository.save(warehouse);
    }

    /**
     * 更新数量
     * @param warehouse
     * @throws Exception
     */
    public void modifySaveCount(WarehouseTemp warehouse) throws Exception {
        WarehouseTemp wa = repository.getOne(warehouse.getId());
        wa.setPurchaseCount(warehouse.getPurchaseCount());
        repository.save(wa);
    }

    /**
     * 批量删除临时计划单
     *
     * @param ids
     */
    public void warehouseDelete(List<Long> ids) throws Exception {
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
    public WarehouseTemp findWarehouseOne(Long id) throws ServiceException {
        return repository.getOne(id);
    }

}
