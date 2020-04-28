package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2018/3/8.
 * User: itachi
 * Date: 2018/3/8
 * Time: 18:00
 *
 * @author itachi
 */
public interface JpaUserRepository extends JpaRepository<User, Long> {

    /**
     * 带复杂map参数语句示例，但是这里不支持动态语句。动态语句的写法它是通过代码逻辑实现，并不能在模板中实现。
     * 这个注解就等价于mybatis中的mapper xml文件中语句。
     * @param params
     * @return
     */
    @Query("select u from User u where u.account=:#{#params.get('name')} and u.password=:#{#params.get('password')}")
    User findDynamic(@Param("params") Map<String, Object> params);

    /**
     * 当然它还支持这样的实体参数。至于每个参数是基本类型的方式相信大家都清楚了，我就不举例了。
     * @param params
     * @return
     */
    @Query("select u from User u where u.account=:#{#params.account} and u.password=:#{#params.password}")
    User findDynamic(@Param("params") User params);

    /**
     * 在实体头上标注本地查询的方式
     * @return
     */
    @Query(nativeQuery = true, name = "findUserComplex")
    List findUserComplex();

    /**
     * 在实体上标注hql查询方式
     * @return
     */
    List<User> findUsers();
}
