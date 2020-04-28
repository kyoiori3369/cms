package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 订单明细设备表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "order_device")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDevice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //订单id
    private Long orderId;
    //订单编号
    @Transient
    private String orderNumber;
    //客户名称
    @Transient
    private String companyName;
    @Transient
    private String companyCode;
    //设备ID
    private Long deviceId;
    //设备编号
    @Transient
    private String deviceNumber;
    //设备名称
    @Transient
    private String deviceName;
    //设备规则
    @Transient
    private String deviceRule;
    //数量
    private int count;
    //下单时间
    @Transient
    private String orderTime;
    //交货时间
    private String deliveryTime;
    //备注
    private String remarks;
    //计量
    private int metering;
    //状态
    private int status;
    //状态
    private int deleted;
    //订单价格
    private Double  orderPrice;

}
