package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.OutCodeInfoVo;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.GetSbatchIdsByPrizeWheelsFeign;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.EsRelationcode;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlUnBindDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.translator.ProductDomainTranfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
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
        log.info(" initSbatchIds请求入参如下{}",JSONObject.toJSONString(getBatchInfoDtoList));
        RestResult<Object> sbatchIds = getSbatchIdsByPrizeWheelsFeign.getSbatchIds(getBatchInfoDtoList);
        log.info(" initSbatchIds请求返回如下{}",JSONObject.toJSONString(sbatchIds));

        // 业务
        if(!CollectionUtils.isEmpty((ArrayList)sbatchIds.getResults()) && sbatchIds.getState() ==200 ){
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
            log.info("initSbatchIds(List<Product> products) =》 {}",JSONObject.toJSONString(getBatchInfoDtoList));
            throw new BizRuntimeException("获取码管理生码信息失败");
        }
        // 携带生码批次
        return products;


    }

    @Override
    public void executeBizWhichCodeManagerWant(List<Product> products) {
        List<SbatchUrlDto> sbatchUrlDtoList = productDomainTranfer.tranferProductsToSbatchUrlDtosWhenBinding(products);
        log.info("更新大转盘executeBizWhichCodeManagerWant(List<Product> sbatchUrlDtoList） =》 {}",JSONObject.toJSONString(sbatchUrlDtoList));
        RestResult restResult = getSbatchIdsByPrizeWheelsFeign.bindingUrlAndBizType(sbatchUrlDtoList);
        log.info("更新大转盘executeBizWhichCodeManagerWant(List<Product> products） =》 {}",JSONObject.toJSONString(restResult));
        Asserts.check(restResult!=null && restResult.getState() == 200 ,"服务调用失败");
    }

    @Override
    public void removeOldProduct(List<Product> product) {
        if(CollectionUtils.isEmpty(product)){
            return;
        }
        List<SbatchUrlUnBindDto> sbatchids = productDomainTranfer.tranferProductsToSbatchUrlDtosWhenUnBinding(product);
        log.info("更新大转盘removeOldProduct(List<Product> sbatchids) =》 {}",JSONObject.toJSONString(sbatchids));
        RestResult<Object> objectRestResult = getSbatchIdsByPrizeWheelsFeign.removeOldProduct(sbatchids);
        log.info("更新大转盘removeOldProduct(List<Product> product) =》 {}",JSONObject.toJSONString(objectRestResult));
        Asserts.check(objectRestResult!=null && objectRestResult.getState() == 200 ,"服务调用失败");
    }
}
