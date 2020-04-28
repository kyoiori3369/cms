package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采购管理
 * Created by kyo on 2018/3/9.
 * @author zhuzhidong
 */

@Entity
@Table(name = "purchase_manage")
@Data
@NoArgsConstructor
public class PurchaseManage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //采购单
    private String purchaseCode;
    //采购时间
    private String planTime;
    //订单更新时间
    private String orderUpdateTime;
    //期望交货日期
    private String expectDate;
    //订单状态
    private String orderStatus;
    //采购进度
    @Transient
    private int orderProgress;
    //入库状态
    private int isStatus;
    //保存增量信息
    private Integer increment;
    //入库时间
    private String createTime;
    //标识
    private int deleted;
}
