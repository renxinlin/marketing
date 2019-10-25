package com.jgw.supercodeplatform.marketingsaler.integral.application.group;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.CodeManagerFeign;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.dto.ProductInfoByCodeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CodeManagerService {
    @Autowired
    private CodeManagerFeign codeManagerFeign;


    @Autowired
    private ModelMapper modelMapper;

    public ProductInfoByCodeDto getProductByCode(OutCodeInfoDto outCodeInfoDto){
        log.info("准备获取外码对应的产品信息{}",outCodeInfoDto);
        Asserts.check(UserConstants.MARKETING_CODE_TYPE.equals(outCodeInfoDto.getCodeTypeId())|| UserConstants.MARKETING_CODE_TYPE_13.equals(outCodeInfoDto.getCodeTypeId()),"非营销码");
        RestResult<Object>  restResult = null;
        restResult = codeManagerFeign.getProductByCode(outCodeInfoDto);
        log.info("准备获取外码对应的产品信息返回{}", JSONObject.toJSONString(restResult));
        if(restResult == null ){
            return null;
        }
        if(restResult.getState() != 200 ){
            return null;
        }
        ProductInfoByCodeDto results =  modelMapper.map(restResult.getResults(),ProductInfoByCodeDto.class);
        return results;
    }
}
