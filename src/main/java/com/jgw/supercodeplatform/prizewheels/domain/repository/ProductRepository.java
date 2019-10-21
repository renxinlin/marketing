package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository {
    void saveButDeleteOld(List<Product> products);

    int deleteByPrizeWheelsId(Long id);

    List<Product> getByPrizeWheelsId(Long prizeWheelsid);

    List<ProductPojo> getPojoByPrizeWheelsId(Long prizeWheelsid);

    List<ProductPojo> getPojoByBatchId(String productBatchId);
}
