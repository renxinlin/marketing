package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
}