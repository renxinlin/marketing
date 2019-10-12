package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.OutCodeInfoVo;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.GetSbatchIdsByPrizeWheelsFeign;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.EsRelationcode;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.translator.ProductDomainTranfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class ProductDomainServiceImpl implements ProductDomainService {

    @Autowired
    private GetSbatchIdsByPrizeWheelsFeign getSbatchIdsByPrizeWheelsFeign;

    @Autowired
    private ProductDomainTranfer productDomainTranfer;


    @Override
    public List<Product> initSbatchIds(List<Product> products) {
        // 防腐层
        List<GetBatchInfoDto> getBatchInfoDtoList =  productDomainTranfer.tranferProductsToGetBatchInfoDtos(products);
        // 两个限界上下文交互
        RestResult<Object> sbatchIds = getSbatchIdsByPrizeWheelsFeign.getSbatchIds(getBatchInfoDtoList);

        // 业务
        if(sbatchIds!=null && sbatchIds.getState() ==200){
            List<EsRelationcode> esRelationcodes = JSONObject.parseArray((JSONObject.toJSONString(sbatchIds.getResults())), EsRelationcode.class);
            esRelationcodes.forEach(esRelationcode -> {
                products.forEach(product -> {
                    if(product.getProductId().equals(esRelationcode.getProductId())){
                        if(product.getProductBatchId().equals(esRelationcode.getProductBatchId())){
                            product.appendSbatchId(esRelationcode.getGlobalBatchId());
                        }
                    }
                });

            });

        }else {
            throw new RuntimeException("获取码管理生码信息失败");
        }
        // 携带生码批次
        return products;


    }

    @Override
    public void executeBizWhichCodeManagerWant(List<Product> products) {
        List<SbatchUrlDto> sbatchUrlDtoList = productDomainTranfer.tranferProductsToSbatchUrlDtos(products);
        RestResult restResult = getSbatchIdsByPrizeWheelsFeign.bindingUrlAndBizType(sbatchUrlDtoList);
        log.error("更新大转盘失败executeBizWhichCodeManagerWant(List<Product> products） =》 {}",JSONObject.toJSONString(restResult));
        Asserts.check(restResult!=null && restResult.getState() == 200 ,"服务调用失败");
    }

    @Override
    public void removeOldProduct(List<Product> byPrizeWheelsId) {
        // TODO
        getSbatchIdsByPrizeWheelsFeign.removeOldProduct();
    }
}
