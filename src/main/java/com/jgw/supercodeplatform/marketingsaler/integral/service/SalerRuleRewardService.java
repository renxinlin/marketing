package com.jgw.supercodeplatform.marketingsaler.integral.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam;
import com.jgw.supercodeplatform.marketing.dto.integral.Product;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.BatchSalerRuleRewardDto;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRuleRewardMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.transfer.SalerRuleRewardParamTransfer;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.rest.SalerRuleRewardRestInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.jgw.supercodeplatform.marketing.constants.ActivityDefaultConstant.superToken;
import static com.jgw.supercodeplatform.marketingsaler.integral.transfer.SalerRuleRewardParamTransfer.constructProductAndBatchMOByPPArr;

/**
 * <p>
 * 产品积分规则表 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Slf4j
@Service
public class SalerRuleRewardService extends SalerCommonService<SalerRuleRewardMapper, SalerRuleReward> {


    @Value("${rest.codemanager.url}")
    private String codeManagerRestUrl;
    @Autowired
    private SalerRuleRewardRestInterface salerRuleRewardRestInterface;
    /**
     * 未选列表
     * copy会员体系代码
     * @param daoSearch
     * @return
     */
    public RestResult<AbstractPageService.PageResults<List<SalerRuleReward>>> unSelectPage(DaoSearch daoSearch) throws SuperCodeException {
        RestResult<AbstractPageService.PageResults<List<SalerRuleReward>>> restResult=new RestResult<AbstractPageService.PageResults<List<SalerRuleReward>>>();
        Map<String, Object> params=new HashMap<String, Object>();
        Integer current=daoSearch.getCurrent();
        Integer pagesize=daoSearch.getPageSize();

        params.put("current", current);
        params.put("pageSize", pagesize);
        String organizationId = commonUtil.getOrganizationId();
        params.put("organizationId",organizationId );
        params.put("search", daoSearch.getSearch());

        List<SalerRuleReward> salerRuleRewards = baseMapper.selectList(query().select("ProductId").eq("OrganizationId", commonUtil.getOrganizationId()).getWrapper());
        List<String> productIds =salerRuleRewards.stream().map(element->element.getProductId()).collect(Collectors.toList());
        if (null!=productIds && !productIds.isEmpty()) {
            params.put("excludeProductIds",String.join(",", productIds));
        }

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("super-token", commonUtil.getSuperToken());
        ResponseEntity<String> responseEntity = restTemplateUtil.getRequestAndReturnJosn(codeManagerRestUrl+ CommonConstants.CODEMANAGER_RELATION_PRODUCT_URL, params, headerMap);
        String body = responseEntity.getBody();
        log.info("接收到码管理进行过码关联的产品信息："+body);
        JSONObject json=JSONObject.parseObject(body);
        int state=json.getInteger("state");
        List<SalerRuleReward> ruleproductList=new ArrayList<>();
        if (state==200) {
            restResult.setState(200);
            JSONArray arry=json.getJSONObject("results").getJSONArray("list");
            for (int i=0 ;i<arry.size();i++) {
                JSONObject ruleProduct=arry.getJSONObject(i);
                String prductId = ruleProduct.getString("productId");
                if(productIds == null || !productIds.contains(prductId)) {
                    SalerRuleReward product=new SalerRuleReward();
                    product.setProductId(prductId);
                    product.setProductName(ruleProduct.getString("productName"));
                    ruleproductList.add(product);
                }
            }
            String pagination_str=json.getJSONObject("results").getString("pagination");
            com.jgw.supercodeplatform.marketing.common.page.Page page=JSONObject.parseObject(pagination_str, com.jgw.supercodeplatform.marketing.common.page.Page.class);
            AbstractPageService.PageResults<List<SalerRuleReward>> pageResults=new AbstractPageService.PageResults<List<SalerRuleReward>>(ruleproductList, page);
            restResult.setResults(pageResults);
        }else {
            throw new SuperCodeException("请求码管理产品出错", 500);
        }
        return restResult;    }

    public AbstractPageService.PageResults<List<SalerRuleReward>> listSearchViewLike(DaoSearch daoSearch) throws SuperCodeException {
        // 获取分页集
        IPage<SalerRuleReward> salerRuleRewardIPage = baseMapper.selectPage(
                SalerRuleRewardParamTransfer.getPage(daoSearch)
                , SalerRuleRewardParamTransfer.getPageParam(daoSearch,commonUtil.getOrganizationIdNew())
        );
        // 数据转换
        AbstractPageService.PageResults<List<SalerRuleReward>> pageResult = SalerRuleRewardParamTransfer.toPageResult( salerRuleRewardIPage);
        return pageResult;
    }

    public void deleteByProductIds(List<String> productIds)  {
        Asserts.check(!CollectionUtils.isEmpty(productIds),"产品id不能为空");
        baseMapper.delete(query().in("ProductId",productIds).eq("OrganizationId",commonUtil.getOrganizationId()).getWrapper());
    }

    /**
     * copy会员代码
     * @param bProductRuleParam
     */
    public void batchSetSalerRule(BatchSalerRuleRewardDto bProductRuleParam) throws SuperCodeException {
        // 规则设置数据校验
        List<Product> products=bProductRuleParam.getProducts();
        Asserts.check(!CollectionUtils.isEmpty(products),"产品不能为空");

        products.forEach(product ->Asserts.check(!StringUtils.isEmpty(product.getProductId()),"产品id不能为空"));
        Asserts.check(!CollectionUtils.isEmpty(products),"产品id不能为空");

        Integer settedCount = baseMapper.selectCount(
                query().eq("OrganizationId", commonUtil.getOrganizationId())
                        .in("ProductId", products.stream().map(product -> product.getProductId()).collect(Collectors.toList()))
                .getWrapper());
        Asserts.check(settedCount == null || settedCount <= 0,"已设置过无法继续设置");
        List<SalerRuleReward> batchRule = SalerRuleReward.toSaveBatch(bProductRuleParam,commonUtil.getOrganizationId());
        this.saveBatch(batchRule);

        //根据产品id集合去基础平台请求对应的产品批次 TODO 检查是否需要修改 如url,导购角色等
//        JSONArray jsonArray= commonService.requestPriductBatchIds(products.stream().map(product -> product.getProductId()).collect(Collectors.toList()), commonUtil.getSuperToken());
        //构建请求生码批次参数
//        List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = SalerRuleRewardParamTransfer.constructProductAndBatchMOByPPArr(jsonArray);
        //请求生码批次及积分url绑定批次 TODO 检查是否需要修改 如跳转url,导购角色等
//        salerRuleRewardRestInterface.integralUrlBindBatch(BusinessTypeEnum.INTEGRAL.getBusinessType(),superToken, productAndBatchGetCodeMOs);

        // 保存积分设置数据


        //更新基础数据产品营销信息
//        salerRuleRewardRestInterface.updateBaseProductPriceBatch(products,bProductRuleParam,superToken);
    }

    /**
     * copy 会员代码
     * @param inRuleProduct
     * @throws SuperCodeException
     */
    public void singleSetRuleProduct(SalerRuleReward inRuleProduct) throws SuperCodeException {
        if (null==inRuleProduct.getId()) {
            // 基础业务校验
            Asserts.check(inRuleProduct!= null && inRuleProduct.getProductId() != null,"导购员积分单个设置参数不全");
            Integer settedCount = baseMapper.selectCount(
                    query().eq("OrganizationId", commonUtil.getOrganizationId())
                            .eq("ProductId",inRuleProduct.getProductId())
                            .getWrapper());
            Asserts.check(settedCount == null || settedCount <= 0,"该产品已经设置请携带主键");

            // 数据保存
            baseMapper.insert(inRuleProduct);

            //根据产品id集合去基础平台请求对应的产品批次
//            JSONArray jsonArray= commonService.requestPriductBatchIds(Arrays.asList(inRuleProduct.getProductId()), commonUtil.getSuperToken());
            //构建请求生码批次参数
//            List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = constructProductAndBatchMOByPPArr(jsonArray);
//            salerRuleRewardRestInterface.integralUrlBindBatch(BusinessTypeEnum.INTEGRAL.getBusinessType(),superToken, productAndBatchGetCodeMOs);

            //更新产品营销信息
//            salerRuleRewardRestInterface.updateBaseProductPrice(inRuleProduct,superToken);

        }else {
            // copy 会员代码
            if (StringUtils.isBlank(inRuleProduct.getProductId())) {
                inRuleProduct.setProductId(null);
            }
            if (StringUtils.isBlank(inRuleProduct.getProductName())) {
                inRuleProduct.setProductName(null);
            }
            baseMapper.updateById(inRuleProduct);
        }
    }
}
