package com.jgw.supercodeplatform.marketing.service.integral;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dao.integral.*;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.*;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 积分兑换
 *
 */
@Service
public class IntegralExchangeService extends AbstractPageService<IntegralExchange> {
    private static Logger logger = LoggerFactory.getLogger(IntegralExchangeService.class);
    @Autowired
    private IntegralExchangeMapperExt mapper;
    @Autowired
    private MarketingMembersMapper membersMapper;
    @Autowired
    private DeliveryAddressMapperExt deliveryAddressMapper;

    @Autowired
    private IntegralOrderMapperExt orderMapper;

    @Autowired
    private IntegralRecordMapperExt recordMapper;

    @Autowired
    private CommonUtil commonUtil;

    // 对象转换器
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private ExchangeStatisticsMapperExt exchangeStatisticsMapper;

    @Value("${rest.user.url}")
    private String BASE_SERVICE_NAME;

    /**
     * 分页数据获取
     * @param searchParams
     * @return
     */
    @Override
    protected List<IntegralExchange> searchResult(IntegralExchange searchParams)   {
       return  mapper.list(searchParams);
    }



    /**
     * H5 兑换信息
     * @param organizationId
     * @return
     */
    public List<IntegralExchangeParam> getOrganizationExchange(String organizationId) {
        return  mapper.getOrganizationExchange(organizationId);

    }


    /**
     * 分页count
     * @param searchParams
     * @return
     * @throws Exception
     */
    @Override
    protected int count(IntegralExchange searchParams) throws Exception {
        return  mapper.count(searchParams);
    }

    /**
     * 兑换记录删除
     * @param id
     * @param organizationId
     * @return
     * @throws SuperCodeException
     */
    public int deleteByOrganizationId(Long id, String organizationId) throws SuperCodeException {
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
        if(id != null && id <= 0){
            throw new SuperCodeException("id不合法",500);
        }
        int i = mapper.deleteByOrganizationId(id, organizationId);
        if(i != 1){
            logger.error("{组织" + organizationId + "删除积分兑换记录记录" + id + " 共"+i+"条}" );
            throw new SuperCodeException("兑换记录不存在",500);
        }
        return i;
    }

    /**
     * 上下架操作
     * @param id
     * @param organizationId
     * @param status
     * @return
     * @throws SuperCodeException
     */
    public int updateStatus(Long id, String organizationId,Byte status) throws SuperCodeException {
        // 校验
        validateUpdateStatus(id,organizationId,status);

        // 获取记录看看能否上架
        IntegralExchange updateStatus = new IntegralExchange();
        updateStatus.setId(id);
        updateStatus.setOrganizationId(organizationId);
        updateStatus.setStatus(status);
        // 兑换活动状态3上架1手动下架2自动下架
        int i = 0;
        if(status == (byte) 3){
            // 准备上架
            i =  mapper.updateStatusUp(updateStatus);
            if(i != 1){
                logger.error("{组织" + organizationId + "上架" + id + " 共"+i+"条}" );
                throw new SuperCodeException("上架失败",500);
            }

        }else {
            // 准备下架status 为 1手动下架
            i =  mapper.updateStatusLowwer(updateStatus);
            if(i != 1){
                logger.error("{组织" + organizationId + "下架" + id + " 共"+i+"条}" );
                throw new SuperCodeException("下架失败",500);
            }
        }
        return i;

    }

    /**
     * 查看详情
     * @param id
     * @return
     * @throws SuperCodeException
     */
    public IntegralExchange selectById(Long id, String organizationId) throws SuperCodeException{
        validateSelectById(id,organizationId);
        IntegralExchange integralExchange = mapper.selectByPrimaryKey(id);
        if(organizationId.equals(integralExchange.getOrganizationId())){
            return integralExchange;
        }else {
            logger.error("组织"+organizationId+"发生数据越权,数据id"+id );
            throw new SuperCodeException("组织" + organizationId + "无法查看" +id +"数据");
        }
    }

