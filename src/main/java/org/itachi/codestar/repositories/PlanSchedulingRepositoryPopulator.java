package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaPlannedSchedulingRepository;
import org.springframework.stereotype.Component;

/**
 *计划管理
 * @author itachi
 * @since 2018/3/16 00:26
 */
@Component
public class PlanSchedulingRepositoryPopulator extends RepositoryPopulator{
    public PlanSchedulingRepositoryPopulator (){
        super("planScheduling.json", JpaPlannedSchedulingRepository.class);
    }
}
