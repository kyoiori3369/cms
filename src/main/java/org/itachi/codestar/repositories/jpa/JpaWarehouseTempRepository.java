package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.Warehouse;
import org.itachi.codestar.domain.WarehouseTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.*;
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
public interface JpaWarehouseTempRepository extends JpaRepository<WarehouseTemp, Long> {

    /**
     * 根据应用名获取记录数
     * @param planSheetCode 应用名
     * @return 记录数
     */
    int countByPlanSheetCode(String planSheetCode);
    /**
     * 根据应用名查询分页数据
     * @param pageable 分页参数
     * @return 应用分页数据
     */
    Page<WarehouseTemp> findByPlanSheetCode(String planSheetCode, Pageable pageable);

    List<WarehouseTemp> findByPlanSheetCode(String planSheetCode);
    /**
     * 根据id批量删除应用数据
     * @param ids 应用id集合
     */
    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);

}
