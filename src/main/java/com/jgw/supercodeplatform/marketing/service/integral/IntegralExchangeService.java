package com.jgw.supercodeplatform.marketing.service.integral;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.dao.integral.*;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.*;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

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
    private UnsaleProductService unsaleService;

    @Autowired
    private ProductUnsaleMapperExt unsaleMapper;

    // 对象转换器
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    //  服务端有很多策略配置;准实时特性强，慎用
    @Autowired
    private CodeEsService codeEsService;

    @Autowired
    private RestTemplate restTemplate;
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

        // 兑换活动状态0上架1手动下架2自动下架
        int i = 0;
        if(status == (byte) 0){
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
     * H5会员查看详情
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
        }else{
            // desc 前端希望在下一个页面携带库存信息
            // 不存在sku产品不展示库存
//            int stock = 0;
//            for (IntegralExchangeDetailParam ed : integralExchangeDetailParams){
//                stock = ed.getHaveStock() + stock;
//            }
 //            integralExchangeDetailParam.setHaveStock(stock);
        }
        // 查询详情
        if(integralExchangeDetailParam.getDetail() == null){
            // TODO URL补充【基础信息】
            Map datailFromBaseService = restTemplate.postForObject(BASE_SERVICE_NAME,integralExchangeDetailParam,Map.class);
            integralExchangeDetailParam.setDetail((String) datailFromBaseService.get("detail"));
        }
        if(integralExchangeDetailParam.getDetail() == null){
            throw new SuperCodeException("商品详情信息不存在");
        }
         return integralExchangeDetailParam;
    }

    /**
     * 兑换详情SKU+地址信息|【h5会员】
     * @param productId
     * @return
     */
    public IntegralExchangeSkuDetailAndAddress detailSkuByMember(Long productId, Long memberId) throws SuperCodeException{
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
            List<SkuInfo> skuInfos = new ArrayList<SkuInfo>();
            for(IntegralExchange integralExchange : integralExchanges){
                // 注意此时库存页面和库可以不一致
                SkuInfo skuInfo = modelMapper.map(integralExchange, SkuInfo.class);
                skuInfos.add(skuInfo);
            }
            // 添加sku信息
            result.setSkuInfos(skuInfos);
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
        }
        //  优先默认地址; 否则地址为空
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
            codeEsService.putExchangeCount((String) exchangeNumKey.get("key"), (Integer) exchangeNumKey.get("count"));

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
        List<IntegralExchange> integralExchanges = mapper.selectByProductId(exchangeProductParam.getProductId());
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


        // TODO  原因: 从unitcode表查询【暂时保持，后期修改】
        record.setIntegralReasonCode(1);
        record.setIntegralReason("兑换商品");

        List<IntegralExchange> integralExchanges = mapper.selectByProductId(exchangeProductParam.getProductId());
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
        // 查看组织id - productID-sku-是否存在 开启锁，强制阻塞
        IntegralExchange exists = mapper.exists(exchangeProductParam.getOrganizationId(), exchangeProductParam.getProductId(), exchangeProductParam.getSkuName());
        if(exists == null){
            logger.error(" {兑换信息不存在"+ JSONObject.toJSONString(exchangeProductParam) +"} ");
            throw new SuperCodeException("兑换信息不存在");
        }
        if(exists.getStatus() != 0){
            throw new SuperCodeException("兑换商品已经下架");
        }
        if(exists.getHaveStock() <= 0){
            throw new SuperCodeException("库存不足");
        }
        StringBuffer key = new StringBuffer("exchange:num").append(exchangeProductParam.getMemberId()).append(":").append(exchangeProductParam.getProductId());
        if(exchangeProductParam.getSkuName() != null){
            key.append(exchangeProductParam.getSkuName());
        }
        Long userCount = codeEsService.getExchangeCount(key.toString());
        if(exists.getCustomerLimitNum() <= userCount + exchangeProductParam.getExchangeNum()){
            throw new SuperCodeException("兑换数量超过上限");

        }
        if(member.getHaveIntegral() < exchangeProductParam.getExchangeNum() * exists.getExchangeIntegral()){
            throw new SuperCodeException("积分不足");
        }
        Map userExchangenum = new HashMap(2);// 2次方:4个容量
        userExchangenum.put("key",key.toString());
        userExchangenum.put("count",userCount + exchangeProductParam.getExchangeNum());
        userExchangenum.put("ingetralNum",exchangeProductParam.getExchangeNum() * exists.getExchangeIntegral() );
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
        if(StringUtils.isEmpty(exchangeProductParam.getSkuName()) && !StringUtils.isEmpty(exchangeProductParam.getSkuUrl())){
            throw new SuperCodeException("兑换信息错误000008");
        }
        // SKU全无或全有才可
        if(!StringUtils.isEmpty(exchangeProductParam.getSkuName()) && StringUtils.isEmpty(exchangeProductParam.getSkuUrl())){
            throw new SuperCodeException("兑换信息错误000009");
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

        if(integralExchange.getExchangeResource() != 0 && integralExchange.getExchangeResource() != 0){
            throw new SuperCodeException("兑换资源类型不存在");
        }
        if(integralExchange.getExchangeIntegral() <= 0){
            throw new SuperCodeException("兑换积分为正整数");
        }
        if(integralExchange.getExchangeStock() <= 0){
            throw new SuperCodeException("兑换库存为正整数");
        }

        if(integralExchange.getCustomerLimitNum() <= 0){
            throw new SuperCodeException("每人限兑为正整数");
        }

        if(integralExchange.getCustomerLimitNum() <= 0){
            throw new SuperCodeException("每人限兑为正整数");
        }
        // 目前默认上架
        integralExchange.setStatus((byte)0);
        if(integralExchange.getPayWay() == null || integralExchange.getPayWay() != 0){
            throw new SuperCodeException("支付手段目前只有积分");
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
            integralExchange.setStockWarningNum(Integer.MAX_VALUE);
        }
        if(integralExchange.getStockWarningNum() <= 0 ){
            throw new SuperCodeException("库存预警为正整数且大于0");
        }

        if(CollectionUtils.isEmpty(integralExchange.getProducts())){
            throw new SuperCodeException("兑换产品信息不存在");
        }else{
            // 产品信息
            List<ProductAddParam> products = integralExchange.getProducts();
            for(ProductAddParam productAddParam : products){
                if(productAddParam.getProductName() == null){
                    throw new SuperCodeException("产品名称不存在");
                }
                 if(productAddParam.getProductId() == null){
                     throw new SuperCodeException("产品id不存在");
                 }else {
                     List<SkuInfo> skuinfos = productAddParam.getSkuinfos();
                     // sku可以不存在;存在则进行非空校验
                     if(!CollectionUtils.isEmpty(skuinfos)){
                         for(SkuInfo skuinfo:skuinfos ){
                             if(StringUtils.isBlank(skuinfo.getSkuName())){
                                 throw new SuperCodeException("sku名称不存在");                             }
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
        List<ProductAddParam> products = integralExchange.getProducts();
        String[] productIds =new String[products.size()];
        for(int i=0;i<products.size();i++){
            productIds[i]=products.get(i).getProductId();
        }
        // 一次查出所有,有些没有sku[比较productID],有些有sku[比较skuName]
        List<IntegralExchange> having = mapper.having(productIds);
        if(!CollectionUtils.isEmpty(having)){
            for(ProductAddParam productAddParam : products){
                for(IntegralExchange have: having){
                    if(!productAddParam.getProductId().equals(have.getProductId())){
                        continue;
                    }
                    // 没有sku
                    if(have.getProductId().equals(productAddParam.getProductId()) && have.getSkuStatus() == 0){
                        throw new SuperCodeException("产品已经添加");
                    }
                    // 有sku
                    if(have.getProductId().equals(productAddParam.getProductId()) && have.getSkuStatus() == 1){
                        List<SkuInfo> skuinfos = productAddParam.getSkuinfos();
                        for(SkuInfo skuinfo: skuinfos){
                            if(skuinfo.getSkuName().equals(have.getSkuName())){
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
            List<SkuInfo> skuinfos = productAddParam.getSkuinfos();
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
                if(exchangeResource == 0){
                    ProductUnsale productUnsale = unsaleMapper.selectByProductId(exchangeDo.getProductId());
                    exchangeDo.setShowPrice(productUnsale.getShowPrice());
                }else{
                    // TODO 调用基础信息展示售价
                }
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
                    if(exchangeDo.getHaveStock() >= exchangeDo.getStockWarningNum() ){
                        // 不发出警告
                        exchangeDo.setStockWarning((byte)(0));
                    }else{
                        exchangeDo.setStockWarning((byte)(1));
                    }
                    if(exchangeResource == 0){
                        ProductUnsale productUnsale = unsaleMapper.selectByProductId(exchangeDo.getProductId());
                        exchangeDo.setShowPrice(productUnsale.getShowPrice());
                        exchangeDo.setSkuName(skuinfo.getSkuName());
                        String skuJsonString = productUnsale.getUnsaleProductSkuInfo();
                        List<SkuInfo> skuChilds = JSONArray.parseArray(skuJsonString, SkuInfo.class);
                        for(SkuInfo skuChild : skuChilds){
                            if(skuinfo.getSkuName().equals(skuChild.getSkuName())){
                                exchangeDo.setSkuUrl(skuChild.getSkuUrl());
                            }
                        }
                    }else{
                        // TODO 调用基础信息展示售价和sku信息
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

        if(status.intValue() != 0 && status.intValue() != 1){
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
