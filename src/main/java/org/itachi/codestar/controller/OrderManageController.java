package org.itachi.codestar.controller;

import org.itachi.codestar.domain.OrderManage;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.service.OrderManageService;
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
 * Created by itachi on 2018/1/15.
 * User: itachi
 * Date: 2018/1/15
 * Time: 15:50
 *
 * @author itachi
 */
@RestController
@RequestMapping("/api/orderManage")
@Validated
public class OrderManageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderManageController.class);
    @Autowired
    private OrderManageService orderManageService;

    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("orderNumber") String orderNumber,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return orderManageService.findOrder(orderNumber,pager);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OrderManage orderManage) throws Exception {
        logger.info("Updating album " + orderManage.getId());
        orderManageService.orderManageSave(orderManage);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        logger.info("Deleting service " + id);
        orderManageService.deleteById(id);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid OrderManage service) throws Exception {
        orderManageService.modifySave(service);
    }

    @GetMapping("/{id}")
    public OrderManage orderManage(@PathVariable("id") Long id) throws Exception {
        logger.info("Getting service " + id);
        return orderManageService.findOrderManageOne(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void orderManageDelete(@RequestBody List<Long> ids) throws Exception{
        orderManageService.orderManageDelete(ids);
    }

}
