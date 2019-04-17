package com.jgw.supercodeplatform.marketing.service.integral;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralExchangeMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.ProductUnsaleMapperExt;
import com.jgw.supercodeplatform.marketing.dto.integral.ProductPageFromBaseServiceParam;
import com.jgw.supercodeplatform.marketing.dto.integral.ProductPageParam;
import com.jgw.supercodeplatform.marketing.dto.integral.SkuInfo;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UnsaleProductService extends AbstractPageService<ProductUnsale> {
    private Logger logger = LoggerFactory.getLogger(UnsaleProductService.class);
    @Value("${rest.user.url}")
    private  String baseService;

    @Autowired
    private ProductUnsaleMapperExt mapper;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private IntegralExchangeMapperExt integralExchangeMapper;


    @Autowired
    private CommonUtil commonUtil;



    @Autowired
    private ModelMapper modelMapper;

    /**
     * 抽取到基础信息
     * @param organizationId
     * @return
     */
    public RestResult selectUnSalePruduct(String organizationId, ProductPageParam pageParam) throws SuperCodeException{
        if(StringUtils.isBlank(organizationId)){
            throw  new SuperCodeException("组织id获取失败");
        }
        // 基础校验
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织ID信息失败");
        }

        // 获取组织已经添加的自卖产品集合
        Set<IntegralExchange> excludeProducts = integralExchangeMapper.selectUnSalePruduct(organizationId);
        // 选择的产品Id
        Set<String> excludeProductIds = new HashSet<>();
        // 选择的产品skuId
        Set<String> excludeSkuIds = new HashSet<>();
        // 生成传递基础数据的参数
        for(IntegralExchange excludeProduct:excludeProducts){
            excludeProductIds.add(excludeProduct.getProductId());
            excludeSkuIds.add(excludeProduct.getSkuId());
        }
        // 查询基础平台
        ProductPageFromBaseServiceParam queryCondition = modelMapper.map(pageParam, ProductPageFromBaseServiceParam.class);
        queryCondition.setExcludeProductIds(new ArrayList(excludeProductIds));
        queryCondition.setExcludeSkuIds(new ArrayList(excludeSkuIds));
        RestResult restResult = getProductFromBaseService(queryCondition,false);
        if(restResult.getState() == 200){
            return  restResult;
        }else{
            return RestResult.error("基础信息异常...");
        }
    }


    /**
     * 基于组织id:自卖产品查询
     * @param organizationId
     * @return
     */
    public RestResult selectSalePruduct(String organizationId, ProductPageParam pageParam) throws SuperCodeException{
        // 基础校验
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织ID信息失败");
        }

        // 获取组织已经添加的自卖产品集合
        Set<IntegralExchange> excludeProducts = integralExchangeMapper.selectSalePruduct(organizationId);
        // 选择的产品Id
        Set<String> excludeProductIds = new HashSet<>();
        // 选择的产品skuId
        Set<String> excludeSkuIds = new HashSet<>();
        // 生成传递基础数据的参数
        for(IntegralExchange excludeProduct:excludeProducts){
            excludeProductIds.add(excludeProduct.getProductId());
            excludeSkuIds.add(excludeProduct.getSkuId());
        }
        // 查询基础平台
        ProductPageFromBaseServiceParam queryCondition = modelMapper.map(pageParam, ProductPageFromBaseServiceParam.class);
        // 已存在兑换产品由基础信息过滤
        queryCondition.setExcludeProductIds(new ArrayList(excludeProductIds));
        queryCondition.setExcludeSkuIds(new ArrayList(excludeSkuIds));
        RestResult restResult = getProductFromBaseService(queryCondition,true);
        if(restResult.getState() == 200){
            return  restResult;
        }else{
            return RestResult.error("基础信息异常...");
        }
    }

