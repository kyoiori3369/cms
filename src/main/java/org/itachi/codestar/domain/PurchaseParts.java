package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 采购零件管理
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "purchase_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseParts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //采购ID
    private Long planId;
    //采购单号
    private String purchaseCode;;
    //零件id
    private Long partId;
    //采购数量
    private int purchaseCount;
    //采购状态
    private String purchaseStatus;
    @Transient
    private String partCode;
    //零件名称
    @Transient
    private String partName;
    //零件品牌
    @Transient
    private String partBrand;
    //规则
    @Transient
    private String ruleModel;
    //库存量
    @Transient
    private String numberParts;
    //渠道
    @Transient
    private String partSource;
    //入库状态
    private int intStatus;
    //标识
    private int deleted;
    /**
     * 零件单价
     */
    @Transient
    private String  partPrice;
    /**
     * 零件供货商
     */
    @Transient
    private String partSupplier;

}
