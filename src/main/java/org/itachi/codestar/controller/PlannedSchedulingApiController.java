package org.itachi.codestar.controller;

import org.itachi.codestar.domain.OrderDevice;
import org.itachi.codestar.domain.PlannedScheduling;
import org.itachi.codestar.service.PlannedSchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**

 *计划排期管理
 * @author itachi
 * @since 2018/3/16 00:05
 */
@RestController
@RequestMapping("/api/plannedscheduling")
@Validated
public class PlannedSchedulingApiController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(PlannedSchedulingApiController.class);
    @Autowired
    private PlannedSchedulingService service;
    @GetMapping
    public Map<String, Object> configPages(@Valid @RequestParam("planDeviceId") String planDeviceId,
                                           @Valid @RequestParam("partName") String partName,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows) throws Exception {
        return service.schedulingPages(planDeviceId,partName,buildPager(page,rows));
    }
    @GetMapping("/out")
    public Map<String, Object> outConfigPages(@Valid @RequestParam("planDeviceId") String planDeviceId,
                                              @Valid @RequestParam("partName") String partName,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows) throws Exception {
        return service.outConfigPages(planDeviceId,partName,buildPager(page,rows));
    }
    @GetMapping("/planMage")
    public Map<String, Object> planMage(@Valid @RequestParam("orderNumber") String orderNumber,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows) throws Exception {
        return service.planMage(orderNumber,buildPager(page,rows));
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        service.delete(ids);
    }


    @GetMapping("/{id}")
    public PlannedScheduling plannedScheduling(@PathVariable("id") Long id) throws Exception {
        return service.planScheduling(id);
    }
    @PutMapping("update")
    public void updatePlanStatus(@RequestBody @Valid List<Long> id) throws Exception {
         service.updatePlanStatus(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid PlannedScheduling plannedScheduling) throws Exception {
        logger.info("Updating album " + plannedScheduling.getId());
        service.create(plannedScheduling);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid List<Long> orderStatus) throws Exception {
        logger.info("Adding service ");
        service.modify(orderStatus);
    }
    @PutMapping("/out/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void outModify(@RequestBody @Valid List<Long> orderStatus,@PathVariable("id") Long id) throws Exception {
        service.outModify(orderStatus,id);
    }

    @PutMapping("/determine")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void determine(@RequestBody @Valid Long warehouseId) throws Exception {
        logger.info("Adding service ");
        service.determine(warehouseId);
    }
    @PutMapping("/change")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDevice changeModify(@RequestBody @Valid OrderDevice orderDevice) throws Exception {
        return service.modifySave(orderDevice);
    }
    @PutMapping("updateStatus/{id}/{status}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStatus(@PathVariable("status")int status,@PathVariable("id") Long id) throws Exception {
        service.updateStatus(status,id);
    }
}
