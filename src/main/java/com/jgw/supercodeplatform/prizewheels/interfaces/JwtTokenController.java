package com.jgw.supercodeplatform.prizewheels.interfaces;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("marketing/front/prizeWheels")
@Api(value = "", tags = "登录token生成器")
public class JwtTokenController extends SalerCommonController {

    @GetMapping("/jwt")
    @ApiOperation(value = "导出参与记录",notes = "")
    public RestResult<String> exportRecords() throws SuperCodeException {
        JWTUtil demo = new JWTUtil();
        H5LoginVO j = new H5LoginVO();
        j.setMemberId(62l);
        j.setMemberType((byte)0);
        j.setMobile("15306818218");
        j.setOrganizationId("86ff1c47b5204e88918cb89bbd739f12");
        j.setCustomerId("86ff1c47b5204e88918cb89bbd739f12");
        j.setCustomerName("86ff1c47b5204e88918cb89bbd739f12");
        j.setOrganizationName("江苏浮华文化传播有限公司");
        j.setOpenid("oeVn5slgtRdcwnThodO-1u5rjrk0");
        j.setMemberName("逮虾户");
        j.setWechatHeadImgUrl("http://thirdwx.qlogo.cn/mmopen/PiajxSqBRaEJDTjDZqgTvVrP8fmOqurVoIKpTojV1Y7JKVozYB7Vxkn9iamGlAmILKDXLYiayMH4OiaGtyKkrK9nnw/132");
        j.setHaveIntegral(303);
        String createTokenWithClaim = demo.createTokenWithClaim(j);
        System.out.println(createTokenWithClaim);

//		H5LoginVO jwtUser = demo.verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJINSBTRUNVQ0lUWSIsImF1ZCI6IkFQUCIsImp3dFVzZXIiOiJ7XCJjdXN0b21lcklkXCI6XCI4NmZmMWM0N2I1MjA0ZTg4OTE4Y2I4OWJiZDczOWYxMlwiLFwibWVtYmVySWRcIjo4MixcIm1lbWJlck5hbWVcIjpcIjE1NzI4MDQzNTc5XCIsXCJtZW1iZXJUeXBlXCI6MSxcIm1vYmlsZVwiOlwiMTU3MjgwNDM1NzlcIixcIm9yZ2FuaXphdGlvbklkXCI6XCI4NmZmMWM0N2I1MjA0ZTg4OTE4Y2I4OWJiZDczOWYxMlwiLFwib3JnYW5pemF0aW9uTmFtZVwiOlwi5rGf6IuP5rWu5Y2O5paH5YyW5Lyg5pKt5pyJ6ZmQ5YWs5Y-4XCIsXCJyZWdpc3RlcmVkXCI6MH0iLCJpc3MiOiJKR1cgQ0pNIENPTVBBTlkiLCJleHAiOjE1Njg3MDc5MjB9.ylw5Af_un-3Iz3dErGyjxIk2t443T6fxBCRgrcMcXbw");

        H5LoginVO jwtUser = demo.verifyToken(createTokenWithClaim);
        System.out.println(JSONObject.toJSONString(jwtUser));
        return success(createTokenWithClaim);
    }

}
