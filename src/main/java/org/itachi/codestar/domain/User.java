package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 测试，jpa示例使用
 * User: itachi
 * Date: 2018/3/8
 * Time: 17:54
 *
 * @author itachi
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "userComplex",
                classes = {
                        @ConstructorResult(
                                targetClass = UserComplex.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "user_id", type = Long.class),
                                        @ColumnResult(name = "account"),
                                        @ColumnResult(name = "content")
                                }
                        )
                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "findUserComplex",
                query = "select a.account, b.* from users a inner join user_expand b on a.id=b.user_id",
                resultSetMapping = "userComplex")
})
@NamedQueries({
        @NamedQuery(name = "User.findUsers", query = "select u from User u")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account;
    private String password;

}
