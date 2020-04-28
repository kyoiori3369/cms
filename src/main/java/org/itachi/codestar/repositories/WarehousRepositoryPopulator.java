package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaOrdersRepository;
import org.itachi.codestar.repositories.jpa.JpaWarehouseRepository;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/11 15:39
 */
@Component
public class WarehousRepositoryPopulator extends RepositoryPopulator{
    public WarehousRepositoryPopulator(){
        super("warehouse.json", JpaWarehouseRepository.class);
    }

}
