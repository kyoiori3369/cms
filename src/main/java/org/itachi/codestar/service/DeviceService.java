package org.itachi.codestar.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Device;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.error.ServiceError;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.DeviceMapper;
import org.itachi.codestar.repositories.jpa.JpaDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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
public class DeviceService {
    @Autowired
    private JpaDeviceRepository repository;

    @Autowired
    private DeviceMapper mapper;

    public Map<String, Object> findManageService(String deviceName, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(deviceName)) {
            pager.setTotal((int) repository.count());
            map.put("devices", repository.findAll(PageRequest.of(pager.getPage() - 1, pager.getRows())).getContent());
        } else {
            pager.setTotal(repository.countByDeviceName(deviceName));
            Page<Device> servicePage = repository.findByDeviceName(deviceName, PageRequest.of(pager.getPage() - 1, pager.getRows()));
            map.put("devices", servicePage.getContent());
        }
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findDevice(String deviceName,Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("deviceName",deviceName);
        int count = mapper.countDevice(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Device> assembling = mapper.findDevice(map);
        map.put("devices", assembling);
        map.put("pager", pager);
        return map;
    }


    /**
     * 创建设备
     *
     * @param deviceName
     */
    public void deviceNameSave(Device deviceName) throws ServiceException {
        datavalidation(deviceName);
        repository.save(deviceName);
    }

    /**
     * 批量删除客户
     *
     * @param ids
     */
    public void deviceNameDelete(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * 根据ID查询
     *
     * @param id
     */
    public Device findDeviceNameOne(Long id) throws ServiceException {
        return repository.getOne(id);
    }

    /**
     * 更新设备
     *
     * @param device
     * @throws Exception
     */
    public void modifySave(Device device) throws ServiceException {
        datavalidation(device);
        repository.save(device);
    }


    public void datavalidation(Device device) throws ServiceException {
        if (StringUtils.isBlank(device.getDeviceName())) {
            throw new ServiceException(ServiceError.Error.DEVICENAME_NOT_EMPTY);
        }
        if (StringUtils.isBlank(device.getDeviceNumber())) {
            throw new ServiceException(ServiceError.Error.DEVICENUMBER_NOT_EMPTY);
        }
        if (StringUtils.isBlank(device.getDeviceRule())) {
            throw new ServiceException(ServiceError.Error.DEVICERULE_NOT_EMPTY);
        }
//        if (StringUtils.isBlank(device.getDeviceStandard())) {
//            throw new ServiceException(ServiceError.Error.DEVICESTANDARD_NOT_EMPTY);
//        }
    }

    /**
     * 加载设备名称下拉框值填充
     * @param deviceName
     * @return
     */
    public Map<String,Object> findDeviceService(@Valid String deviceName) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(deviceName)) {
            map.put("devices", repository.findAll());
        }
        return map;
    }
}