    /**
     * H5会员查看详情|第一个详情页
     * @param productId
     * @return
     * @throws SuperCodeException
     */
    public IntegralExchangeDetailParam selectById(String productId) throws SuperCodeException{
        if(StringUtils.isEmpty(productId) ){
            throw new SuperCodeException("兑换记录不存在");
        }
        // 查询兑换信息
        List<IntegralExchangeDetailParam>  integralExchangeDetailParams = mapper.selectH5ById(productId);
        if(integralExchangeDetailParams == null || integralExchangeDetailParams.size() <= 0){
            throw new SuperCodeException("商品信息不存在");
        }
         IntegralExchangeDetailParam  integralExchangeDetailParam= integralExchangeDetailParams.get(0);
        // 存在sku
        if(integralExchangeDetailParam.getSkuStatus() != 0 ){
            // 存在sku产品不展示库存
            integralExchangeDetailParam.setHaveStock(null);
        }
//        else{
            // desc 前端希望在下一个页面携带库存信息
            // 不存在sku产品不展示库存
//            int stock = 0;
//            for (IntegralExchangeDetailParam ed : integralExchangeDetailParams){
//                stock = ed.getHaveStock() + stock;
//            }
 //            integralExchangeDetailParam.setHaveStock(stock);
//        }
        // 查询详情
        RestResult datailFromBaseServiceResult = getDetail(integralExchangeDetailParam,integralExchangeDetailParams.get(0).getExchangeResource());
        if(datailFromBaseServiceResult.getState() == 200){
            integralExchangeDetailParam.setDetail((String) datailFromBaseServiceResult.getResults());
        }
        if(integralExchangeDetailParam.getDetail() == null){
            logger.info("详情数据不存在,产品ID"+ integralExchangeDetailParam.getProductId());
        }
        return integralExchangeDetailParam;
    }
    // 选择线程池还是信号灯
//    @HystrixCommand(fallbackMethod = "getDetailByhystrix") //断路器命令
    public RestResult  getDetail( IntegralExchangeDetailParam integralExchangeDetailParam,Byte exchangeResource) throws SuperCodeException{
        // 查询参数
          Map productId = new HashMap();
        productId.put("productId",integralExchangeDetailParam.getProductId());
       //兑换资源0非自卖1自卖产品
        if(1== exchangeResource.intValue()){
            // 查询基础信息自卖产品
            ResponseEntity<String> response = restTemplateUtil.getRequestAndReturnJosn(BASE_SERVICE_NAME + CommonConstants.SALE_PRODUCT_DETAIL_URL,productId, null);
            JSONObject parse = JSONObject.parseObject(response.getBody());
            if( parse.getInteger("state") == 200){
                String detail = parse.getJSONObject("results").getString("productDetails");
                return RestResult.success("success",detail);
            }else {
                logger.error("商品详情查询失败的返回数据"+response.getBody());
                return RestResult.error(null);
            }

        }else if(0==exchangeResource.intValue()){
            // 查询基础信息非自卖产品
            ResponseEntity<String> response = restTemplateUtil.getRequestAndReturnJosn(BASE_SERVICE_NAME + CommonConstants.UN_SALE_PRODUCT_DETAIL_URL, productId, null);
            JSONObject parse = JSONObject.parseObject(response.getBody());
            if( parse.getInteger("state") == 200){
                String detail = parse.getJSONObject("results").getString("productDetails");
                return RestResult.success("success",detail);
            }else {
                return RestResult.error(null);
            }
        }
        return RestResult.error("");




        }

    // hystrix方法
    public RestResult  getDetailByhystrix(IntegralExchangeDetailParam integralExchangeDetailParam,Byte exchangeResource) throws SuperCodeException{
        logger.error("base service failure by hystrix!");
        return RestResult.error("");
    }

    /**
     * 兑换详情SKU+地址信息|【h5会员】第二个详情页,预下单页面
     * @param productId
     * @return
     */
    public IntegralExchangeSkuDetailAndAddress detailSkuByMember(String productId, Long memberId) throws SuperCodeException{
        // 初始化返回
        IntegralExchangeSkuDetailAndAddress result = new IntegralExchangeSkuDetailAndAddress();

        // 查询兑换信息
        List<IntegralExchange> integralExchanges = mapper.selectH5ByIdFirst(productId);
        if(CollectionUtils.isEmpty(integralExchanges)){
            throw new SuperCodeException("产品不存在");
        }

        if(integralExchanges.get(0).getSkuStatus() == 0){
            // 无sku,产品不可重复添加，所以数据只有1条| 补充product信息
            modelMapper.map(integralExchanges.get(0),result);
        }else{
            List<SkuInfo> skuInfos = new ArrayList<>();
            for(IntegralExchange integralExchange : integralExchanges){
                // 注意此时库存页面和库可以不一致
                SkuInfo skuInfo = modelMapper.map(integralExchange, SkuInfo.class);
                skuInfos.add(skuInfo);
            }
            // 添加sku信息
            result.setSkuInfos(skuInfos);
            result.setProductId(integralExchanges.get(0).getProductId());
        }

        // 补充地址
        DeliveryAddress deliveryAddress = null;
        List<DeliveryAddress> deliveryAddresses = deliveryAddressMapper.selectByMemberId(memberId);
        if(deliveryAddresses != null && deliveryAddresses.size() > 0 ){
            for(DeliveryAddress da : deliveryAddresses){
                if(da.getDefaultUsing().intValue() == 0){
                    deliveryAddress = da;
                    break;
                }
            }
            //  优先默认地址; 否则地址为空 现在时间字段删除了【所有跟ID走】
            if(deliveryAddress == null){
                deliveryAddress = deliveryAddresses.get(deliveryAddresses.size()-1);
            }
        }

        result.setDeliveryAddress(deliveryAddress);
        return result;

    }


