package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaAdminUserRepository;
import org.springframework.stereotype.Component;

/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 14:53
 *
 * @author itachi
 */
@Component
public class AdminUserRepositoryPopulator extends RepositoryPopulator {

    public AdminUserRepositoryPopulator() {
        super("users.json", JpaAdminUserRepository.class);
    }

}
