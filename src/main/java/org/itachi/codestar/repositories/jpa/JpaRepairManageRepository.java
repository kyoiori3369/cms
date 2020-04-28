package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.Entrucking;
import org.itachi.codestar.domain.RepairManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface JpaRepairManageRepository extends JpaRepository<RepairManage, Long> {

    int countByLoadingId(Long loadingId);
    /**
     * 根据上传人查询维修信息
     * @param
     * @return
     */
    Page<RepairManage> findByLoadingId(Long loadingId, Pageable pageable);


    /**
     * 根据id批量删除维修管理信息
     * @param ids 应用id集合
     */
    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);
}
