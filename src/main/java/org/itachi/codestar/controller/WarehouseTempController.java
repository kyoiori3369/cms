package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.Warehouse;
import org.itachi.codestar.domain.WarehouseTemp;
import org.itachi.codestar.service.WarehouseService;
import org.itachi.codestar.service.WarehouseTempService;
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
@RequestMapping("/api/warehouseTemp")
@Validated
@Slf4j
public class WarehouseTempController extends BaseController {
    @Autowired
    private WarehouseTempService warehouseTempService;
    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("planSheetCode") String planSheetCode,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return warehouseTempService.findManageService(planSheetCode,pager);
    }
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody List<Long> ids,@PathVariable String id) throws Exception {
        warehouseTempService.warehouseTempSave(ids,id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid WarehouseTemp service) throws Exception {
        warehouseTempService.modifySave(service);
    }
    @PutMapping("/save")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifySaveCount(@RequestBody @Valid WarehouseTemp service) throws Exception {
        warehouseTempService.modifySaveCount(service);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        warehouseTempService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        warehouseTempService.warehouseDelete(ids);
    }
    @GetMapping("/{id}")
    public WarehouseTemp findWarehouseOne(@PathVariable("id") Long id) throws Exception {
        return warehouseTempService.findWarehouseOne(id);
    }

}
