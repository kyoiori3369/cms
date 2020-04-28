package org.itachi.codestar.controller;

import org.itachi.codestar.domain.Assembling;
import org.itachi.codestar.service.AssemblingService;
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
 *设备装配
 * @author itachi
 * @since 2018/3/16 00:05
 */
@RestController
@RequestMapping("/api/assembling")
@Validated
public class AssemblingController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(AssemblingController.class);
    @Autowired
    private AssemblingService assemblingService;

    @GetMapping("/planAssem")
    public Map<String, Object> assemblingPages(@Valid @RequestParam("status") String status,
                                               @RequestParam("page")Integer page,
                                               @RequestParam("rows") Integer rows) throws Exception {
        return assemblingService.assemblingPages(status,buildPager(page, rows));
    }
    @GetMapping("/equipment")
    public Map<String, Object> configPages(@Valid @RequestParam("partName") String partName,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows) throws Exception {
        return assemblingService.partsPages(partName,buildPager(page,rows));
    }
    @GetMapping("/testDevice")
    public Map<String, Object> testDevice(@Valid @RequestParam("status") String status,
                                          @Valid @RequestParam("orderNumber") String orderNumber,
                                          @RequestParam("page")Integer page,
                                          @RequestParam("rows") Integer rows) throws Exception {
        return assemblingService.findTestDevicd(status,orderNumber,buildPager(page, rows));
    }
  /*  @PutMapping("/complete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void completeModify(@RequestBody @Valid List<String> completeState ) throws Exception {
        assemblingService.completeModify(completeState);
    }*/
    @GetMapping("/detailed")
    public Map<String, Object> detailedPages(@Valid @RequestParam("planDeviceId") String planDeviceId,
                                              @RequestParam("partName") String partName,
                                              @RequestParam("page")Integer page,
                                              @RequestParam("rows") Integer rows) throws Exception {
        return assemblingService.detailedPages(planDeviceId,partName,buildPager(page,rows));
    }
    @PutMapping("/out/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCompleteModify(@RequestBody @Valid List<Long> ids ,@PathVariable("id") Long id) throws Exception {
        assemblingService.updateCompleteModify(ids,id);
    }

    @PutMapping("/updateComplete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateComplete(@RequestBody @Valid List<Long> ids) throws Exception {
        assemblingService.updateComplete(ids);
    }

    @GetMapping("/{id}")
    public Assembling assembling(@PathVariable("id") Long id) throws Exception {
        return assemblingService.assembling(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid Assembling assembling) throws Exception {
        assemblingService.modify(assembling);
    }

}
