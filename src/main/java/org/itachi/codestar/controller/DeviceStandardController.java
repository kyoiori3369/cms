package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.Device;
import org.itachi.codestar.domain.DeviceStandard;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.service.DeviceService;
import org.itachi.codestar.service.DeviceStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2018/1/15.
 * User: itachi
 * Date: 2018/1/15
 * Time: 15:50
 *
 * @author itachi
 */
@RestController
@RequestMapping("/api/deviceStandard")
@Validated
@Slf4j
public class DeviceStandardController extends BaseController {
    @Autowired
    private DeviceStandardService deviceStandardService;
    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("deviceId") String deviceId,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return deviceStandardService.findManageService(deviceId,pager);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid DeviceStandard deviceStandard ) throws Exception {
        deviceStandardService.deviceStandardSave(deviceStandard);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid DeviceStandard deviceStandard) throws Exception {
        deviceStandardService.modifySave(deviceStandard);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        deviceStandardService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        deviceStandardService.deviceStandardDelete(ids);
    }
    @GetMapping("/{id}")
    public DeviceStandard equipmentOne(@PathVariable("id") Long id) throws Exception {
        return deviceStandardService.findDeviceStandardOne(id);
    }
}
