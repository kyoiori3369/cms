package org.itachi.codestar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 测试，jpa示例使用。
 * 这里要注意属性与列注解的顺序，是有关的。
 * User: itachi
 * Date: 2018/3/8
 * Time: 17:57
 *
 * @author itachi
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings(
        {
                @SqlResultSetMapping(
                        name = "userComplexView",
                        entities = {
                                @EntityResult(entityClass = UserExpand.class),
                                @EntityResult(entityClass = User.class, fields = {
                                        @FieldResult(name = "id", column = "user_id")
                                })
                        },
                        columns = {
                                @ColumnResult(name = "add_column")
                        }
                ),
                @SqlResultSetMapping(
                        name = "userExpandView",
                        classes = {
                                @ConstructorResult(targetClass = UserExpand.class, columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "user_id", type = Long.class),
                                        @ColumnResult(name = "content"),
                                        @ColumnResult(name = "account"),
                                        @ColumnResult(name = "test_field")
                                })
                        }
                )
        }
)
public class UserExpand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String content;
    @Transient
    private String userName;
    private String testField;

}
