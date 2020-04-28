package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 临时采购表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "warehouse_temp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WarehouseTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //计划单
    private String planSheetCode;
    //零件ID
    private Long partId;
    //零件编号
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
    //采购数量
    private int purchaseCount;
    //状态
    private String isStatus;
    //标识
    private int deleted;
    /**
     * 阀值
     */
    private int threshold;


}