    /**
     * H5兑换:具有事务支持 es操作在最后
     * @param exchangeProductParam
     */
    @Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
    public void exchanging(ExchangeProductParam exchangeProductParam) throws SuperCodeException{
        // 基本校验，地址，兑换数量，memberID,productID,组织id,sku信息【可有可无】
        // 收货地址 收货手机 收货名
        validateBasicWhenExchanging(exchangeProductParam);
        // 库存为0不可兑换
        //下架不可兑换[存在可能性]
        // 超过限兑换数量不可兑换
        // 积分不够不可兑换
        // 开始锁表|涉及库存
        Map userExchangenum = validateBizWhenExchanging(exchangeProductParam);
        // 减少库存: 兑换数量不可以超过库存
        // 减少积分
        // 创建订单记录
        // 添加限兑数量
        // 额外数据补充
        doexchanging(exchangeProductParam,userExchangenum);

    }

    /**
     * 兑换操作
     * @param exchangeProductParam
     * @param exchangeNumKey
     * @throws SuperCodeException
     */
    private void doexchanging(ExchangeProductParam exchangeProductParam,Map exchangeNumKey) throws SuperCodeException{
        // 支付方式暂不考虑
        // 减少库存: 兑换数量不可以超过库存
        int i = mapper.reduceStock(exchangeProductParam);
        // 在同一个事务中，此库存减少可见
        if(i == 0){
            throw new SuperCodeException("库存不足");
        }else{
            // 会员减少积分
            membersMapper.deleteIntegral((Integer) exchangeNumKey.get("ingetralNum"));
            // 创建订单记录
            orderMapper.insertSelective(getOrderDo(exchangeProductParam));
            // 创建积分记录
            recordMapper.insertSelective(getRecordDo(exchangeProductParam));
            // 添加限兑数量
            ExchangeStatistics exchangeStatistics = new ExchangeStatistics();
            exchangeStatistics.setOrganizationId(exchangeProductParam.getOrganizationId());
            exchangeStatistics.setProductId(exchangeProductParam.getProductId());
            exchangeStatistics.setMemberId(exchangeProductParam.getMemberId());
            exchangeStatistics.setExchangeNum((Integer) exchangeNumKey.get("count"));
            int j = exchangeStatisticsMapper.updateCount(exchangeStatistics);

        }
    }

    /**
     * 创建订单信息
     * @param exchangeProductParam
     * @return
     */
    private IntegralOrder getOrderDo(ExchangeProductParam exchangeProductParam) {
        //
        IntegralOrder order = modelMapper.map(exchangeProductParam, IntegralOrder.class);
        List<IntegralExchange> integralExchanges = mapper.selectByProductId(exchangeProductParam.getProductId(),exchangeProductParam.getSkuId());
        // 订单号
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
        // 订单地址
         order.setExchangeIntegralNum(exchangeProductParam.getExchangeNum() * integralExchanges.get(0).getExchangeIntegral());
         // 待发货
        order.setStatus((byte)0);
        MarketingMembers memberById = membersMapper.getMemberById(exchangeProductParam.getMemberId());
        // 会员名
        order.setMemberName(memberById.getUserName());
        // 订单创建日期
        order.setCreateDate(new Date());
        // 发货时间
        order.setDeliveryDate(null);
        // 组织名称
        order.setOrganizationName(integralExchanges.get(0).getOrganizationName());

        // 产品信息
        order.setProductId(integralExchanges.get(0).getProductId());
        order.setProductPic(integralExchanges.get(0).getProductPic());
        order.setProductName(integralExchanges.get(0).getProductName());
        order.setSkuId(integralExchanges.get(0).getSkuId());
        order.setSkuName(integralExchanges.get(0).getSkuName());
        order.setSkuUrl(integralExchanges.get(0).getSkuUrl());
        // 展示价
        order.setShowPrice(integralExchanges.get(0).getShowPrice());
        return order;
    }

