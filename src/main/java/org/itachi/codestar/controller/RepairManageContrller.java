package org.itachi.codestar.controller;

import org.itachi.codestar.domain.Entrucking;
import org.itachi.codestar.domain.RepairManage;
import org.itachi.codestar.service.RepairManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/repairManage")
@Validated
public class RepairManageContrller extends BaseController{
    @Autowired
    private RepairManageService manageService;

    @GetMapping
    public Map<String, Object> repair(@Valid @RequestParam("orderNumber") String orderNumber ,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        return manageService.repair(orderNumber,buildPager(page, rows));
    }
    @GetMapping("{id}/{loadingId}")
    public RepairManage repairManages(@PathVariable("id") Long id,@PathVariable("loadingId") Long loadingId) throws Exception {
        return manageService.repairManage(id,loadingId) ;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid RepairManage repairmanage) throws Exception {
        manageService.create(repairmanage);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid RepairManage repairmanage) throws Exception {
        manageService.modify(repairmanage);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        manageService.delete(ids);
    }

    @GetMapping("detailed")
    public Map<String, Object> repairDetailed(@Valid @RequestParam("loadingId") String loadingId ,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows,
                                      HttpServletRequest request) throws Exception {
        return manageService.repairDetailed(loadingId,buildPager(page, rows));
    }

}
