package com.jgw.supercodeplatform.marketing.service.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.CouponAcquireConditionEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.GetSbatchIdsByPrizeWheelsFeign;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;

@Component
public class CommonMqTaskService {
	protected static Logger logger = LoggerFactory.getLogger(CommonMqTaskService.class);
	
	@Autowired
	private MarketingActivityProductMapper mProductMapper;
	
	@Autowired
	private MarketingActivitySetMapper mSetMapper;
	
	@Autowired
	private GetSbatchIdsByPrizeWheelsFeign getSbatchIdsByPrizeWheelsFeign;
	
	@Value("${marketing.domain.url}")
	private String marketingDomain;
	
	@Value("${rest.codemanager.url}")
	private String codeManagerUrl;
	
	/**
	 * 如果mq消费失败如何保证重复消费时不导致活动码数量非法增加
	 * 处理码管理平台新绑定的产品批次和生码批次
	 * @param batchList
	 */
	public void handleNewBindBatch(List<Map<String, Object>> batchList) {
			Map<Long, Long> activityCodeSumMap=new HashMap<Long, Long>();
			List<SbatchUrlDto> bindBatchList=new ArrayList<>();
			for (Map<String, Object> map : batchList) {
				Object productId=map.get("productId");
				Object productBatchId=map.get("productBatchId");
				Object codeTotal=map.get("codeTotal");
				Object codeBatch=map.get("codeBatch");
				logger.info("收到mq:productId="+productId+",productBatchId="+productBatchId+",codeTotal="+codeTotal+",codeBatch="+codeBatch);
				if (null==productId || null==productBatchId ||null==codeTotal|| null==codeBatch) {
					logger.error("获取码管理平台推送的新增批次mq消息，值有空值productId="+productId+",productBatchId="+productBatchId+",codeTotal="+codeTotal+",codeBatch="+codeBatch);
					continue;
				}
				Long codeTotalLon=Long.parseLong(String.valueOf(codeTotal));
				String strProductId=String.valueOf(productId);
				String strProductBatchId=String.valueOf(productBatchId);
				List<MarketingActivityProduct> mProducts=mProductMapper.selectByProductAndProductBatchId(strProductId, strProductBatchId);
				if (null==mProducts || mProducts.isEmpty()) {
					return;
				}
			    for (MarketingActivityProduct mProduct : mProducts) {
			    	//只有会员活动需要跳转公共h5页面才需要绑定
			    	Byte referenceRole=mProduct.getReferenceRole();
			    	if (null!=referenceRole) {
				    	Long activitySetId=mProduct.getActivitySetId();
						MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
						if (null==mActivitySet ) {
							continue;
						}
						Integer autoFecth=mActivitySet.getAutoFetch();
						if (null==autoFecth || autoFecth.intValue()==2) {
							continue;
						}
						Long activityCodeSum=activityCodeSumMap.get(activitySetId);
						if (null==activityCodeSum) {
							activityCodeSumMap.put(activitySetId, codeTotalLon);
						}else {
							activityCodeSumMap.put(activitySetId,  codeTotalLon+activityCodeSum);
						}
						mProduct.setSbatchId((String) codeBatch+","+mProduct.getSbatchId());
						mProductMapper.updateWhenAutoFetch(mProduct);
						int activityId = mActivitySet.getActivityId().intValue();
						Integer bizType = null;
						//如果是抵扣券，则需要看情况，如果是扫商品领取则需要绑定，如果是领取积分则不需要
						if(activityId == 4) {
							// 抵扣券外部处理
//							MarketingActivitySetCondition validCondition = JSON.parseObject(mActivitySet.getValidCondition(), MarketingActivitySetCondition.class);
//							validCondition.getAcquireCondition();
//							if(CouponAcquireConditionEnum.SHOPPING.getCondition().equals(validCondition.getAcquireCondition())){
//								bizType = BusinessTypeEnum.MARKETING_COUPON.getBusinessType();
//							}
						} else {
							bizType = BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType();
						}
						if (bizType != null) {
							SbatchUrlDto sbatchUrlDto = new SbatchUrlDto();
							//导购红包
							if (activityId == 3) {
								sbatchUrlDto.setClientRole("1");
								sbatchUrlDto.setUrl(marketingDomain + WechatConstants.SALER_SCAN_CODE_JUMP_URL);
							} else {
								sbatchUrlDto.setClientRole("0");
								sbatchUrlDto.setUrl(marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL);
							}
							sbatchUrlDto.setProductId(strProductId);
							sbatchUrlDto.setProductBatchId(strProductBatchId);
							sbatchUrlDto.setBatchId(Long.valueOf(codeBatch.toString()));
							sbatchUrlDto.setBusinessType(bizType);
							bindBatchList.add(sbatchUrlDto);
						}
					}
				}
			}
			try {
				if (bindBatchList.isEmpty()) {
					return;
				}
				//绑定生码批次与url的关系
				//生码批次跟url绑定
				RestResult restResult = getSbatchIdsByPrizeWheelsFeign.bindingUrlAndBizType(bindBatchList);
//				String bindJson=JSONObject.toJSONString(bindBatchList);
//				ResponseEntity<String>  bindBatchresponse=restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl+WechatConstants.CODEMANAGER_BIND_BATCH_TO_URL, bindJson, null);
//				String batchBody=bindBatchresponse.getBody();
//				JSONObject batchobj=JSONObject.parseObject(batchBody);
//				Integer batchstate=batchobj.getInteger("state");
				if (restResult.getState() != HttpStatus.SC_OK) {
					logger.error("处理码管理推送的mq消息时绑定生码批次与url的关系出错，错误信息："+JSON.toJSONString(restResult)+",批次信息：");
					return;
				}
//				if (!activityCodeSumMap.isEmpty()){
//					for(Long activitySetid:activityCodeSumMap.keySet()) {
//						Long codeNum=activityCodeSumMap.get(activitySetid);
//						synchronized (this) {
//							mSetMapper.addCodeTotalNum(codeNum,activitySetid);
//						}
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