    /**
     * 创建积分对象
     * @param exchangeProductParam
     * @return
     */
    private IntegralRecord getRecordDo(ExchangeProductParam exchangeProductParam) {
        IntegralRecord record = modelMapper.map(exchangeProductParam, IntegralRecord.class);
        MarketingMembers memberById = membersMapper.getMemberById(exchangeProductParam.getMemberId());
        record.setMemberType(memberById.getMemberType());
        record.setMemberName(memberById.getUserName());
        // 积分记录存储的为会员手机号而不是收货手机
        record.setMobile(memberById.getMobile());

        // 积分兑换原因和编码
        record.setIntegralReasonCode(IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReasonCode());
        record.setIntegralReason(IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReason());

        List<IntegralExchange> integralExchanges = mapper.selectByProductId(exchangeProductParam.getProductId(),null);
        record.setProductName(integralExchanges.get(0).getProductName());
//        if(integralExchanges.get(0).getExchangeResource() == 1){
            // 关于自卖产品的码信息
            // 目前自卖和非自卖兑换产生的积分记录都没有码信息
//        }
        // 门店ID
        record.setCustomerId(memberById.getCustomerId());
        // 门店名称
        record.setCustomerName(memberById.getCustomerName());
        // 创建日期
        record.setCreateDate(new Date());
        // 组织名称
        record.setOrganizationName(integralExchanges.get(0).getOrganizationName());
        // 积分数值：负数
        record.setIntegralNum(0-( integralExchanges.get(0).getExchangeIntegral()* exchangeProductParam.getExchangeNum()));
        return record;
    }
    /**
     * 兑换校验业务校验
     *
     * @param exchangeProductParam
     * @return 兑换数量
     */
    private Map validateBizWhenExchanging(ExchangeProductParam exchangeProductParam) throws SuperCodeException{
        // 手机号校验
        checkPhoneFormat(exchangeProductParam.getMobile());
        // 查看memberID存在
        MarketingMembers member = membersMapper.getMemberById(exchangeProductParam.getMemberId());
        if(member == null){
            logger.error(" {会员ID"+exchangeProductParam.getMemberId()+"信息异常 ,统计出0条} ");
            throw new SuperCodeException("会员不存在");
        }
        if(member.getState() == (byte)0){
            throw new SuperCodeException("您已被管理员禁用...");
        }
        // 查看组织id - productID-sku-是否存在 开启锁，强制阻塞
        IntegralExchange exists = mapper.exists(exchangeProductParam.getOrganizationId(), exchangeProductParam.getProductId(), exchangeProductParam.getSkuName());
        if(exists == null){
            logger.error(" {兑换信息不存在"+ JSONObject.toJSONString(exchangeProductParam) +"} ");
            throw new SuperCodeException("兑换信息不存在");
        }
        if(exists.getStatus() != 3){
            throw new SuperCodeException("兑换商品已经下架");
        }
        if(exists.getHaveStock() <= 0){
            throw new SuperCodeException("库存不足");
        }

        ExchangeStatistics exchangeStatistics = exchangeStatisticsMapper.selectCount(exchangeProductParam.getOrganizationId(), exchangeProductParam.getProductId(), exchangeProductParam.getMemberId());
        if (exchangeStatistics == null){
            exchangeStatistics = new ExchangeStatistics();
            exchangeStatistics.setExchangeNum(0);
        }
        if(exists.getCustomerLimitNum() <= exchangeStatistics.getExchangeNum() + exchangeProductParam.getExchangeNum()){
            throw new SuperCodeException("兑换数量超过上限");

        }
        if(member.getHaveIntegral() < exchangeProductParam.getExchangeNum() * exists.getExchangeIntegral()){
            throw new SuperCodeException("积分不足");
        }
        Map userExchangenum = new HashMap(2);// 2次方:4个容量
        // 需要被更新的兑换数
        userExchangenum.put("count",exchangeStatistics.getExchangeNum() + exchangeProductParam.getExchangeNum());
        // 兑换积分数
        userExchangenum.put("ingetralNum",exchangeProductParam.getExchangeNum() * exists.getExchangeIntegral());
        return userExchangenum;

    }

