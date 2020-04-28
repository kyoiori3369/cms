package org.itachi.codestar.repositories.jpa;
import org.itachi.codestar.domain.OrderPlanned;
import org.itachi.codestar.domain.PurchaseParts;
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
public interface JpaPurchasePartsRepository extends JpaRepository<PurchaseParts, Long> {
    int countByPlanId(Long planId);
    int countByPlanIdAndPurchaseStatus(Long planId,String purchaseStatus);
    void deleteByIdIn(Collection<Long> ids);
}
