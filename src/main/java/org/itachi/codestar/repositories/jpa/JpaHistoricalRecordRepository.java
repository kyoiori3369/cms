package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.HistoricalRecord;
import org.itachi.codestar.domain.OrderPlanned;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface JpaHistoricalRecordRepository extends JpaRepository<HistoricalRecord, Long> {

    /**
     * 根据应用名获取记录数
     * @param status 应用名
     * @return 记录数
     */
    int countByPartIdAndStatus(Long partId,String status);
    int countByPartId(Long partId);
    /**
     * 根据应用名查询分页数据
     * @param pageable 分页参数
     * @return 应用分页数据
     */
    Page<HistoricalRecord> findByPartIdAndStatus(Long partId,String status, Pageable pageable);
    Page<HistoricalRecord> findByPartId(Long partId, Pageable pageable);
}
