package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.OrderPlanned;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.WarehouseTemp;
import org.itachi.codestar.service.OrderPlannedService;
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
@RequestMapping("/api/orderPlanned")
@Validated
@Slf4j
public class OrderPlannedController extends BaseController {
    @Autowired
    private OrderPlannedService orderPlannedService;
    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("planId")String planId,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return orderPlannedService.findManageService(planId,pager);
    }
    @GetMapping("purchasePart")
    public Map<String, Object> findPurchasePart(@Valid @RequestParam("planId")String planId,
                                      @RequestParam("status")String status,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return orderPlannedService.findPurchasePart(planId,status,pager);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OrderPlanned orderPlanned) throws Exception {
        orderPlannedService.plannedSave(orderPlanned);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid OrderPlanned service) throws Exception {
        orderPlannedService.modifySave(service);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        orderPlannedService.deleteById(id);
    }

    @DeleteMapping("/purchasePart/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purchasePartDelete(@RequestBody List<Long> ids,@PathVariable Long id)throws Exception {
        orderPlannedService.purchasePartDelete(ids,id);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        orderPlannedService.warehouseDelete(ids);
    }
    @PutMapping("/updatePurchase/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePurchase(@RequestBody List<Long> ids,@PathVariable Long id) throws Exception {
        orderPlannedService.updatePurchase(ids,id);
    }
    @PutMapping("/updateCount")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCount(@RequestBody List<Long> ids) throws Exception {
        orderPlannedService.updateCount(ids);
    }

}
