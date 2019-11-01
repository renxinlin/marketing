package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class ProductPojoTransfer {
    @Autowired private ModelMapper modelMapper;

    public  List<ProductPojo> transferProductsToPojos(List<Product> products) {
        List<ProductPojo> productPojos = new ArrayList<>();
        products.forEach(product -> {
            ProductPojo productPojo = modelMapper.map(product, ProductPojo.class);
            // TODO 属性补充
            productPojos.add(productPojo);
        });
        return  productPojos;
    }

    public List<Product> tranferPojosToDomains(List<ProductPojo> productPojos) {
        List<Product> products = new ArrayList<>();
        productPojos.forEach(productPojo-> {
            Product product  = modelMapper.map(productPojo, Product.class);
            // TODO 属性补充
            products.add(product);
        });
        return  products;
    }
}
