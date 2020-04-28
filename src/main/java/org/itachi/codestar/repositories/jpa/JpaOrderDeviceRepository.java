package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.OrderDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 15:17
 *
 * @author itachi
 */
@Repository
public interface JpaOrderDeviceRepository extends JpaRepository<OrderDevice, Long> {

    int countByOrderId(Long orderId);

    int countByOrderIdAndDeleted(Long orderId,int deleted);

    int countByOrderIdAndStatusAndDeleted(Long orderId,int status,int delete);
     /**
     * 根据id批量删除应用数据
     * @param ids 应用id集合
     */
    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);

    int countByStatus(String status);

    void deleteAllByOrderIdIn(Collection<Long> orderIds);

}
