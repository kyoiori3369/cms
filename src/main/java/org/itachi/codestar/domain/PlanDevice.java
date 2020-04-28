package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 计划管理表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "plan_device")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanDevice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //设备排期ID
    private Long planDeviceId;
    //计划排期编号
    private String planCode;
    //订单ID
    private Long orderId;
    //设备ID
    private Long deviceId;
    //订单编号
    @Transient
    private String orderNumber;
    //交货时间
    @Transient
    private String deliveryTime;
    //客户名称
    @Transient
    private String companyName;
    @Transient
    private String companyCode;
    //设备编号
    @Transient
    private String deviceNumber;
    //设备名称
    @Transient
    private String deviceName;
    //设备规则
    @Transient
    private String deviceRule;
    //下单时间
    @Transient
    private String orderTime;
    @Transient
    private String isStatus;
    @Transient
    private int outPercent;
    //计划时间
    private String planTime;
    //状态
    private String status;
    //出库状态
    private String outStatus;
    //装配进度
    @Transient
    private int assemblingStatus;
    @Transient
    private String flagStatus;
    @Transient
    private String loadingState;
    //创建时间
    private String createTime;
    //保存增量信息
    private Integer increment;
    //标识
    private int deleted;

}