    /**
     * 兑换校验基础校验
     * @param exchangeProductParam
     * @throws SuperCodeException
     */
    private void validateBasicWhenExchanging(ExchangeProductParam exchangeProductParam) throws SuperCodeException{
        if(exchangeProductParam == null){
            throw new SuperCodeException("兑换信息不全000001");
        }
        if(exchangeProductParam.getMemberId() == null && exchangeProductParam.getMemberId() <= 0){
            throw new SuperCodeException("兑换信息不全000002");
        }
        if(StringUtils.isEmpty(exchangeProductParam.getAddress())){
            throw new SuperCodeException("兑换信息不全000003");
        }
        if(StringUtils.isEmpty(exchangeProductParam.getMobile())){
            throw new SuperCodeException("兑换信息不全000004");
        }
        if(StringUtils.isEmpty(exchangeProductParam.getName())){
            throw new SuperCodeException("兑换信息不全000005");
        }
        if(StringUtils.isEmpty(exchangeProductParam.getOrganizationId())){
            throw new SuperCodeException("兑换信息不全000006");
        }
        if(StringUtils.isEmpty(exchangeProductParam.getProductId())){
            throw new SuperCodeException("兑换信息不全000007");
        }


        if(exchangeProductParam.getExchangeNum() == null || exchangeProductParam.getExchangeNum() <= 0){
            throw new SuperCodeException("兑换信息不全000010");
        }

    }

    /**
     * 兑换编辑
     * @param integralExchange
     * @param organizationId
     */
    @Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
    public void updateByOrganizationId(IntegralExchangeUpdateParam integralExchange, String organizationId, String organizationName) throws SuperCodeException {
        // 更新: 先删除，后新增
        if(integralExchange == null){
            throw new SuperCodeException("编辑的兑换信息丢失");
        }
        if(integralExchange.getId() <= 0L){
            throw new SuperCodeException("编辑的Id不合法");
        }
        if(StringUtils.isBlank(organizationId) || StringUtils.isBlank(organizationName) ){
            throw new SuperCodeException("组织信息丢失");
        }

        IntegralExchangeAddParam integralExchangeAddParam = modelMapper.map(integralExchange, IntegralExchangeAddParam.class);
        int i = deleteByOrganizationId(integralExchange.getId(), organizationId);
        if (i!= 1){
            throw new SuperCodeException("更新编辑信息失败");
        }
        // 调用新增方法
        add(integralExchangeAddParam,organizationId,organizationName);
//        // 校验
//        Long id = integralExchange.getId();
//        validateUpdateByOrganizationId(id,organizationId,integralExchange);
//        integralExchange  =addFieldWhenUpdate(integralExchange);
//        IntegralExchange result = mapper.selectByPrimaryKey(id);
//        validateBizWithUpdate(integralExchange,result);
//        if(organizationId.equals(organizationId)){
//            int i = mapper.updateByPrimaryKeySelective(integralExchange);
//            if(i != 1){
//                logger.error("{组织" + organizationId + "标记积分兑换记录" + id + " 共"+i+"条}" );
//                throw new SuperCodeException("编辑积分记录失败",500);
//            }
//        }else {
//            logger.error("组织"+organizationId+"发生数据越权,数据id"+ id);
//            throw new SuperCodeException("组织" + organizationId + "无法查看" + id +"数据");
//        }
    }

//    /**
//     * 更新的业务校验
//     * @param integralExchangeVO
//     * @param integralExchangeDO
//     */
//    private void validateBizWithUpdate(IntegralExchange integralExchangeVO, IntegralExchange integralExchangeDO) throws SuperCodeException{
//        // 兑换的资源类型不可以修改
//        if(integralExchangeDO.getExchangeResource().intValue() != integralExchangeVO.getExchangeResource().intValue()){
//            throw new SuperCodeException("兑换的资源类型不可以修改");
//        }
//
//        // 兑换库存只能增不能减少
//        if(integralExchangeDO.getExchangeStock() > integralExchangeVO.getExchangeStock()){
//            throw new SuperCodeException("兑换库存只能增加不可以减少");
//        }
//        // 兑换库存处理
//        if(integralExchangeDO.getExchangeStock() < integralExchangeVO.getExchangeStock()){
//            integralExchangeVO.setHaveStock( integralExchangeDO.getHaveStock() + integralExchangeVO.getExchangeStock() - integralExchangeDO.getExchangeStock() );
//        }
//        // 每人限兑只能增不能减
//        if(integralExchangeDO.getCustomerLimitNum() > integralExchangeVO.getCustomerLimitNum()){
//            throw new SuperCodeException("兑换库存只能增加不可以减少");
//        }
//        // 兑换的产品不可以修改
//        if( integralExchangeDO.getProductId().equals(integralExchangeVO.getProductId())){
//            throw new SuperCodeException("兑换的产品不可以修改");
//        }
//        if(integralExchangeDO.getSkuName() != null && !integralExchangeDO.getSkuName().equals(integralExchangeVO.getSkuName())){
//            throw new SuperCodeException("兑换的sku不可以修改");
//        }
//
//
//    }


