package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //零件编号
    private String partCode;
    //零件名称
    private String partName;
    //零件品牌
    private String partBrand;
    //规则
    private String ruleModel;
    //库存量
    private String warehouseCount;
    //阈值
    private String threshold;
    //工序类型
    private String procedureStatus;

}
