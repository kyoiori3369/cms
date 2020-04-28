package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.PartConfiguration;
import org.itachi.codestar.domain.PlannedScheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 *计划排期管理接口
 * @author itachi
 * @since 2018/3/16 00:00
 */
public interface JpaPlannedSchedulingRepository extends JpaRepository<PlannedScheduling,Long > {

    int countByPlanDeviceId(Long planDeviceId);

    int countByPlanDeviceIdAndIsStatus(Long planDeviceId,int isStatus);

    int countByPlanDeviceIdAndAmStatus(Long planDeviceId,int isStatus);
    int countByPlanDeviceIdAndAmStatusAndAssemblingStatusIsNot(Long planDeviceId,int isStatus,int assemblingStatus);

    List<PlannedScheduling> findByPlanDeviceId(Long planDeviceId);

    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);
}
