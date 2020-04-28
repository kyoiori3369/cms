package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 订单管理表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "order_manage")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderManage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //订单编号
    private String orderNumber;
    //客户名称 ID
    private Long customerId;
    //下单时间
    private String orderTime;
    //交货时间
    private String deliveryTime;
    //订单状态
    private int orderStatus;
    //备注
    private String remarks;
    //创建时间
    private String createTime;
    //标识
    private int deleted;
    //保存增量信息
    private Integer increment;
    @Transient
    private Long orderId;
    //客户名称
    @Transient
    private String customerName;
    //客户代码
    @Transient
    private String companyCode;
    //公司名称
    @Transient
    private String companyName;
    @Transient
    private String orderStatusName;
    /**
     * 期望交货日期
     */
    private String expectDate;
}
