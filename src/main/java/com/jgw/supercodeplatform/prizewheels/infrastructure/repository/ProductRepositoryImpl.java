package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ProductRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.ProductService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.ProductMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.ProductPojoTransfer;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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

    /**
     * 删除旧的产品
     * @param products
     */
    @Override
    public void saveButDeleteOld(List<Product> products) {
        List<String> productBatchIds = products.stream().map(product -> product.getProductBatchId()).collect(Collectors.toList());

        // 删除当前的产品已经存在于数据库的
        QueryWrapper<ProductPojo> wrapper = new QueryWrapper<>();
        wrapper.in("ProductBatchId",productBatchIds);
        productMapper.delete(wrapper);
        // 删除这个活动之前选择,此次没有选择的
        deleteByPrizeWheelsId(products.get(0).getActivitySetId());

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


    @Override
    public List<Product> getByPrizeWheelsId(Long prizeWheelsid) {
        QueryWrapper<ProductPojo> wapper = new QueryWrapper<>();
        wapper.eq("ActivitySetId",prizeWheelsid);
        List<ProductPojo> productPojos = productMapper.selectList(wapper);
        Asserts.check(!CollectionUtils.isEmpty(productPojos), ErrorCodeEnum.NOT_EXITS_ERROR.getErrorMessage());
        return productPojoTransfer.tranferPojosToDomains(productPojos);
    }

    @Override
    public List<ProductPojo> getPojoByPrizeWheelsId(Long prizeWheelsid) {
        QueryWrapper<ProductPojo> wapper = new QueryWrapper<>();
        wapper.eq("ActivitySetId",prizeWheelsid);
        return productMapper.selectList(wapper);
    }

    @Override
    public List<ProductPojo> getPojoByBatchId(String productBatchId) {
        QueryWrapper<ProductPojo> wapper = new QueryWrapper<>();
        wapper.eq("ProductBatchId",productBatchId);
        List<ProductPojo> productPojos = productMapper.selectList(wapper);
        return productPojos;
    }
}
