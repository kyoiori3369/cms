package org.itachi.codestar.controller;

import org.itachi.codestar.domain.Entrucking;
import org.itachi.codestar.service.EntruckingService;
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
 * Created by kyo on 2018/3/9.
 * @author zhuzhidong
 */

@RestController
@RequestMapping("api/entrucking")
@Validated
public class EntruckingController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(EntruckingController.class);
    @Autowired
    private EntruckingService entruckingService;

    @GetMapping
    public Map<String, Object> entrucking(@Valid @RequestParam("loadingNumber") String loadingNumber ,
                                          @RequestParam("page")Integer page,
                                          @RequestParam("rows") Integer rows) throws Exception {
        return entruckingService.entrucking(loadingNumber,buildPager(page, rows));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        entruckingService.delete(id);
    }

//    @GetMapping("/repair")
//    public Map<String, Object> repair(@Valid @RequestParam("orderNumber") String orderNumber ,
//                                          @RequestParam("page")Integer page,
//                                          @RequestParam("rows") Integer rows) throws Exception {
//        return entruckingService.repair(orderNumber,buildPager(page, rows));
//    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        entruckingService.delete(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Entrucking entrucking) throws Exception {
        entruckingService.create(entrucking);
    }

    @GetMapping("/{id}")
    public Entrucking entrucking(@PathVariable("id") Long id) throws Exception {
        return entruckingService.entrucking(id) ;
    }
    /*@GetMapping("/{id}")s
    public TestDevice testDevice(@PathVariable("id") Long id) {
        return testDeviceService.testDevice(id);
    }*/

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid Entrucking entrucking) throws Exception {
        entruckingService.modify(entrucking);
    }


    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody List<Long> ids)throws Exception {
        return entruckingService.save(ids);
    }

    /**
     * 确认装车发货
     * @param loadingNumber
     * @return
     * @throws Exception
     */
    @PutMapping("/loadingNumber/{loadingNumber}")
    public void entruckingDone(@PathVariable("loadingNumber") String loadingNumber ) throws Exception {
        entruckingService.entruckingDone(loadingNumber);
    }

}
