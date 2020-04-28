package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.Entrucking;
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
public interface JpaEntruckingRepository extends JpaRepository<Entrucking, Long> {

    /**
     * 根据装车单号获取记录数
     * @param loadingNumber 应用名
     * @return 记录数
     */
    int countByLoadingNumber(String loadingNumber);
    /**
     * 根据应用名查询分页数据
     * @param pageable 分页参数
     * @return 应用分页数据
     */
    Page<Entrucking> findByLoadingNumber(String loadingNumber, Pageable pageable);

    /**
     * 根据id批量删除应用数据
     * @param ids 应用id集合
     */
    @Transactional(rollbackOn = Throwable.class)
    void deleteByIdIn(Collection<Long> ids);

    int countByIdIn(List<Long> ids);
    /*@Query("select max(t.increment) as increment from TestDevice t where t.storageTime=:#{#storageTime}")
    String findDynamic(@Param("storageTime") String storageTime);*/


    /**
     * 根据装车发货单号查询装车发货信息
     * @param loadingNumber
     * @return
     */
    List<Entrucking> findByLoadingNumber(String loadingNumber);

}
