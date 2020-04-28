package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.OrderDevice;
import org.itachi.codestar.domain.PlanDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 15:17
 *
 * @author itachi
 */
@Repository
public interface JpaPlanDeviceRepository extends JpaRepository<PlanDevice, Long> {

    int countByPlanDeviceId(Long planDeviceId);

    int countByPlanDeviceIdAndStatus(Long planDeviceId,String status);

    int countByStatus(String status);

    int countByOrderIdAndStatusOrOutStatus(Long orderId,String status,String outStatus);

    int countByOrderIdAndStatus(Long orderId,String status);

    List<PlanDevice> findByPlanDeviceIdAndStatus(Long planDeviceId,String status);
}
