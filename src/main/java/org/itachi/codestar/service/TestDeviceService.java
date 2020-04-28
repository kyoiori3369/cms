package org.itachi.codestar.service;

import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.TestDevice;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.TestDeviceMapper;
import org.itachi.codestar.repositories.jpa.JpaTestDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备调试逻辑
 *
 * @author zhuzhidong
 */
@Service
public class TestDeviceService {

    private static final String PAGER = "pager";
    private static final String TESTDEVICES = "testDevices";

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private JpaTestDeviceRepository repository;

    @Autowired
    private TestDeviceMapper mapper;

    public Map<String, Object> device(String orderNumber, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(orderNumber)) {
            pager.setTotal((int) repository.count());
            map.put(TESTDEVICES, repository.findAll(PageRequest.of(pager.getPage() - 1, pager.getRows())).getContent());
        } else {
            String key = orderNumber.trim();
            pager.setTotal((int) repository.countByOrderNumber(key));
            Page<TestDevice> debuggingPager = repository.findByOrderNumber(key, PageRequest.of(pager.getPage() - 1, pager.getRows()));
            map.put(TESTDEVICES, debuggingPager.getContent());
        }
        map.put(PAGER, pager);
        return map;
    }


    public Map<String, Object> findTestService(String orderNumber,Pager pager)throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(orderNumber)) {
            pager.setTotal((int) repository.count());
        } else {
            pager.setTotal(repository.countByOrderNumber(orderNumber));
        }
        map.put("orderNumber",orderNumber);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        map.put(TESTDEVICES, mapper.findTestDevice(map));
        map.put(PAGER, pager);
        return map;
    }

    public void testDevicesSave(TestDevice testDevice)throws Exception {
        String da = df.format(new Date());
        testDevice.setLoadingTime(df.format(new Date()));
        Integer number = loadingNumber(da);
        testDevice.setIncrement(number);
        testDevice.setLoadingNumber("zc"+ number);
        repository.save(testDevice);
    }

    public Integer loadingNumber(String da) {
        Integer ii = mapper.findLoadingNumber(da);
        if(ii==null||ii<=0){
            String d = df.format(new Date());
            ii = (Integer.valueOf(d)*100)+1;
        }else {
            ii++;
        }
        return ii;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void delete(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void create(TestDevice testDevice) {
        repository.save(testDevice);
    }

    public TestDevice testDevice(Long id) throws ServiceException {
        Optional<TestDevice> testDevice = repository.findById(id);
        if (testDevice.isPresent()) {
            return testDevice.get();
        }
        throw new ServiceException(501, "设备信息不存在！");
    }

    public void modify(TestDevice testDevice) {
        repository.save(testDevice);
    }

}
