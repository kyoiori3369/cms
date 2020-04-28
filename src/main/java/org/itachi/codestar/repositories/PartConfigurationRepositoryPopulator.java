package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaPartConfigurationRepository;
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
public class PartConfigurationRepositoryPopulator extends RepositoryPopulator {
    public PartConfigurationRepositoryPopulator() {
        super("partConfiguration.json", JpaPartConfigurationRepository.class);
    }
}
