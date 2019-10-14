package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductTransfer {

    @Autowired
    private ModelMapper modelMapper;

    public List<Product> dtoToProduct(List<ProductDto> productDtos) {
       return productDtos
                .stream()
                .map(productDto -> {
                    Product product = modelMapper.map(productDto, Product.class);
                    // TODO 字段补充
                    return product;})
                .collect(Collectors.toList());
    }

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

    public List<ProductUpdateDto> productToProductDto(List<Product> products) {
        return products
                .stream()
                .map(product -> {
                    ProductUpdateDto productDto = modelMapper.map(product, ProductUpdateDto.class);
                    // TODO 字段补充
                    return productDto;})
                .collect(Collectors.toList());
    }
}