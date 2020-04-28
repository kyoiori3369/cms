package org.itachi.codestar.repositories;

import org.itachi.codestar.repositories.jpa.JpaPurchaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by kyo on 2018/3/9   .
 * @author zhuzhidong
 */
@Component
public class PurchaseRepository extends RepositoryPopulator {
    public PurchaseRepository() {
        super("purchase.json", JpaPurchaseRepository.class);
    }
}
