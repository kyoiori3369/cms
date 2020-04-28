package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 *装车发货表
 * Created by kyo on 2018/3/18.
 * @author zhuzhidong
 */

@Entity
@Table(name = "entrucking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Entrucking implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 设备编号
     */
    private Long deviceId;
    @Transient
    private String deviceNumber;
    @Transient
    private String deviceName;
    /**
     * 装车单号
     */
    private String loadingNumber;
    /**
     * 装车时间
     */
    private Long customerId;
    /**
     * 设备编号
     */
    private String planCode;

    /**
     * 公司名称
     */
    @Transient
    private String companyName;
    /**
     * 公司代号
     */
    @Transient
    private String companyCode;
    private String loadingTime;
    private int status;

    //保存增量信息
    private Integer increment;

    //入库时间
    private String storageTime;

    /**
     * 订单号
     */
    @Transient
    private String orderNumber;
    @Transient
    private String orderTime;

    /**
     * 发货单号
     */
    private Long assemblingId;
    /**
     * 维修时间
     */
    @Transient
    private String repairTime;

    private Long planDeviceId;
}
