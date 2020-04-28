package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 计划单表
 * Created by kyo on 2018/3/9.
 * @author zhuzhidong
 */

@Entity
@Table(name = "purchase")
@Data
@NoArgsConstructor
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //计划单号
    private String planNumber;
    //采购单
    @Transient
    private String purchaseCode;
    //计划时间
    private String planTime;
    //订单更新时间
    private String orderUpdateTime;
    //订单状态
    private String orderStatus;
    //入库状态
    private String isStatus;
    //订单进度
    @Transient
    private int orderProgress;
    //订单明细
    private String orderDetails;
    //保存增量信息
    private Integer increment;
    //入库时间
    private String createTime;
    //标识
    private int deleted;
}
