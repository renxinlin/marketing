package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public void saveButDeleteOld(List<Product> products) {

    }
}
