package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaTestDeviceRepository;
import org.springframework.stereotype.Component;

/**
 * Created by kyo on 2018/3/7.
 */
@Component
public class TestDeviceRepository extends RepositoryPopulator {
    public TestDeviceRepository() {
        super("testDevice.json", JpaTestDeviceRepository.class);
    }
}
