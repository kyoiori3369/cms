package org.itachi.codestar.domain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 零件表
 * Created by itachi on 2018/3/8.
 * User: itachi
 * Date: 2018/3/8
 * Time: 15:23
 * 零件配置
 * @author itachi
 */
@Entity
@Table(name = "part_configuration")
@Data
@NoArgsConstructor
public class PartConfiguration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 零件编号
     */
    private String partCode;
    /**
     * 零件名称
     */
    private String partName;
    /**
     * 零件品牌
     */
    private String partBrand;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 零件渠道
     */
    private String partSource;
    /**
     * 零件单价
     */
    private Double  partPrice;
    /**
     * 零件图
     */
    private String partImage;

    /**
     * 零件供货商
     */
    private String partSupplier;
    /**
     * 规则/型号
     */
    private String ruleModel;
    /**
     * 零件状态
     */
    private  String partState;
    /**
     * 零件数量
     */
    private long numberParts;
    /**
     * 删除标识
     */
    private int deleted;
    /**
     * 阀值
     */
    private int threshold;
    /*
     *需要采购数量
     */
    @Transient
    private Long purchaseCount;
}
