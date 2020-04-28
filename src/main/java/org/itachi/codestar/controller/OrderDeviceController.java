package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.OrderDevice;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.service.OrderDeviceService;
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
@RequestMapping("/api/orderDevice")
@Validated
@Slf4j
public class OrderDeviceController extends BaseController {
    @Autowired
    private OrderDeviceService orderDeviceService;

    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("orderId") String orderId,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return orderDeviceService.findManageService(orderId,pager);
    }
    @GetMapping("/shipment")
    public Map<String, Object> findShipment(@Valid @RequestParam("orderNumber") String orderNumber,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return orderDeviceService.findTestDevicd(orderNumber,pager);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OrderDevice orderDevice) throws Exception {
         orderDeviceService.customerSave(orderDevice);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid OrderDevice orderDevice) throws Exception {
         orderDeviceService.modifySave(orderDevice);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        orderDeviceService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        orderDeviceService.customerDelete(ids);
    }
    @GetMapping("/{id}")
    public OrderDevice customerOne(@PathVariable("id") Long id) throws Exception {
        return orderDeviceService.findCustomerOne(id);
    }

}
