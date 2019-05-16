package com.jgw.supercodeplatform.marketing.check.activity;

import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;

@Component
public class StandActicityParamCheck extends BaseActivityParamCheck{

	@Override
	protected void specialPrizeTypeCheck(MarketingPrizeTypeParam prizeTypeParam) throws SuperCodeException{
		
	}

}
