package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    void saveButDeleteOld(List<Product> products);
}
