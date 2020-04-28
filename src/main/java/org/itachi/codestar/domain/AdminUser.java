package org.itachi.codestar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 14:40
 *
 * @author itachi
 */
@Entity
@Table(name = "admin_users")
@Data
@NoArgsConstructor
public class AdminUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userNo;
    private String userName;
    private String password;
    private Long roleId;
    private String roleNo;
    private String roleName;
    private String lastLoginIP;
    private Long lastLoginTime;
    private Long lastLogoutTime;
    private Integer loginTimes;
    private String adminList;
    private Long departmentId;
    private String departmentNo;
    private String duty;
    private String relation;
    private Long createTime;
    private Long updateTime;
    private Integer deleted;

}
