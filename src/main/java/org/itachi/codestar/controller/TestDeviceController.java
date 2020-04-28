package org.itachi.codestar.controller;

import org.itachi.codestar.domain.TestDevice;
import org.itachi.codestar.service.TestDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by kyo on 2018/3/7.
 *
 * @author zhuzhidong
 */

@RestController
@RequestMapping("api/device")
@Validated
public class TestDeviceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TestDeviceController.class);
    @Autowired
    private TestDeviceService testDeviceService;

    @GetMapping
    public Map<String, Object> device(@Valid @RequestParam("orderNumber") String orderNumber,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        return testDeviceService.findTestService(orderNumber, buildPager(page, rows));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid TestDevice testDevice) throws Exception {
        logger.info("Updating album " + testDevice.getId());
        testDeviceService.testDevicesSave(testDevice);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        testDeviceService.delete(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        testDeviceService.delete(ids);
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid TestDevice testDevice) throws Exception {
        testDeviceService.create(testDevice);
    }*/

    @GetMapping("/{id}")
    public TestDevice testDevice(@PathVariable("id") Long id) throws Exception {
        return testDeviceService.testDevice(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid TestDevice testDevice) throws Exception {
        testDeviceService.modify(testDevice);
    }

}
