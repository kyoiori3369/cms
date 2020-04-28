package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.service.HistoricalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/api/historicalRecord")
@Validated
@Slf4j
public class HistoricalRecordController extends BaseController {
    @Autowired
    private HistoricalRecordService historicalRecordService;
    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("status") String status,
                                      @RequestParam("partId") String partId,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return historicalRecordService.findManageService(partId,status,pager);
    }
    @GetMapping("get")
    public Map<String, Object> findByReport() throws Exception {
        return historicalRecordService.findByReport();
    }
    @GetMapping("/overview")
    public Map<String,Object> findOverview(@Valid @RequestParam("orderNumber") String orderNumber,
                                           @RequestParam("deviceNumber") String deviceNumber,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows){
        Pager pager = buildPager(page, rows);
        return historicalRecordService.findOverview(pager,orderNumber,deviceNumber);
    }
}
