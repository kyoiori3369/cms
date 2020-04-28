package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.Customer;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.PartConfiguration;
import org.itachi.codestar.domain.Warehouse;
import org.itachi.codestar.service.CustomerService;
import org.itachi.codestar.service.WarehouseService;
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
@RequestMapping("/api/warehouse")
@Validated
@Slf4j
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("partCode") String partCode,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return warehouseService.findManageService(partCode,pager);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Warehouse customer) throws Exception {
        warehouseService.customerSave(customer);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid Warehouse service) throws Exception {
        warehouseService.modifySave(service);
    }
    @PutMapping("threshold")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateThreshold(@RequestBody @Valid PartConfiguration service) throws Exception {
        warehouseService.updateThreshold(service);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        warehouseService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        warehouseService.customerDelete(ids);
    }
    @GetMapping("/{id}")
    public Warehouse findWarehouseOne(@PathVariable("id") Long id) throws Exception {
        return warehouseService.findWarehouseOne(id);
    }

}