    /**
     * 新增兑换
     * @param integralExchange
     */
    public void add(IntegralExchangeAddParam integralExchange,String organizationId,String organizationName) throws SuperCodeException{
        validateBasicWhenAdd(integralExchange, organizationId, organizationName);
        validateBizWhenAdd(integralExchange);
        // 根据业务补充数据
        List<IntegralExchange> integralExchanges = addFeildByBuzWhenAdd(integralExchange,organizationId,organizationName);
        int i = mapper.insertBatch(integralExchanges);
        if(1 > i){
            throw new SuperCodeException("插入兑换记录失败",500);
        }

    }

    /**
     * 新增对象基础校验
     * @param integralExchange
     */
    private void validateBasicWhenAdd(IntegralExchangeAddParam integralExchange,String organizationId,String organizationName) throws SuperCodeException{
        if(integralExchange == null){
            throw new SuperCodeException("兑换信息不存在");
        }
        if(organizationId == null){
            throw new SuperCodeException("组织id不存在");
        }
        if(organizationName == null){
            throw new SuperCodeException("组织名称不存在");
        }
        // 目前写死，只有会员 upgrade
        if(integralExchange.getMemberType() != 0){
            throw new SuperCodeException("会员类型不存在");
        }

        if(integralExchange.getExchangeResource() != 1 && integralExchange.getExchangeResource() != 0){
            throw new SuperCodeException("兑换资源类型不存在");
        }
        if(integralExchange.getExchangeIntegral() <= 0){
            throw new SuperCodeException("兑换积分为正整数");
        }
        if(  integralExchange.getExchangeStock()  == null || integralExchange.getExchangeStock() <= 0 ){
            integralExchange.setExchangeStock(0);
            // throw new SuperCodeException("兑换库存为正整数");
        }

        if(integralExchange.getCustomerLimitNum()  == null || integralExchange.getCustomerLimitNum() <= 0){
            // 不传，则认为无上限给999999
            integralExchange.setCustomerLimitNum(999999);
            //throw new SuperCodeException("每人限兑为正整数");
        }


        // 默认上架:应前端要求将0改成3
        integralExchange.setStatus((byte)3);
        if(integralExchange.getPayWay() == null || integralExchange.getPayWay() != 0){
            integralExchange.setPayWay((byte)0);
//            throw new SuperCodeException("支付手段目前只有积分");
        }
        if(integralExchange.getUndercarriageSetWay() == null ){
            throw new SuperCodeException("请设置自动下架方式");
        }
        if(integralExchange.getUndercarriageSetWay() == 1 && (integralExchange.getUnderCarriage() == null )){
            throw new SuperCodeException("请设置自动下架时间");
        }
        if (integralExchange.getUndercarriageSetWay() == 1 &&  new Date().after(integralExchange.getUnderCarriage()) ){
            throw new SuperCodeException("请设置自动下架为未来时间");
        }
        if(integralExchange.getStockWarningNum() == null){
            // 负数;则不进行预警
            integralExchange.setStockWarningNum(-1);
        }
        if(CollectionUtils.isEmpty(integralExchange.getProducts())){
            throw new SuperCodeException("兑换产品信息不存在");
        }else{
            // start 产品抽离到基础信息，直接网页传递所有基础信息数据
            // end
            List<ProductAddParam> products = integralExchange.getProducts();
            for(ProductAddParam productAddParam : products){
                if(StringUtils.isBlank(productAddParam.getProductName())){
                    throw new SuperCodeException("产品名称不存在");
                }
                if(StringUtils.isBlank(productAddParam.getProductPic())){
                    productAddParam.setProductPic("");
                }
                if(StringUtils.isBlank(productAddParam.getShowPriceStr())){
                    productAddParam.setShowPriceStr("0.00");
                }
                if(productAddParam.getProductId() == null){
                    throw new SuperCodeException("产品id不存在");
                }else {
                    List<SkuInfo> skuinfos = productAddParam.getSkuInfo();
                     // sku可以不存在;存在则进行非空校验
                    if(!CollectionUtils.isEmpty(skuinfos)){
                        for(SkuInfo skuinfo:skuinfos ){
                            if(StringUtils.isBlank(skuinfo.getSkuName())){
                                throw new SuperCodeException("sku名称不存在");
                            }
                            if(StringUtils.isBlank(skuinfo.getSkuUrl())){
//                                null == skuinfo.getSkuUrl()
                                throw new SuperCodeException("sku 图片不存在");
                            }
                            if(StringUtils.isBlank(skuinfo.getSkuId())){
                                throw new SuperCodeException("sku id不存在");
                            }
                        }
                    }
                }
             }
        }
    }

