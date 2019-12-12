package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CommonConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ProductRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.ProductService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.ProductMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.ProductPojoTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Slf4j
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
        log.info("大转盘覆盖存在的产品{}", JSONObject.toJSONString(products));
        // 同时删除出入库只有产品没有产品批次Id
        // toDO 这里带了业务，提出去
        List<String> deleteChuRuKu = new ArrayList<>();
        products.stream().forEach(product -> {
            String productBatchId = product.getProductBatchId();
            String productId = product.getProductId();
            if(!StringUtils.isEmpty(productId) && StringUtils.isEmpty(productBatchId)){
                deleteChuRuKu.add(productId);
            }
        });
        // 刪除出入ku
        QueryWrapper<ProductPojo> deleteChuRuKuwrapper1 = new QueryWrapper<>();
        deleteChuRuKuwrapper1.in("ProductId",deleteChuRuKu);
        deleteChuRuKuwrapper1.isNull("ProductBatchId");
        productMapper.delete(deleteChuRuKuwrapper1);

        // 删除当前的产品已经存在于数据库的
        QueryWrapper<ProductPojo> wrapper = new QueryWrapper<>();
        wrapper.in("ProductBatchId",productBatchIds);
        productMapper.delete(wrapper);

        // 更新时删除这个活动之前选择,此次没有选择的
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
        // TODO AJBDH Asserts.check(!CollectionUtils.isEmpty(productPojos), ErrorCodeEnum.NOT_EXITS_ERROR.getErrorMessage());
        return productPojoTransfer.tranferPojosToDomains(productPojos);
    }

    @Override
    public List<ProductPojo> getPojoByPrizeWheelsId(Long prizeWheelsid) {
        QueryWrapper<ProductPojo> wapper = new QueryWrapper<>();
        wapper.eq("ActivitySetId",prizeWheelsid);
        return productMapper.selectList(wapper);
    }

    public List<ProductPojo> getPojoByBatchId(String productBatchId) {
        return null;
    }

    @Override
    public List<ProductPojo> getPojoByBatchId(String productId, String productBatchId) {
        Asserts.check(!StringUtils.isEmpty(productId),"产品不存在");
        QueryWrapper<ProductPojo> wapper = new QueryWrapper<>();
        wapper.eq(!StringUtils.isEmpty(productBatchId) && !CommonConstant.NULL.equalsIgnoreCase(productBatchId),"ProductBatchId",productBatchId);
        wapper.eq("productId",productId);
        wapper.eq("Type", ActivityTypeConstant.wheels);
        List<ProductPojo> productPojos = productMapper.selectList(wapper);
        return productPojos;
    }
}
