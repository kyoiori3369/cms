package org.itachi.codestar.controller;

import org.itachi.codestar.domain.PartConfiguration;
import org.itachi.codestar.service.PartConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
/**
 * Created by itachi on 2018/3/8.
 * User: itachi
 * Date: 2018/3/8
 * Time: 19:30
 *零件配置
 * @author itachi
 */
@RestController
@RequestMapping("/api/partConfiguration")
@Validated
public class PartConfigurationApiController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PartConfigurationApiController.class);
    @Autowired
    private PartConfigurationService  configservice;

    @GetMapping
    public Map<String, Object> configPages(@Valid @RequestParam("partName") String partName,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows) throws Exception {
        return configservice.configPages(partName,buildPager(page,rows));
    }
    @GetMapping("/storage")
    public Map<String, Object> storage(@Valid @RequestParam("partName") String partName,
                                           @RequestParam("page")Integer page,
                                           @RequestParam("rows") Integer rows,
                                           HttpServletRequest request) throws Exception {
        return configservice.storage(partName,buildPager(page,rows));
    }
    @GetMapping("/planned")
    public Map<String, Object> planned(@Valid @RequestParam("orderDeviceId") String orderDeviceId,
                                       @RequestParam("orderDeviceCount") String orderDeviceCount,
                                       @RequestParam("page")Integer page,
                                       @RequestParam("rows") Integer rows,
                                       HttpServletRequest request) throws Exception {
        return configservice.storagePlanned(orderDeviceId,orderDeviceCount,buildPager(page,rows));
    }
    @GetMapping("/search")
    public Map<String, Object> search(@Valid @RequestParam("partName") String partName) throws Exception {
        return configservice.findPartService(partName);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        configservice.delete(ids);
    }


    @GetMapping("/{id}")
    public PartConfiguration partConfiguration(@PathVariable("id") Long id) throws Exception {
        return configservice.partConfig(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid PartConfiguration partConfiguration) throws Exception {
        logger.info("Updating album " + partConfiguration.getId());
        configservice.create(partConfiguration);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modify(@RequestBody @Valid PartConfiguration partConfiguration) throws Exception {
        configservice.modify(partConfiguration);
    }

}
