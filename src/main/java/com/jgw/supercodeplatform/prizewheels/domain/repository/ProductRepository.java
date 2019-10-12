package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository {
    void saveButDeleteOld(List<Product> products);

    int deleteByPrizeWheelsId(Long id);

    void batchSave(List<Product> products);
}
