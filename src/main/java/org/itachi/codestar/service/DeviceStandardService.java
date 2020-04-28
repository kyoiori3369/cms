package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Device;
import org.itachi.codestar.domain.DeviceStandard;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.JpaDeviceRepository;
import org.itachi.codestar.repositories.jpa.JpaDeviceStandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class DeviceStandardService {
    @Autowired
    private JpaDeviceStandardRepository repository;
    @Autowired
    private PartMapper partMapper;

    public Map<String, Object>  findManageService(String deviceId,Pager pager)throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(deviceId)) {
          return null;
        }
        map.put("deviceId",deviceId);
        pager.setTotal(repository.countByDeviceId(Long.valueOf(deviceId)));
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<DeviceStandard> servicePage = partMapper.findManageStandard(map);
        map.put("deviceStandards", servicePage);
        map.put("pager", pager);
        return map;
    }

    /**
     * 创建设备
     * @param deviceStandard
     */
    public void deviceStandardSave(DeviceStandard deviceStandard)throws ServiceException {
        int ii = repository.countByDeviceIdAndPartId(deviceStandard.getDeviceId(),deviceStandard.getPartId());
        if(ii>0){
            throw new ServiceException(ServiceError.Error.CHILD_PART);
        }
        repository.save(deviceStandard);
    }

    /**
     * 更新设备
     * @param deviceStandard
     * @throws Exception
     */
    public void modifySave(DeviceStandard deviceStandard)throws Exception {
        DeviceStandard de = repository.getOne(deviceStandard.getId());
        de.setPartCount(deviceStandard.getPartCount());
        repository.save(de);
    }

    /**
     * 批量删除设备
     * @param ids
     */
    @Transactional(rollbackOn = Throwable.class)
    public void deviceStandardDelete(List<Long> ids)throws Exception  {
        repository.deleteByIdIn(ids);
    }

    /**
     * 根据ID删除
     * @param id
     */
    public void  deleteById(Long id)throws Exception {
        repository.deleteById(id);
    }
    /**
     * 根据ID查询
     * @param id
     */
    public DeviceStandard findDeviceStandardOne(Long id)throws ServiceException {
        Optional<DeviceStandard> device= repository.findById(id);
        if (device != null) {
            return device.get();
        }
        throw new ServiceException(ServiceError.Error.DATA_ONE);
    };
}
