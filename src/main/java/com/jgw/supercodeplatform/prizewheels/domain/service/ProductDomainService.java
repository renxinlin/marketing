package com.jgw.supercodeplatform.prizewheels.domain.service;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 该服务涉及技术实现,接口提供
 */
@Service
public interface ProductDomainService {

    /**
     * 调用码管理完成生码批次绑定
     * @param products
     */
    List<Product> initSbatchIds(List<Product> products);

    /**
     * 完成大转盘在码管理的逻辑
     * @param products
     */
    void executeBizWhichCodeManagerWant(List<Product> products);

    void removeOldProduct(List<Product> byPrizeWheelsId);

    default List<Product> initPrizeWheelsId(List<Product> products, Long prizeWheelsid){
        products.forEach(product -> {
            product.setActivitySetId(prizeWheelsid);
        });
        return products;
    }

    default boolean isPrizeWheelsMatchThisBatchId(List<Product> products, String sbatchId){
        Asserts.check(!CollectionUtils.isEmpty(products), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils.isEmpty(sbatchId), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        for(Product product : products){
            if(!StringUtils.isEmpty(product.getSbatchId() ) && product.getSbatchId().contains(sbatchId) ){
                return true;
            }
        }
        throw new RuntimeException(ErrorCodeEnum.BIZ_VALID_ERROR.getErrorMessage());
    }

    default void checkSbatchId(List<Product> products){
        for(Product product:products){
            if(StringUtils.isEmpty(product.getSbatchId())){
                throw new RuntimeException("产品"+ product.getProductName() + "未绑定码");
            }

        }
    }
}
