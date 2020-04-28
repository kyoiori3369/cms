package org.itachi.codestar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by itachi on 2018/3/8.
 * User: itachi
 * Date: 2018/3/8
 * Time: 17:58
 *
 * @author itachi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserComplex implements Serializable {
    
    private Long id;
    private Long userId;
    private String account;
    private String password;
    private String content;

    public UserComplex(Long id, Long userId, String account, String content) {
        this.id = id;
        this.userId = userId;
        this.account = account;
        this.content = content;
    }

}
