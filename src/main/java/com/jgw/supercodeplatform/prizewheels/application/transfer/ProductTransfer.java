package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductBatchDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductTransfer {

    @Autowired
    private ModelMapper modelMapper;



    public List<Product>  transferUpdateDtoToDomain(List<ProductUpdateDto> productUpdateDtos, Long activitySetId,byte autoType) {
        List<Product> products = new ArrayList<>();
        for(ProductUpdateDto productDto : productUpdateDtos){
            List<ProductBatchDto> productBatchParams = productDto.getProductBatchParams();
            for(ProductBatchDto productBatchDto : productBatchParams) {
                String productBatchId = productBatchDto.getProductBatchId();
                String productBatchName = productBatchDto.getProductBatchName();
                Product product = modelMapper.map(productDto, Product.class);
                product.setActivitySetId(activitySetId);
                product.setAutoType(autoType);
                product.setProductBatchId(productBatchId);
                product.setProductBatchName(productBatchName);
                product.setUrlByCodeManagerCallBack(CallBackConstant.PRIZE_WHEELS_URL);

                products.add(product);
            }
        }
        return products;


     }

    public List<Product> transferDtoToDomain(List<ProductDto> productDtos, byte autoType) {
        List<Product> products = new ArrayList<>();
        for(ProductDto productDto : productDtos){
            List<ProductBatchDto> productBatchParams = productDto.getProductBatchParams();
            for(ProductBatchDto productBatchDto : productBatchParams) {
                String productBatchId = productBatchDto.getProductBatchId();
                String productBatchName = productBatchDto.getProductBatchName();
                Product product = modelMapper.map(productDto, Product.class);
                product.setAutoType(autoType);
                product.setProductBatchId(productBatchId);
                product.setProductBatchName(productBatchName);
                product.setUrlByCodeManagerCallBack(CallBackConstant.PRIZE_WHEELS_URL);

                products.add(product);
            }
        }
        return products;

    }
    public List<ProductUpdateDto> productPojoToProductUpdateDto(List<ProductPojo> productPojos) {


        List<ProductUpdateDto> list = new ArrayList<>();
        if(CollectionUtils.isEmpty(productPojos)){
            return  list;
        }
        HashMap<String ,String> hashMap = new HashMap<>();
        productPojos.forEach(productPojo -> {
            // 产品
            ProductUpdateDto productDto = modelMapper.map(productPojo, ProductUpdateDto.class);
            // 产品批次
            String productId = productPojo.getProductId();
            if(!hashMap.keySet().contains(productId)){
                // 产品批次列表
                List<ProductBatchDto> productBatchParams = new ArrayList<>();
                ProductBatchDto productBatchDto = new ProductBatchDto();
                productBatchDto.setProductBatchId(productPojo.getProductBatchId());
                productBatchDto.setProductBatchName(productPojo.getProductBatchName());
                productBatchParams.add(productBatchDto);
                // 产品新增产品批次
                productDto.setProductBatchParams(productBatchParams);
                list.add(productDto);
                // 元数据
                hashMap.put(productPojo.getProductId(),productPojo.getProductBatchId());
            }else {
                // 产品追加产品批次
                list.forEach(productUpdateDto -> {
                    if(productUpdateDto.getProductId().equals(productId)){
                        ProductBatchDto productBatchDto = new ProductBatchDto();
                        productBatchDto.setProductBatchId(productPojo.getProductBatchId());
                        productBatchDto.setProductBatchName(productPojo.getProductBatchName());
                        productUpdateDto.getProductBatchParams().add(productBatchDto);
                        return;
                    }
                });

            }
        });

        return list;
    }
}