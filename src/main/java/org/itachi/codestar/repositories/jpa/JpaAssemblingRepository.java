package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.Assembling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *计划排期管理接口
 * @author itachi
 * @since 2018/3/16 00:00
 */
public interface JpaAssemblingRepository extends JpaRepository<Assembling,Long > {
    int countByStatus(String status);
    int countByOrderIdAndLoadingState(Long orderId,int loadingState);
    int countByIdIn(List<Long> ids);
    Assembling findByPlanDeviceId(Long id);
    int countByPlanDeviceId(Long id);
    int countByPlanDeviceIdAndStatus(Long id,String status);

}
