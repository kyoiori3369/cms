package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaDeviceRepository;
import org.springframework.stereotype.Component;

/**
 * Created by kyo on 2018/3/7.
 */
@Component
public class DeviceRepositoryPopulator extends RepositoryPopulator {
    public DeviceRepositoryPopulator() {
        super("device.json", JpaDeviceRepository.class);
    }
}
