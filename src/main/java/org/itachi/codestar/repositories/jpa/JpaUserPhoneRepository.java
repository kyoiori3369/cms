package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.PlannedScheduling;
import org.itachi.codestar.domain.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 *用户电话
 * @author itachi
 * @since 2018/3/16 00:00
 */
public interface JpaUserPhoneRepository extends JpaRepository<UserPhone,Long > {
    List<UserPhone> findByCustomerId(Long customerId);

    @Transactional(rollbackOn = Throwable.class)
    void deleteByCustomerId(Long customerId);

}
