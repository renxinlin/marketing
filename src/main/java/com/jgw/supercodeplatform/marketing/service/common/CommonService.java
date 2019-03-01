package com.jgw.supercodeplatform.marketing.service.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.properties.NormalProperties;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;

@Service
public class CommonService {
    @Autowired
    private CommonUtil commonUtil;
    
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Value("${rest.user.url}")
    private String restUserUrl;
    
	public RestResult<String> sendPhoneCode(String mobile) throws Exception {
		RestResult<String> resuRestResult=new RestResult<String>();
		if (StringUtils.isBlank(mobile)) {
			resuRestResult.setState(500);
			resuRestResult.setMsg("手机号不能为空");
			return resuRestResult;
		}
		String code=commonUtil.getSixRandom();
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("code", code);
		jsonObj.put("mobile", mobile);
		String json=jsonObj.toJSONString();
		ResponseEntity<String> responseEntity=restTemplateUtil.postJsonDataAndReturnJosn(restUserUrl, json, null);
		int status=responseEntity.getStatusCodeValue();
		String body=responseEntity.getBody();
		//如果HTTP响应成功
		if (status>=200 && status<300 ) {
			JSONObject codeObj=JSONObject.parseObject(body);
			boolean flag=codeObj.containsKey("state");
			//如果基础平台接口响应成功
			if (flag && 200==codeObj.getIntValue("state")) {
				boolean redisset=redisUtil.set(RedisKey.phone_code_prefix+mobile, code, NormalProperties.MAIL_CODE_TIMEOUT);
			    //如果验证码保存redis成功
				if (redisset) {
					resuRestResult.setState(200);
					resuRestResult.setMsg("手机验证码发送成功");
					return resuRestResult;
				}else {
					resuRestResult.setState(500);
					resuRestResult.setMsg("手机验证码设置redis失败，请重试");
					return resuRestResult;
				}
			}
			resuRestResult.setState(500);
			resuRestResult.setMsg(codeObj.getString("msg"));
			return resuRestResult;
		}
		resuRestResult.setState(500);
		resuRestResult.setMsg(body);
		return resuRestResult;
	}

}
