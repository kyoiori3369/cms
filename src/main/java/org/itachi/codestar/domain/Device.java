package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 设备表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Table(name = "device_t")
@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //设备编号
    private String deviceNumber;
    //设备名称
    private String deviceName;
    //设备规则
    private String deviceRule;
    //设备状态
    private String deviceStatus;
    //备注
    private String remarks;
//    //设备标准
//    private String deviceStandard;
    private int isCore;
    //保存增量信息
    private int increment;
    //入库时间
    private String storageTime;

}
