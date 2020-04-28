package org.itachi.codestar.repositories.jpa;

import org.itachi.codestar.domain.OrderDevice;
import org.itachi.codestar.domain.OrderManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface JpaOrdersRepository extends JpaRepository<OrderManage, Long> {

    /**
     * 根据应用名获取记录数
     * @param orderNumber 应用名
     * @return 记录数
     */
    int countByOrderNumberAndDeleted(String orderNumber,int deleted);
    int countByDeleted(int deleted);
    int countByIdAndOrderStatus(Long id,int orderStatus);

    /**
     * 订单撤销
     * @param orderdevice
     */
    @Transactional
    @Modifying
    @Query("update OrderDevice u set u.status =:#{#orderdevice.status} where u.id=:#{#orderdevice.id}")
     void updateStatus(@Param("orderdevice") OrderDevice orderdevice);


/*   @Query("select max(u.increment) as increment from OrderManage u where u.storageTime=:#{#storageTime}")
   String findDynamic(@Param("storageTime") String storageTime);*/
    /**
     * 根据应用名查询分页数据
     * @param orderNumber 应用名
     * @param pageable 分页参数
     * @return 应用分页数据
     */
    Page<OrderManage> findByOrderNumber(String orderNumber, Pageable pageable);

    /**
     * 根据id批量删除应用数据
     * @param ids 应用id集合
     */
    void deleteByIdIn(Collection<Long> ids);
}
