package org.itachi.codestar.repositories.jpa;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.itachi.codestar.domain.User;
import org.itachi.codestar.domain.UserComplex;
import org.itachi.codestar.domain.UserExpand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by itachi on 2018/3/8.
 * User: itachi
 * Date: 2018/3/8
 * Time: 18:03
 *
 * @author itachi
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaUserRepository repository;

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setAccount("admin");
        user.setPassword("111111");
        entityManager.persist(user);
        User user1 = new User();
        user1.setAccount("admin1");
        user1.setPassword("222222");
        entityManager.persist(user1);
        User user2 = new User();
        user2.setAccount("test");
        user2.setPassword("111111");
        entityManager.persist(user2);
        UserExpand expand = new UserExpand();
        expand.setUserId(1L);
        expand.setContent("测试一下");
        entityManager.persist(expand);
        UserExpand expand1 = new UserExpand();
        expand1.setUserId(2L);
        expand1.setContent("test");
        entityManager.persist(expand1);
    }

    /**
     * 动态组合返回map列表形式，但官方已经不建议使用，慎用。
     *
     * @param name
     * @return
     */
    private List<Map<String, Object>> findUsers(String name) {
        Query query = entityManager.getEntityManager().createNativeQuery("select a.account, a.password, b.* from users a inner join user_expand b on a.id=b.user_id where a.account like concat('%', :name, '%')");
        query.setParameter("name", name);
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> users = new ArrayList<>();
        for (Object object : query.getResultList()) {
            Map map = (Map) object;
            Map<String, Object> user = new HashMap<>();
            for (Object key : map.keySet()) {
                user.put(key.toString().toLowerCase(), map.get(key));
            }
            users.add(user);
        }
        return users;
    }

    /**
     * 传参使用方式，建议写在repository上，慎用。
     *
     * @param name
     * @param password
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<User> findUsers(String name, String password) {
        Query query = entityManager.getEntityManager().createNativeQuery("select * from users u where u.account=:name or u.password=:password", User.class);
        query.setParameter("name", name);
        query.setParameter("password", password);
        return (List<User>) query.getResultList();
    }

    /**
     * 语句返回对应的实体方式，需要注意的点比较多。
     *
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<UserComplex> findUserComplex(String name) {
        Query query = entityManager.getEntityManager().createNativeQuery("select a.account, a.password, b.* from users a inner join user_expand b on a.id=b.user_id where a.account like concat('%', :name, '%')", "userComplex");
        query.setParameter("name", name);
        return (List<UserComplex>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    private List findUserExpand() {
        Query query = entityManager.getEntityManager().createNativeQuery("select b.*, a.account, a.password, 'hehe' as add_column from users a inner join user_expand b on a.id=b.user_id", "userComplexView");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    private List<UserExpand> findUserExpandView() {
        Query query = entityManager.getEntityManager().createNativeQuery("select b.*, a.account from users a inner join user_expand b on a.id=b.user_id", "userExpandView");
        return (List<UserExpand>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testJPA() {
        List<Map<String, Object>> list = findUsers("admin");
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).get("password")).isEqualTo("111111");
        assertThat(list.get(1).get("password")).isEqualTo("222222");
        List<User> users = findUsers("admin", "111111");
        assertThat(users).isNotEmpty();
        assertThat(users.get(1).getAccount()).isEqualTo("test");
        Map<String, Object> params = new HashMap<>();
        params.put("name", "admin");
        params.put("password", "111111");
        User user = repository.findDynamic(params);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        List<UserComplex> complexes = findUserComplex("admin");
        assertThat(complexes).isNotEmpty();
        assertThat(complexes.get(0).getContent()).isEqualTo("测试一下");
        assertThat(complexes.get(0).getId()).isEqualTo(1L);
        assertThat(complexes.get(0).getUserId()).isEqualTo(1L);
        assertThat(complexes.get(1).getContent()).isEqualTo("test");
        List expands = findUserExpand();
        assertThat(expands).isNotEmpty();
        List<UserExpand> expandList = findUserExpandView();
        assertThat(expandList).isNotEmpty();
        assertThat(expandList.get(1).getUserName()).isEqualTo("admin1");
        List<UserComplex> complexes1 = repository.findUserComplex();
        assertThat(complexes1).isNotEmpty();
        assertThat(complexes1.get(0).getContent()).isEqualTo("测试一下");
        assertThat(complexes1.get(0).getId()).isEqualTo(1L);
        assertThat(complexes1.get(0).getUserId()).isEqualTo(1L);
        assertThat(complexes1.get(1).getContent()).isEqualTo("test");
        List<User> users1 = repository.findUsers();
        assertThat(users1).isNotEmpty();
        assertThat(users1.get(0).getAccount()).isEqualTo("admin");
    }

}