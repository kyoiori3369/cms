package org.itachi.codestar.controller;

import org.itachi.codestar.domain.Purchase;
import org.itachi.codestar.service.PurchaseService;
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
@RequestMapping("api/purchase")
@Validated
public class PurchaseController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);
    @Autowired
    private PurchaseService purchaseService;


    @GetMapping
    public Map<String, Object> purchase(@Valid @RequestParam("planNumber") String planNumber,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("rows") Integer rows) throws Exception {
    return purchaseService.purchases(planNumber ,buildPager(page,rows));
    }
    @GetMapping("purchaseIn")
    public Map<String, Object> purchaseIn(@Valid @RequestParam("isStatus") String isStatus,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        return purchaseService.purchaseIn(isStatus ,buildPager(page,rows));
    }
    @GetMapping("/manage")
    public Map<String, Object> purchaseManage(@Valid @RequestParam("planNumber") String planNumber,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        return purchaseService.purchaseManage(planNumber ,buildPager(page,rows));
    }
    @PostMapping("addPurchase/{expectDate}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPurchase(@RequestBody List<Long> ids,@PathVariable String expectDate) {
        purchaseService.addPurchase(ids,expectDate);
    }
    @PostMapping("updatePurchase")
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePurchase(@RequestBody List<Long> ids) {
        purchaseService.updatePurchase(ids);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        purchaseService.delete(id);
    }

    @PostMapping("/plan")
    @ResponseStatus(HttpStatus.CREATED)
    public void planSheetSize(@RequestBody List<Long> ids) {
        purchaseService.planSheetSize(ids);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        purchaseService.delete(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Purchase purchase) throws Exception {
        purchaseService.create(purchase);
    }
    @GetMapping("/{id}")
    public Purchase purchase(@PathVariable("id") Long id) throws Exception {
        return purchaseService.purchase(id) ;
    }
    /*@GetMapping("/{id}")s
    public TestDevice testDevice(@PathVariable("id") Long id) {
        return testDeviceService.testDevice(id);
    }*/

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid Purchase purchase) throws Exception {
        purchaseService.modify(purchase);
    }



}
