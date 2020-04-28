package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.Device;
import org.itachi.codestar.domain.DeviceStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface JpaDeviceStandardRepository extends JpaRepository<DeviceStandard, Long> {

    /**
     * 根据应用名获取记录数
     * @param deviceId 应用名
     * @return 记录数
     */
    int countByDeviceId(Long deviceId);
    int countByDeviceIdAndPartId(Long deviceId,Long partId);

    List<DeviceStandard> findByDeviceId(Long deviceId);
    /**
     * 根据应用名查询分页数据
     * @param pageable 分页参数
     * @return 应用分页数据
     */
    Page<DeviceStandard> findByDeviceId(Long deviceId, Pageable pageable);

    /**
     * 根据id批量删除应用数据
     * @param ids 应用id集合
     */
    void deleteByIdIn(Collection<Long> ids);

    void deleteByPartIdIn(Collection<Long> partId);

}