    /**
     * 已经存在的产品|sku不可以添加
     * @param integralExchange
     * @throws SuperCodeException
     */
    private void validateBizWhenAdd(IntegralExchangeAddParam integralExchange) throws  SuperCodeException{
        // 已经存在的产品[SKU]不可以再次添加
        // 由于H5的展示,现在要求产品和sku只能两者选1[已经存在的产品兑换信息,给产品加sku,不可基于这条产品dku添加兑换][已经存在sku,删除产品sku,不可基于产品加sku]
        // 用来保证h5正常展示
        List<ProductAddParam> products = integralExchange.getProducts();
        String[] productIds =new String[products.size()];
        for(int i=0;i<products.size();i++){
            productIds[i]=products.get(i).getProductId();
        }
        // 一次查出所有,有些没有sku[比较productID],有些有sku[比较skuName]
        List<IntegralExchange> having = mapper.having(productIds);

        // 添加数据有sku   [数据库有sku  sku相同不可添加   |无sku不可添加]
        // 无sku [数据库有productID],不可添加
        if(!CollectionUtils.isEmpty(having)){
            for(ProductAddParam productAddParam : products){
                for(IntegralExchange have: having){
                    if(!productAddParam.getProductId().equals(have.getProductId())){
                        continue;
                    }
                    // 新增数据没有sku|数据库有无sku都不可添加
                    if(have.getProductId().equals(productAddParam.getProductId()) && CollectionUtils.isEmpty(productAddParam.getSkuInfo())){
                        throw new SuperCodeException("产品已经添加");
                    }
                    // 0无sku,1有sku
                    // 新增数据有sku || 数据库无SKU
                    if(have.getProductId().equals(productAddParam.getProductId()) && !CollectionUtils.isEmpty(productAddParam.getSkuInfo()) && have.getSkuStatus() == 0){
                        // 确保产品和sku只能二选一,保证H5展示正常
                        throw new SuperCodeException("存在无sku产品兑换,不可在添加含sku信息产品兑换");
                    }


                    // 0无sku,1有sku
                    // 新增数据有sku || 数据库有SKU
                    // sku相同不可添加
                    if(have.getProductId().equals(productAddParam.getProductId()) && !CollectionUtils.isEmpty(productAddParam.getSkuInfo()) && have.getSkuStatus() == 1){
                        List<SkuInfo> skuinfos = productAddParam.getSkuInfo();
                        for(SkuInfo skuinfo: skuinfos){
                            if(skuinfo.getSkuId().equals(have.getSkuId())){
                                throw new SuperCodeException("产品SKU已经添加");
                            }
                        }
                    }
                }
            }
        }


    }

