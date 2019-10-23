package com.jgw.supercodeplatform.prizewheels.infrastructure.translator;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoProductBatch;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlUnBindDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;
@Slf4j
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


    /**
     *
     * @param products
     * @return
     */
    public List<SbatchUrlDto> tranferProductsToSbatchUrlDtosWhenBinding(List<Product> products) {
        log.info(" tranferProductsToSbatchUrlDtos(List<Product> products) {}",JSONObject.toJSONString(products));
        List<SbatchUrlDto> list = new ArrayList<>();
        products.forEach(product -> {
            String[] sbatchIds = product.getSbatchId().split(Product.SPLIT_SYMBOL);
            Arrays.asList(sbatchIds).forEach(sbatchId ->{
                SbatchUrlDto sbatchUrlDto = new SbatchUrlDto();
                sbatchUrlDto.setUrl(CallBackConstant.PRIZE_WHEELS_URL);
                sbatchUrlDto.setBusinessType(ActivityTypeConstant.wheels);
                sbatchUrlDto.setBatchId(Long.parseLong(sbatchId));
                sbatchUrlDto.setClientRole(MemberTypeEnums.VIP.getType()+"");
                list.add(sbatchUrlDto);
            });
        });
        return list;
    }


    public List<SbatchUrlUnBindDto> tranferProductsToSbatchUrlDtosWhenUnBinding(List<Product> products) {
        log.info(" tranferProductsToSbatchUrlDtos(List<Product> products) {}",JSONObject.toJSONString(products));
        List<SbatchUrlUnBindDto> list = new ArrayList<>();
        products.forEach(product -> {
            String[] sbatchIds = product.getSbatchId().split(Product.SPLIT_SYMBOL);
            Arrays.asList(sbatchIds).forEach(sbatchId ->{
                SbatchUrlUnBindDto sbatchUrlDto = new SbatchUrlUnBindDto();
                sbatchUrlDto.setUrl(CallBackConstant.PRIZE_WHEELS_URL);
                sbatchUrlDto.initAllBusinessType();
                sbatchUrlDto.setBatchId(Long.parseLong(sbatchId));
                sbatchUrlDto.setClientRole(MemberTypeEnums.VIP.getType()+"");
                list.add(sbatchUrlDto);
            });
        });
        return list;
    }
}
