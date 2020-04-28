package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * TODO
 * 计划排零件管理
 * @author itachi
 * @since 2018/3/15 23:55
 */
@Entity
@Table(name = "planned_scheduling")
@Data
@NoArgsConstructor
public class PlannedScheduling implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //设备明细ID
    private long planDeviceId;
    //设备ID
    private Long deviceId;
    //零件号
    private long partId;
    //
    private long orderId;
    /**
     * 零件编号
     */
    @Transient
    private String partCode;
    /**
     * 零件名称
     */
    @Transient
    private String partName;
    /**
     * 零件品牌
     */
    @Transient
    private String partBrand;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 零件渠道
     */
    @Transient
    private String partSource;
    /**
     * 零件单价
     */
    @Transient
    private Double  partPrice;
    /**
     * 零件图
     */
    @Transient
    private String partImage;

    /**
     * 零件供货商
     */
    @Transient
    private String partSupplier;
    /**
     * 规则/型号
     */
    @Transient
    private String ruleModel;
    /**
     * 零件状态
     */
    @Transient
    private  String partState;
    /**
     * 零件数量
     */
    private int numberParts;
    /**
     * 库存总量
     */
    @Transient
    private int partsSum;
    /**
     * 出库状态
     */
    private int isStatus;
    /**
     * 装配状态
     */
    private int amStatus;

    private int status;

    private int deleted;
    /**
     * 需要出库的零件
     */
    private int outStatus;
    /**
     * 需要装配的零件
     */
    private int assemblingStatus;

    /**
     * 装配状态
     */
    @Transient
    private String atStatus;
}
