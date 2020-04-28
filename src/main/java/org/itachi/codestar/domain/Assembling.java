
package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 设备装配表
 */

@Table(name = "equipment_assembly")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assembling implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //排期ID
    private Long planDeviceId;
    //订单ID
    private Long orderId;
    //设备ID
    private Long deviceId;
    //计划时间
    private String purchaseTime;
    //出库时间
    private String outTime;
    //出库单号
    private String outCode;
    //保存增量信息
    private Integer increment;
    //订单编号
    @Transient
    private String orderNumber;
    //排期编号
    private String planCode;
    //设备编号
    @Transient
    private String deviceNumber;
    //设备名称
    @Transient
    private String deviceName;
    /**
     * 装配时间
     */
    private String assemblyTime;
    /**
     * 完成状态
     */
    private String status;
    /**
     * 库存总量
     */
    @Transient
    private int partsSum;
    //完成进度
    @Transient
    private int outPercent;
    private int deleted;
    //调试时间
    private String debugTime;
    //调试状态
    private int debugState;
    //备注
    private String remarks;
    //装车状态
    private int loadingState;
    //装车时间
    private String loadingTime;
    //装车编号
    private String loadingNumber;

    /**
     * 返回状态   0:未开始   1:NG   2:OK
     */
    @Transient
    private String flagStatus;
    /**
     * 维修时间
     */
    private String repairTime;
    /**
     * 操作人
     */
    private String operator;
}

