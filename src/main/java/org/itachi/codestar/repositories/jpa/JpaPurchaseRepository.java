package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.Purchase;
import org.itachi.codestar.domain.TestDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by kyo on 2018/3/9.
 * @author zhuzhidong
 */

@Repository
public interface JpaPurchaseRepository extends JpaRepository<Purchase, Long> {

    int countByOrderStatus(String status);
    int countByPlanNumber(String status);

    Page<TestDevice> findByPlanNumberAndOrderStatus(String planNumber,String status, Pageable pageable);

    void deleteByIdIn(Collection<Long> ids);
   /* @Query("select max(u.increment) as increment from OrderManage u where u.storageTime=:#{#storageTime}")
    String findDynamic(@Param("storageTime") String storageTime);*/

}


