package com.jgw.supercodeplatform.marketingsaler.integral.application.group;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.infrastructure.ErrorMsg;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.FromFakeOutCodeToMarketingInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.CodeManagerFeign;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.dto.ProductInfoByCodeDto;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.CodeManagerFromFadeToMarketingFeign;
import feign.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class CodeManagerService {

    @Autowired
    private CodeManagerFeign codeManagerFeign;
    @Autowired
    private CodeManagerFromFadeToMarketingFeign codeManagerFromFadeToMarketingFeign;

    @Autowired
    private ModelMapper modelMapper;

    public ProductInfoByCodeDto getProductByCode(OutCodeInfoDto outCodeInfoDto){
        log.info("准备获取外码对应的产品信息{}",outCodeInfoDto);
        // 防伪码转营销码
        outCodeInfoDto = codeFromfakeToMarket(outCodeInfoDto);
        log.info("转换后的码:{}",JSONObject.toJSONString(outCodeInfoDto));

        if (outCodeInfoDto == null) return null;
        Asserts.check(UserConstants.MARKETING_CODE_TYPE.equals(outCodeInfoDto.getCodeTypeId())|| UserConstants.MARKETING_CODE_TYPE_13.equals(outCodeInfoDto.getCodeTypeId()), ErrorMsg.no_setting_integral);


        RestResult<Object>  restResult = null;
        restResult = codeManagerFeign.getProductByCode(outCodeInfoDto);
        log.info("准备获取外码对应的产品信息返回{}", JSONObject.toJSONString(restResult));
        if(restResult == null ){
            return null;
        }
        if(restResult.getState() != 200 ){
            return null;
        }
        if(restResult.getState() == 500  && "您所查询的码暂未激活".equals(restResult.getMsg()) ){
            //////////////////////////////////////////
            //           ----      ------            //
            //                                       //
            //                 |                      //
            //               ---------               //
            throw new BizRuntimeException("您所查询的码暂未激活，请联系企业");
        }
        ProductInfoByCodeDto results =  modelMapper.map(restResult.getResults(),ProductInfoByCodeDto.class);
        results.setMarketingCode(outCodeInfoDto);
        return results;
    }

    public OutCodeInfoDto codeFromfakeToMarket(OutCodeInfoDto outCodeInfoDto) {
        if(UserConstants.MARKETING_CODE_TYPE_FADE.equals(outCodeInfoDto.getCodeTypeId())){
            // 创建入参
            FromFakeOutCodeToMarketingInfoDto fromFakeOutCodeToMarketingInfoDto = modelMapper.map(outCodeInfoDto, FromFakeOutCodeToMarketingInfoDto.class);
            List<String> needsCodeType = new ArrayList<>();
            needsCodeType.add(UserConstants.MARKETING_CODE_TYPE);
            needsCodeType.add(UserConstants.MARKETING_CODE_TYPE_13);
            fromFakeOutCodeToMarketingInfoDto.setNeedCodeTypeIds(needsCodeType);
            // 获取防伪码对应营销码
            log.info("防伪转营销入参:{}",JSONObject.toJSONString(fromFakeOutCodeToMarketingInfoDto));
            RestResult<Object> niuniuResult = codeManagerFromFadeToMarketingFeign.getCodeManagerFromFadeToMarketingByCode(fromFakeOutCodeToMarketingInfoDto);
            log.info("防伪转营销出参:{}",JSONObject.toJSONString(niuniuResult));

            if(niuniuResult != null && niuniuResult.getState() == 200 && niuniuResult.getResults() != null ) {
                Object results = niuniuResult.getResults();
                for (Object feignResult : (List<?>) results) {
                    // 只会有一个元素返回【对方服务同时支持其他服务调用】
                    OutCodeInfoDto marketOutCodeInfoDto = modelMapper.map(feignResult, OutCodeInfoDto.class);
                    log.info("防伪转营销返回:{}",JSONObject.toJSONString(niuniuResult));
                    return marketOutCodeInfoDto;
                }
            }

            throw new BizRuntimeException("防伪码转对应的营销码失败");
        }else {
            // 非防伪码无需处理
            return outCodeInfoDto;
        }

    }


}
