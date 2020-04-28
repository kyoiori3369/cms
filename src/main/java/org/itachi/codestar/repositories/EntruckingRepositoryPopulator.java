package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaEntruckingRepository;
import org.springframework.stereotype.Component;

/**
 * Created by kyo on 2018/3/9   .
 * @author zhuzhidong
 */
@Component
public class EntruckingRepositoryPopulator extends RepositoryPopulator {
    public EntruckingRepositoryPopulator() {
        super("entrucking.json", JpaEntruckingRepository.class);
    }
}
