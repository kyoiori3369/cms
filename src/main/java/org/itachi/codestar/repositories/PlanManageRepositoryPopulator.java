package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaOrdersRepository;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/11 15:39
 */
@Component
public class PlanManageRepositoryPopulator extends RepositoryPopulator{
    public PlanManageRepositoryPopulator (){
        super("planManage.json", JpaOrdersRepository.class);
    }

}
