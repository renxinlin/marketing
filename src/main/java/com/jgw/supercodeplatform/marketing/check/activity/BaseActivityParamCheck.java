package com.jgw.supercodeplatform.marketing.check.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;

public abstract class BaseActivityParamCheck {
	/**
	 * 校验前端传的参数是否合法无需检查之前是否已存在新增记录，存在覆盖就行，目前暂不需要根据不同活动或角色做特殊化校验
	 * @param maProductParams
	 * @return
	 * @throws SuperCodeException
	 */
  public void baseProductBatchCheck(List<MarketingActivityProductParam> maProductParams) throws SuperCodeException {
		//校验产品及批次数据是否为空
		if (null==maProductParams || maProductParams.isEmpty()) {
			throw new SuperCodeException("产品信息不能为空", 500);
		}
		Map<String, Boolean> deduplication=new HashMap<String, Boolean>();
		for (MarketingActivityProductParam mProductParam : maProductParams) {
			String productId = mProductParam.getProductId();
			List<ProductBatchParam> batchParams=mProductParam.getProductBatchParams();
			if (StringUtils.isBlank(productId) || null==batchParams || batchParams.isEmpty()) {
				throw new SuperCodeException("产品id和批次信息不能为空", 500);
			}
			
			for (ProductBatchParam productBatch:mProductParam.getProductBatchParams()){
				String productBatchId = productBatch.getProductBatchId();
				String combinekey=productId+productBatchId;
				Boolean flag=deduplication.get(combinekey);
				if (null!=flag) {
					throw new SuperCodeException("新增产品及批次不可重复，已存在"+productId+"产品", 500);
				}
				MarketingActivityProduct mProduct=new MarketingActivityProduct();
				mProduct.setProductBatchId(productBatchId);
				mProduct.setProductId(productId);
				deduplication.put(combinekey, true);
			}
		}
  }
  
	/**
	 * TODO 目前校验逻辑是根据awardType判断需不需要名称;此外依赖前端传递数据格式保障逻辑
	 * 存在问题:微信活动是全部没有奖项名称的，锦鲤翻牌全有，其他待定
	 * 这套检验逻辑不够严谨
	 * 公共奖次校验，校验目前已有的奖项参数是否合法
	 * @param mPrizeTypeParams
	 * @throws SuperCodeException
	 */
	public void basePrizeTypeCheck(List<MarketingPrizeTypeParam> mPrizeTypeParams) throws SuperCodeException {
		//校验奖次信息
		if (null==mPrizeTypeParams || mPrizeTypeParams.isEmpty()) {
			throw new SuperCodeException("奖次信息不能为空", 500);
		}else {
			Set<String> set = new HashSet<String>();
			boolean haveAwardType = false;

			for (MarketingPrizeTypeParam prizeTypeParam:mPrizeTypeParams){
				Byte awardType=prizeTypeParam.getAwardType();
				//如果奖项类型是空则是之前的红包活动没有奖项设置的
				if (null==awardType) { //如果为空则所有为空，如果不为空则所有不为空 shouldEveryprizeTypeParam实现
					Byte randomAmont=prizeTypeParam.getIsRrandomMoney();
					if (null==randomAmont) {
						throw new SuperCodeException("是否固定金额不能为空", 500);
					}else if (randomAmont.equals((byte)0)) {
						//如果固定金额则不能小于1大于5000
						Float amount=prizeTypeParam.getPrizeAmount();
						if (null==amount|| amount<=0 ||amount>5000) {
							throw new SuperCodeException("金额参数非法，不能为空只能在0-5000以内", 500);
						}
					}else if (randomAmont.equals((byte)1)) {
						//如果是随机金额则校验随机金额取值
						Float lowrand=prizeTypeParam.getLowRand();
						Float highrand=prizeTypeParam.getHighRand();
						if (null==lowrand || null==highrand || lowrand >=highrand) {
							throw new SuperCodeException("随机金额取值范围不能为空且低取值不能大于等于高取值", 500);
						}
						if (lowrand<=0 || highrand>5000) {
							throw new SuperCodeException("随机金额参数非法，低值和高值取值只能在0-5000以内", 500);
						}
					}
				}else {
					haveAwardType = true;
				   	//奖项类型不为空则为新活动
					switch (awardType) {
					case 1://实物
						
						break;
					case 2://卡券
						String cardLink=prizeTypeParam.getCardLink();
						if (StringUtils.isBlank(cardLink)) {
							throw new SuperCodeException("卡券类型奖次卡券不能为空", 500);
						}
						break;
					case 3://积分
						Integer awardIntegralNum= prizeTypeParam.getAwardIntegralNum();
						if (null==awardIntegralNum) {
							throw new SuperCodeException("积分类型奖次奖励积分不能为空", 500);
						}
						break;
					case 9://其它
						
						break;
					default:
						break;
					}
				}
				//供特殊化需求子类实现
				specialPrizeTypeCheck(prizeTypeParam);
				
				Integer prizeProbability=prizeTypeParam.getPrizeProbability();
				if (null==prizeProbability || prizeProbability<0 || prizeProbability>100) {
					throw new SuperCodeException("概率参数非法prizeProbability="+prizeProbability, 500);
				}
				set.add(prizeTypeParam.getPrizeTypeName());
			}
			// 目前用于检测锦鲤翻牌类型活动

			if (set.size()<mPrizeTypeParams.size() && haveAwardType ) {
				throw new SuperCodeException("奖项名称不能重复", 500);
			}
		}
	}


	protected abstract void specialPrizeTypeCheck(MarketingPrizeTypeParam prizeTypeParam) throws SuperCodeException;
	
	
	
	
}
