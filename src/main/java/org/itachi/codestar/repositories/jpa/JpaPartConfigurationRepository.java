package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.PartConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by kyo on 2018/3/7.
 * liuhongying
 */
@Repository
public interface JpaPartConfigurationRepository extends JpaRepository<PartConfiguration, Long> {
    /**
     * 根据零件名称查询
     * @param partName
     * @return
     */
    int countByPartName(String partName);
    /**
     * 根据零件名称分页查询
     * @param partName
     * @param pageable
     * @return
     */
    Page<PartConfiguration> findByPartName(String partName, Pageable pageable);
    /**
     * 根据id批量删除零件配置数据
     * @param ids 应用id集合
     */
    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);

}


