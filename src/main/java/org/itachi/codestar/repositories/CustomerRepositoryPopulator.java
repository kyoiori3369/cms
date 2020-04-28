package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaCustomerRepository;
import org.springframework.stereotype.Component;

/**
 * Created by kyo on 2018/3/7.
 */
@Component
public class CustomerRepositoryPopulator extends RepositoryPopulator {
    public CustomerRepositoryPopulator() {
        super("customer.json", JpaCustomerRepository.class);
    }
}
