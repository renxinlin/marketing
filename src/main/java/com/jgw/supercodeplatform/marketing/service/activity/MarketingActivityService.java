package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MarketingActivityService extends AbstractPageService<MarketingActivityListParam>{
    @Autowired
    private MarketingActivityMapper dao;
	@Autowired
	private MarketingChannelMapper marketingChannelMapper;
	@Autowired
	private MarketingActivityProductMapper marketingActivityProductMapper;
    
	public RestResult<List<MarketingActivity>> selectAll(int activityType) {

		List<MarketingActivity> list=dao.selectAll(activityType);
		RestResult<List<MarketingActivity>> restResult=new RestResult<List<MarketingActivity>>();
		restResult.setState(200);
		restResult.setResults(list);
		restResult.setMsg("成功");
		return restResult;
	}


	@Override
	protected List<MarketingActivityListMO> searchResult(MarketingActivityListParam marketingActivityListParam) throws Exception {
		List<MarketingActivityListMO> list=dao.list(marketingActivityListParam);
		if (!CollectionUtils.isEmpty(list)) {
			list.forEach(marketingActivityListMO -> {
				Long id = marketingActivityListMO.getId();
				Long id1 = marketingActivityListMO.getId1();
				if(id1 != null) {
					id = id1;
				}
				List<MarketingChannel> channelList = marketingChannelMapper.selectByActivitySetId(id);
				List<MarketingActivityProduct> productList = marketingActivityProductMapper.selectByActivitySetId(id);
				marketingActivityListMO.setMaActivityProducts(productList);
				marketingActivityListMO.setMarketingChannels(channelList);
			});
		}
		return list;
	}


	@Override
	protected int count(MarketingActivityListParam marketingActivityListParam) throws Exception {
		return dao.count(marketingActivityListParam);
	}
  

}
