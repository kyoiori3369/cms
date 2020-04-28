package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 计划采购零件表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "order_planned")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderPlanned implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //计划id
    private Long planId;
    //采购单
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
    //工序类型
    @Transient
    private String partSource;
    //采购状态
    private int pluckStatus;
    //入库状态
    private int intStatus;
    //标识
    private int deleted;

}
