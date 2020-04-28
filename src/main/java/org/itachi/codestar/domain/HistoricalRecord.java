package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 零件出入口历史表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "historical_records")
@Data
@NoArgsConstructor
public class HistoricalRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //零件ID
    private Long partId;
    //零件编号
    private String partCode;
    //零件名称
    private String partName;
    //零件品牌
    private String partBrand;
    //零件渠道
    private String procedureStatus;
     //零件单价
    private Double  partPrice;
    //规则
    private String ruleModel;
    //出入状态（0出，1进）
    private String status;
    //出入库时间
    private String outAndEndTime;
    //数量
    private String number;
    //采购单号
    private String purchaseCode;
    //计划单号
    private String planCode;


}
