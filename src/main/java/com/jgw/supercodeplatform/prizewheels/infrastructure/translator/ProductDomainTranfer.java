package com.jgw.supercodeplatform.prizewheels.infrastructure.translator;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoProductBatch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductDomainTranfer {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 翻译产品到码管理
     * @param products
     * @return
     */
    public List<GetBatchInfoDto> tranferProductsToGetBatchInfoDtos(List<Product> products) {

        Set<GetBatchInfoDto> set = new HashSet<>();
        products.forEach(product -> {
            product.getProductId();
            GetBatchInfoDto getBatchInfoDto = new GetBatchInfoDto();
            getBatchInfoDto.setProductId(product.getProductId());
            getBatchInfoDto.setProductBatchList(new ArrayList<>());
            set.add(getBatchInfoDto);
        });
        List<GetBatchInfoDto> list = new ArrayList<>(set);

        products.forEach(product -> {
            String productId = product.getProductId();
            list.forEach(getBatchInfoDto -> {
                if(getBatchInfoDto.getProductId().equals(productId)){
                    GetBatchInfoProductBatch batchInfo = new GetBatchInfoProductBatch();
                    batchInfo.setProductBatchId(product.getProductBatchId());
                    // 追加批次信息
                    getBatchInfoDto.getProductBatchList().add(batchInfo);
                }
            });
        });

        return list;
    }
}
