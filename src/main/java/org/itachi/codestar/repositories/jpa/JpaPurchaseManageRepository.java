package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.PurchaseManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 15:17
 *
 * @author itachi
 */
@Repository
public interface JpaPurchaseManageRepository extends JpaRepository<PurchaseManage, Long> {
    int countByPurchaseCode(String purchaseCode);
    Page<PurchaseManage> findByPurchaseCode(String purchaseCode, Pageable pageable);



}
