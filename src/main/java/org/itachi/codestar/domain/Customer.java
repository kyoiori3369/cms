package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * 客户管理表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 职位
     */
    private String position;
    /**
     * 电话
     */
    private String phone;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 性别
     */
    private String gender;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司代号
     */
    private String companyCode;
    /**
     * 公司区域
     */
    private String companyArea;
    /**
     * 公司电话
     */
    private String companyPhone;

    private String areaCode;

    private String phoneNumber;
    /**
     * 公司传真
     */
    private String companyFax;

    private String areaCodes;

    private String faxNumber;
    /**
     * 公司地址
     */
    private String  companyAddress;
    /**
     * 公司法人
     */
    private String  companyLegal;
    /**
     * 公司法人电话
     */
    private String  companyLegalPhone;

    /**
     * 省
     */
    private String inputProvince;

    /**
     * 市
     */
    private String inputCity;

    /**
     * 区县
     */
    private String inputArea;
    /**
     * 用户电话
     */
    @Transient
    private List<UserPhone> userPhone;


}
