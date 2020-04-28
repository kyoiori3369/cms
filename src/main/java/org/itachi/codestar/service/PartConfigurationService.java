package org.itachi.codestar.service;

import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.DeviceStandard;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.PartConfiguration;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.JpaDeviceStandardRepository;
import org.itachi.codestar.repositories.jpa.JpaPartConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

/**
 *零件配置服务
 * @author itachi
 * @since 2018/3/9 20:00
 */
@Service
public class PartConfigurationService {
    private static final String PAGER = "pager";
    private static final String CONFIGURATION = "configs";
    @Autowired
    private JpaPartConfigurationRepository repository;
    @Autowired
    private JpaDeviceStandardRepository deviceStandardRepository;


    @Autowired
    private PartMapper partMapper;
    /**
     * 分页查询
     * @param partName
     * @param pager
     * @return
     */
    public Map<String, Object> configPages(String partName, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("partName",partName);
        int count = partMapper.countPartName(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<PartConfiguration> partConfigurations = partMapper.findPartList(map);
      /*  for (PartConfiguration partConfiguration : partConfigurations) {
            partConfiguration.setPartImage(request.getContextPath() + partConfiguration.getPartImage());
        }*/
        map.put(CONFIGURATION, partConfigurations);
        map.put(PAGER, pager);
        return map;
    }
    /**
     *根据id批量删除
     * @param ids
     */
    @Transactional(rollbackOn = Throwable.class)
    public void delete(List<Long> ids) {
        repository.deleteByIdIn(ids);
        deviceStandardRepository.deleteByPartIdIn(ids);
    }

    /**
     * 创建零件配置保存
     * @param configuration
     * @throws Exception
     */
    public void create(PartConfiguration configuration){
        repository.save(configuration);
    }
    public PartConfiguration partConfig(Long id) throws ServiceException {
        Optional<PartConfiguration> config = repository.findById(id);
        if (config.isPresent()) {
            return config.get();
        }
        throw new ServiceException(501, "设备信息不存在！");
    }
    /**
     * 修改零件配置
     * @param configuration
     * @throws Exception
     */
    public void modify(PartConfiguration configuration){
        repository.save(configuration);
    }

    public Map<String,Object> storage(@Valid String partName, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("partName",partName);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        int count = partMapper.countPartName(map);
        pager.setTotal(count);
        List<PartConfiguration> assembling = partMapper.findPart(map);
        map.put(CONFIGURATION, assembling);
        map.put(PAGER, pager);
        return map;
    }
    public Map<String,Object> storagePlanned(String orderDeviceId,String orderDeviceCount, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(orderDeviceId)||StringUtils.isBlank(orderDeviceCount)) {
            return map;
        }
        map.put("orderDeviceId",orderDeviceId);
        map.put("orderDeviceCount",orderDeviceCount);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<DeviceStandard> deviceStandard= deviceStandardRepository.findByDeviceId(Long.valueOf(orderDeviceId));
        List<PartConfiguration> paList = new ArrayList<PartConfiguration>();
        for(DeviceStandard de:deviceStandard){
            int count = de.getPartCount() * Integer.valueOf(orderDeviceCount);
            PartConfiguration pa = repository.getOne(de.getPartId());
            Long lack = count - pa.getNumberParts();
            if(lack > 0){
                pa.setPurchaseCount(lack);
                paList.add(pa);
            }
        }
        pager.setTotal(paList.size());
        map.put(CONFIGURATION, paList);
        map.put(PAGER, pager);
        return map;
    }


    /**
     * 加载零件名称下拉框值填充
     * @param partName
     * @return
     */
    public Map<String,Object> findPartService(String partName) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(partName)) {
            map.put(CONFIGURATION, repository.findAll());
        }
        return map;
    }
}
