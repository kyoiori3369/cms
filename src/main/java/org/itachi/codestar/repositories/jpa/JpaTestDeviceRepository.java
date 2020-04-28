package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.TestDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by kyo on 2018/3/7.
 */

@Repository
public interface JpaTestDeviceRepository extends JpaRepository<TestDevice, Long> {

    int countByOrderNumber(String orderNumber);

    Page<TestDevice> findByOrderNumber(String orderNumber, Pageable pageable);

    void deleteByIdIn(Collection<Long> ids);

}


