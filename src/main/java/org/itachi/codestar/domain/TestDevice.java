package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kyo on 2018/3/7.
 */
@Entity
@Table(name = "test_device")
@Data
@NoArgsConstructor
public class TestDevice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //订单编号
    private String orderNumber;
    //设备编号
    private String deviceNumber;
    //设备名称
    private String deviceName;
    //计划时间
    private String plannedTime;
    //出库时间
    private String outageTime;
    //装配时间
    private String assemblyTime;
    //调试时间
    private String debugTime;
    //调试状态
    private String debugState;
    //备注
    private String remarks;
    //装车状态
    private String loadingState;
    //装车时间
    private String loadingTime;
    //装车编号
    private String loadingNumber;
    //计量
    private int metering;
    //保存增量信息
    private int increment;
    //订单ID
    private int orderId;

}
