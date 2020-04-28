package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.Device;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.service.DeviceService;
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
@RequestMapping("/api/deviceManage")
@Validated
@Slf4j
public class DeviceController extends BaseController {
    @Autowired
    private DeviceService equipmentService;
    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("deviceName") String deviceName,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return equipmentService.findDevice(deviceName,pager);
    }

    @GetMapping("/search")
    public Map<String, Object> search(@Valid @RequestParam("deviceName") String deviceName) throws Exception {
        return equipmentService.findDeviceService(deviceName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Device device) throws Exception {
        equipmentService.deviceNameSave(device);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid Device device) throws Exception {
        equipmentService.modifySave(device);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        equipmentService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        equipmentService.deviceNameDelete(ids);
    }
    @GetMapping("/{id}")
    public Device equipmentOne(@PathVariable("id") Long id) throws Exception {
        return equipmentService.findDeviceNameOne(id);
    }
}
