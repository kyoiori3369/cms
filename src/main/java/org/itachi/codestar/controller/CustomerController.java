package org.itachi.codestar.controller;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.domain.Customer;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.service.CustomerService;
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
@RequestMapping("/api/customer")
@Validated
@Slf4j
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Map<String, Object> findBy(@Valid @RequestParam("companyName") String companyName,
                                        @RequestParam("page")Integer page,
                                        @RequestParam("rows") Integer rows) throws Exception {
        Pager pager = buildPager(page, rows);
        return customerService.findCustomer(companyName,pager);
    }


    @GetMapping("/findUser/{customerId}")
    public Map<String, Object> findUser(@Valid @PathVariable("customerId") Long customerId) throws Exception {
        return customerService.findCustomerUser(customerId);
    }

    @GetMapping("/search")
    public Map<String, Object> search() throws Exception {
        return customerService.findCustomerAllService();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Customer customer) throws Exception {
        customerService.customerSave(customer);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid Customer service) throws Exception {
        customerService.modifySave(service);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id)throws Exception {
        customerService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids)throws Exception {
        customerService.customerDelete(ids);
    }
    @GetMapping("/{id}")
    public Customer customerOne(@PathVariable("id") Long id) throws Exception {
        return customerService.findCustomerOne(id);
    }

}