//    @HystrixCommand(fallbackMethod = "getProductFromBaseServiceHystrix")
    public RestResult getProductFromBaseService(ProductPageFromBaseServiceParam queryCondition,boolean isSale)throws SuperCodeException{
        Map<String, String> header = new HashMap<>();
        header.put("super-token",commonUtil.getSuperToken());
        // 走下json解决反射问题
        String queryConditionStr = JSONObject.toJSONString(queryCondition);
        Map queryConditionMap = modelMapper.map(JSONObject.parse(queryConditionStr), HashMap.class);
        if(isSale){
            // 自卖产品
            // Map map = modelMapper.map(queryCondition, HashMap.class); 无法转换
            ResponseEntity<String> response = restTemplateUtil.getRequestAndReturnJosn(baseService + CommonConstants.SALE_PRODUCT_URL,queryConditionMap, header);
            return JSONObject.parseObject(response.getBody(), RestResult.class);
        }else{
            // 非自卖产品
            ResponseEntity<String> response = restTemplateUtil.getRequestAndReturnJosn(baseService + CommonConstants.UN_SALE_PRODUCT_URL,queryConditionMap, header);
            return JSONObject.parseObject(response.getBody(), RestResult.class);
        }
    }

    /**
     * 断路降级方法
     * @param excludeProductIds
     * @param isSale
     * @return
     * @throws SuperCodeException
     */
    public RestResult<Object> getProductFromBaseServiceHystrix( List<String> excludeProductIds,boolean isSale)throws SuperCodeException{
        return RestResult.error("");
    }

    /**
     * 分页查询组织非自卖产品
     * @param searchParams
     * @return
     * @throws Exception
     */
    @Override
    protected List<ProductUnsale> searchResult(ProductUnsale searchParams) throws Exception {
        return mapper.list(searchParams);
    }

    /**
     * 分页查询组织非自卖产品数量
     * @param searchParams
     * @return
     * @throws Exception
     */
    @Override
    protected int count(ProductUnsale searchParams) throws Exception {
        return mapper.count(searchParams);
    }

    /**
     * 获取非自卖详情
     * @param id
     * @param organizationId
     * @return
     */
    public ProductUnsale selectById(Long id, String organizationId) throws SuperCodeException{
        // 获取数据
        ProductUnsale productUnsale = mapper.selectByPrimaryKey(id);
        if(productUnsale == null){
            throw  new SuperCodeException("非自卖产品不存在");
        }
        if(!organizationId.equals(productUnsale.getOrganizationId())){
            throw  new SuperCodeException("非旗下产品");
        }

        // 处理sku
        String skuJsonString = productUnsale.getUnsaleProductSkuInfo();
        List<SkuInfo> skuChilds = JSONArray.parseArray(skuJsonString, SkuInfo.class);
        productUnsale.setSkuChild(skuChilds);
        return productUnsale;
    }

    /**
     * 添加非自卖产品
     * @param productUnsale
     * @return
     */
    public int add(ProductUnsale productUnsale) throws SuperCodeException{
        if(StringUtils.isBlank(productUnsale.getUnsaleProductName())){
            throw new SuperCodeException("产品名称不能为空");
        }
        if(StringUtils.isBlank(productUnsale.getUnsaleProductPic())){
            throw new SuperCodeException("产品图片不能为空");
        }

        // 金额参数转换
        productUnsale.setShowPrice(Float.parseFloat(productUnsale.getShowPriceStr()));
        if(productUnsale.getShowPrice() == null || productUnsale.getShowPrice() <= 0){
            throw new SuperCodeException("产品展示价需大于0.00");
        }

        if(StringUtils.isBlank(productUnsale.getDetail())){
            throw new SuperCodeException("产品详情不能为空");
        }

        if(StringUtils.isBlank(productUnsale.getOrganizationId()) || StringUtils.isBlank(productUnsale.getOrganizationName())){
            throw new SuperCodeException("组织信息不存在");
        }

        if(StringUtils.isBlank(productUnsale.getCreateUserId()) || StringUtils.isBlank(productUnsale.getCreateUserName())){
            throw new SuperCodeException("用户信息不存在");
        }
        // SKU信息转换
        List<SkuInfo> skuChild = productUnsale.getSkuChild();

        if(CollectionUtils.isEmpty(skuChild)){
            productUnsale.setUnsaleProductSkuNum(0);
        }else {
            if(skuChild.size() > 10){
                throw new SuperCodeException("sku数量不可超过十个");
            }
            // 转换sku成json格式
            String skuJsonString = null;
            try {
                skuJsonString = JSONObject.toJSONString(skuChild);
            } catch (Exception e) {
                logger.error("sku转化失败:"+skuChild);
                e.printStackTrace();
                throw new SuperCodeException("sku转化失败");
            }

            productUnsale.setUnsaleProductSkuInfo(skuJsonString);
        }

        // 业务主键
        productUnsale.setProductId(UUID.randomUUID().toString().replace("-",""));
        productUnsale.setCreateDate(new Date());
        int i = mapper.insertSelective(productUnsale);
        if(i!=1){
            throw new SuperCodeException("保存非自卖产品失败");
        }
        return i;
    }

    /**
     * 更新非自卖产品
     * @param productUnsale
     * @return
     */
    public int update(ProductUnsale productUnsale) throws SuperCodeException{
        if(productUnsale.getId() == null || productUnsale.getId() <= 0){
            throw new SuperCodeException("非自卖产品id获取失败");
        }
        if(StringUtils.isBlank(productUnsale.getOrganizationId())){
            throw new SuperCodeException("组织ID不存在");
        }
        ProductUnsale productUnsaleDO = mapper.selectByPrimaryKey(productUnsale.getId());
        if(productUnsaleDO == null || !productUnsaleDO.getOrganizationId().equals(productUnsale.getOrganizationId()) ){
            throw new SuperCodeException("更新失败");

        }
        List<SkuInfo> skuChild = productUnsale.getSkuChild();

        if(CollectionUtils.isEmpty(skuChild)){
            productUnsale.setUnsaleProductSkuNum(0);
        }else {
            if(skuChild.size() > 10){
                throw new SuperCodeException("sku数量不可超过十个");
            }
            // 转换sku成json格式
            String skuJsonString = null;
            try {
                skuJsonString = JSONObject.toJSONString(skuChild);
            } catch (Exception e) {
                logger.error("sku转化失败:" + skuChild);
                e.printStackTrace();
                throw new SuperCodeException("更新非自卖产品sku转化失败");
            }
            productUnsale.setUnsaleProductSkuInfo(skuJsonString);
        }
        int i = mapper.updateByPrimaryKeySelective(productUnsale);
        if(i!=1){
            throw new SuperCodeException("更新非自卖产品失败");
        }
        return i;
    }

    /**
     * 删除非自卖产品
     * @param id
     */
    public int delete(Long id,String organizationId) throws SuperCodeException{
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织ID不存在");
        }

        ProductUnsale productUnsale = mapper.selectByPrimaryKey(id);
        if(productUnsale == null){
            throw new SuperCodeException("删除的信息不存在");
        }
        if(!productUnsale.getOrganizationId().equals(organizationId)){
            logger.error("组织id"+organizationId+"删除非自卖产品"+id+"发送越权");
            throw new SuperCodeException("组织越权");
        }
        int i = mapper.deleteByPrimaryKey(id);
        if(i!=1){
            throw new SuperCodeException("删除非自卖产品失败");
        }
        return i;
    }
}
