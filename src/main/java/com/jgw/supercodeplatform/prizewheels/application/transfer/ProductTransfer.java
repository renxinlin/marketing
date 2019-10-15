package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductTransfer {

    @Autowired
    private ModelMapper modelMapper;



    public List<Product>  transferUpdateDtoToDomain(List<ProductUpdateDto> productUpdateDtos, Long activitySetId,byte autoType) {
        return productUpdateDtos
                .stream()
                .map(productDto -> {
                    Product product = modelMapper.map(productDto, Product.class);
                    //TODO 检查
                    Asserts.check(product.getReferenceRole() != null ,"modelMapper 映射不了...........");
                    log.error("modelMapper映射问题===============product.getReferenceRole() =>{}" ,product.getReferenceRole());
                    product.setActivitySetId(activitySetId);
                    product.setAutoType(autoType);
                    product.setUrlByCodeManagerCallBack(CallBackConstant.PRIZE_WHEELS_URL);
                    return product;})
                .collect(Collectors.toList());
    }

    public List<Product> transferDtoToDomain(List<ProductDto> productDtos, byte autoType) {
        return productDtos
                .stream()
                .map(productDto -> {
                    Product product = modelMapper.map(productDto, Product.class);
                    //TODO 检查
                    Asserts.check(product.getReferenceRole() != null ,"modelMapper 映射不了...........");
                    log.error("modelMapper映射问题===============product.getReferenceRole() =>{}" ,product.getReferenceRole());
                    product.setAutoType(autoType);
                    product.setUrlByCodeManagerCallBack(CallBackConstant.PRIZE_WHEELS_URL);
                    return product;})
                .collect(Collectors.toList());
    }
    public List<ProductUpdateDto> productPojoToProductUpdateDto(List<ProductPojo> productPojos) {
        return productPojos
                .stream()
                .map(productPojo -> {
                    ProductUpdateDto productDto = modelMapper.map(productPojo, ProductUpdateDto.class);
                    // TODO 字段补充
                    return productDto;})
                .collect(Collectors.toList());
    }
}