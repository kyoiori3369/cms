package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 设备标准表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "device_standard")
@Data
@NoArgsConstructor
public class DeviceStandard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //设备ID
    private Long deviceId;
    //零件ID
    private Long partId;
    //零件编号
    @Transient
    private String partCode;
    //零件名称
    @Transient
    private String partName;
    //零件规则
    @Transient
    private String ruleModel;
    //零件数量
    private int partCount;
    //备注
    private String partRemarks;
    //零件状态
    @Transient
    private String partStatus;
    //零件品牌
    @Transient
    private String partBrand;

    private int deleted;

}
