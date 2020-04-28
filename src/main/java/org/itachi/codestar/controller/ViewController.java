package org.itachi.codestar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by itachi on 2018/1/17.
 * User: itachi
 * Date: 2018/1/17
 * Time: 17:24
 *
 * @author itachi
 */
@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/menu")
    public String menu() throws Exception {
        return "menu";
    }

    @GetMapping
    public String index() throws Exception {
        return "order/orderIndex";
    }

    @GetMapping("/welcome")
    public String welcome() throws Exception {
        return "welcome";
    }

    @GetMapping("/header")
    public String header() throws Exception {
        return "header";
    }

    @GetMapping("/entrucking")
    public String entrucking() throws Exception {
        return "device/entrucking";
    }

    @GetMapping("/device")
    public String services() throws Exception {
        return "device/list";
    }

    @GetMapping("/repair")
    public String repair() throws Exception {
        return "device/repair";
    }

    @GetMapping("/repairDetailed")
    public String repairDetailed() throws Exception {
        return "device/repairDetailed";
    }
    @GetMapping("/deviceDetail")
    public String deviceDetail() throws Exception {
        return "device/deviceDetail";
    }
    @GetMapping("/devicePart")
    public String devicePart() throws Exception {
        return "device/devicePart";
    }

    @GetMapping("/order")
    public String order() throws Exception {
        return "order/orderManage";
    }
    @GetMapping("/index")
    public String orderIndex() throws Exception {
        return "order/orderIndex";
    }
    @GetMapping("/customer")
    public String customer() throws Exception {
        return "order/customer";
    }

    @GetMapping("/purchase")
    public String purchase() throws Exception {
        return "purchase/list";
    }

    @GetMapping("/purchases")
    public String purchases() throws Exception {
        return "purchase/purchase";
    }
    @GetMapping("purchaseManage")
    public String purchaseManage() throws Exception {
        return "purchase/purchaseManage";
    }
    @GetMapping("purchaseDetail")
    public String purchaseDetail() throws Exception {
        return "purchase/purchaseDetail";
    }
    @GetMapping("/planManagement")
    public String configuration() throws Exception {
        return "planManagement/configuration";
    }

    @GetMapping("/planManage")
    public String planManage() throws Exception {
        return "planManagement/planManage";
    }
    @GetMapping("/partDetail")
    public String partDetail() throws Exception {
        return "planManagement/partDetail";
    }
    @GetMapping("/plannedScheduling")
    public String plannedScheduling() throws Exception {
        return "planManagement/plannedScheduling";
    }

    @GetMapping("/assembling")
    public String assembling() throws Exception {
        return "planManagement/assembling";
    }
    @GetMapping("/amDetail")
    public String amDetail() throws Exception {
        return "planManagement/assemDetail";
    }
    @GetMapping("/deviceManage")
    public String device() throws Exception {
        return "order/device";
    }

    @GetMapping("/warehouse")
    public String warehouse() throws Exception {
        return "order/warehouse";
    }

    @GetMapping("/warehouseTemp")
    public String warehouseTemp() throws Exception {
        return "order/warehouseTemp";
    }

    @GetMapping("/warehousePreview")
    public String warehousePreview() throws Exception {
        return "order/warehousePreview";
    }

    @GetMapping("/addDevice")
    public String addDevice() throws Exception {
        return "order/addDevice";
    }

    @GetMapping("/partConfig")
    public String partConfig() throws Exception {
        return "order/partConfig";
    }

    @GetMapping("/shipment")
    public String shipment() throws Exception {
        return "order/shipment";
    }

    @GetMapping("/storage")
    public String storage() throws Exception {
        return "order/storage";
    }

    @GetMapping("/storageDetailed")
    public String storageDetailed() throws Exception {
        return "order/storageDetailed";
    }

    @GetMapping("/detailed")
    public String outDetailed() throws Exception {
        return "planManagement/outDetailed";
    }

    @GetMapping("test")
    public String test() throws Exception {
        return "test";
    }
    @GetMapping("history")
    public String warehouseDetailed()throws Exception{
        return "order/warehouseDetailed";
    }

}