    /**
     * 新增兑换时候补充数据
     * @param integralExchange
     * @return
     */
    private List<IntegralExchange> addFeildByBuzWhenAdd(IntegralExchangeAddParam integralExchange,String organizationId,String organizationName) {
        List<IntegralExchange> list = new ArrayList<>();
        List<ProductAddParam> products = integralExchange.getProducts();
        // 0非自卖1自卖产品
        Byte exchangeResource = integralExchange.getExchangeResource();
        for(ProductAddParam productAddParam : products){{
            List<SkuInfo> skuinfos = productAddParam.getSkuInfo();
            if(CollectionUtils.isEmpty(skuinfos)){
                // 添加产品记录
                IntegralExchange exchangeDo = modelMapper.map(integralExchange, IntegralExchange.class);
                // 无sku
                exchangeDo.setSkuStatus((byte)0);
                exchangeDo.setOrganizationId(organizationId);
                exchangeDo.setOrganizationName(organizationName);
                exchangeDo.setHaveStock(integralExchange.getExchangeStock());
                if(exchangeDo.getHaveStock() >= exchangeDo.getStockWarningNum() ){
                    // 不发出警告
                    exchangeDo.setStockWarning((byte)(0));
                }else{
                    exchangeDo.setStockWarning((byte)(1));
                }
                // 基础信息全部由网页传递
                // 产品id
                exchangeDo.setProductId(productAddParam.getProductId());
                // 产品图片
                exchangeDo.setProductPic(productAddParam.getProductPic());
                // 产品名称
                exchangeDo.setProductName(productAddParam.getProductName());
                // 展示价格
                exchangeDo.setShowPrice(Float.parseFloat(productAddParam.getShowPriceStr()));
                list.add(exchangeDo);
            }else {
                // 添加sku记录
                for(SkuInfo skuinfo : skuinfos){
                    // 添加产品记录
                    IntegralExchange exchangeDo = modelMapper.map(integralExchange, IntegralExchange.class);
                    // 有sku
                    exchangeDo.setSkuStatus((byte)1);
                    exchangeDo.setOrganizationId(organizationId);
                    exchangeDo.setOrganizationName(organizationName);
                    exchangeDo.setHaveStock(integralExchange.getExchangeStock());
                    // 基础信息全部由网页传递
                    // 产品id
                    exchangeDo.setProductId(productAddParam.getProductId());
                    // 产品图片
                    exchangeDo.setProductPic(productAddParam.getProductPic());
                    // 产品名称
                    exchangeDo.setProductName(productAddParam.getProductName());
                    // 展示价格
                    exchangeDo.setShowPrice(Float.parseFloat(productAddParam.getShowPriceStr()));
                    // sku id
                    exchangeDo.setSkuId(skuinfo.getSkuId());
                    // sku 图片
                    exchangeDo.setSkuUrl(skuinfo.getSkuUrl());
                    // sku 名称
                    exchangeDo.setSkuName(skuinfo.getSkuName());
                    if(exchangeDo.getHaveStock() >= exchangeDo.getStockWarningNum() ){
                        // 不发出警告
                        exchangeDo.setStockWarning((byte)(0));
                    }else{
                        exchangeDo.setStockWarning((byte)(1));
                    }
                    list.add(exchangeDo);
                }
            }
        }
        }
        return list;
    }


    /**
     * 编辑兑换记录的属性转换与添加
     * @param integralExchange
     * @return
     */
//    private IntegralExchange addFieldWhenUpdate(IntegralExchange integralExchange) {
//        //  编辑兑换记录的属性转换与添加
//        return  integralExchange;
//    }




    /**
     * 查看详情校验
     * @param id
     * @param organizationId
      */
    private void validateSelectById(Long id, String organizationId) throws SuperCodeException{
        if(id == null || id <= 0 ){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织id不存在",500);
        }

    }

    /**
     * 上下架校验
     * @param id
     * @param organizationId
     * @param status
     */
    private void validateUpdateStatus(Long id, String organizationId, Byte status) throws SuperCodeException{
        if(id == null || id <= 0 ){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织id不存在",500);
        }

        if(status.intValue() != 3 && status.intValue() != 1){
            throw new SuperCodeException("状态不在合法范围",500);

        }
    }




    /**
     * 兑换记录更新校验
     * @param id
     * @param organizationId
     */
    private void validateUpdateByOrganizationId(Long id, String organizationId,IntegralExchange integralExchange) throws SuperCodeException{
        if(integralExchange == null){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(id == null || id <= 0 ){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织id不存在",500);
        }

    }



    /**
     * 新增兑换校验
     * @param id
     * @param organizationId
     */
    private void validateAdd( IntegralExchange integralExchange) throws SuperCodeException{

        if(StringUtils.isBlank(integralExchange.getOrganizationId())){
            throw new SuperCodeException("组织id不存在",500);
        }

    }

}
