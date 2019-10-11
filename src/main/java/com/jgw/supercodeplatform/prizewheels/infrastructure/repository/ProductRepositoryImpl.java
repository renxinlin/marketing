package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ProductRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.ProductService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.ProductMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.ProductPojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productBatchService;

    @Autowired
    private ProductPojoTransfer productPojoTransfer;

    @Override
    public void saveButDeleteOld(List<Product> products) {
        List<String> productBatchIds = products.stream().map(product -> product.getProductBatchId()).collect(Collectors.toList());
        QueryWrapper<ProductPojo> wrapper = new QueryWrapper<>();
        wrapper.in("ProductBatchId",productBatchIds);
        productMapper.delete(wrapper);


        List<ProductPojo> productPojos = productPojoTransfer.transferProductsToPojos(products);
        productBatchService.saveBatch(productPojos);

    }

    @Override
    public int deleteByPrizeWheelsId(Long id) {
        QueryWrapper<ProductPojo> wapper = new QueryWrapper<>();
        wapper.eq("ActivitySetId",id);
        int delete = productMapper.delete(wapper);
        return delete;
    }
}
