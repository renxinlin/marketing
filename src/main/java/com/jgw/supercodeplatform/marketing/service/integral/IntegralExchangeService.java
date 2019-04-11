package com.jgw.supercodeplatform.marketing.service.integral;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.dao.integral.DeliveryAddressMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralExchangeMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralOrderMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dto.*;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
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
import scala.Int;

import java.util.*;

/**
 * 积分兑换
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
    public IntegralExchangeDetailParam selectById(Long productId) throws SuperCodeException{
        if(productId == null || productId <= 0 ){
            throw new SuperCodeException("兑换记录不存在");
        }
        // 查询兑换信息
        List<IntegralExchangeDetailParam>  integralExchangeDetailParams = mapper.selectH5ById(productId);
        if(integralExchangeDetailParams == null || integralExchangeDetailParams.size() <= 0){
            throw new SuperCodeException("商品信息不存在");
        }
        // TODO 统计库存
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
            // TODO URL补充
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

        // TODO 测试modelMapper数据传递是否成功
        if(integralExchanges.get(0).getSkuStatus() == 0){
            // 无sku,产品不可重复添加，所以数据只有1条
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
       boolean success = doexchanging(exchangeProductParam,userExchangenum);
        // 支付方式暂不考虑

    }

    private boolean doexchanging(ExchangeProductParam exchangeProductParam,Map exchangeNumKey) throws SuperCodeException{
        //暂时只有积分支付
        // 减少库存: 兑换数量不可以超过库存
        // 减少积分
        // 创建订单记录
        // 添加限兑数量
        // 额外数据补充
        // 减少库存
        int i = mapper.reduceStock(exchangeProductParam);
        if(i == 0){
            throw new SuperCodeException("库存不足");
        }else{
            // 额外数据补充
            // 减少积分
            // TODO补充信息
            membersMapper.deleteIntegral((Integer) exchangeNumKey.get("ingetralNum"));
            // 创建订单记录
            orderMapper.insertSelective(getOrderDo(exchangeProductParam));
            // 创建积分记录
            recordMapper.insertSelective(getRecordDo(exchangeProductParam));
            // 添加限兑数量
            codeEsService.putExchangeCount((String) exchangeNumKey.get("key"), (Integer) exchangeNumKey.get("count"));

        }

        return false;
    }

    private IntegralOrder getOrderDo(ExchangeProductParam exchangeProductParam) {
        IntegralOrder order = new IntegralOrder();
        // 订单号
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
        // 订单地址
        order.setAddress(exchangeProductParam.getAddress());
        return order;
    }

    private IntegralRecord getRecordDo(ExchangeProductParam exchangeProductParam) {
        IntegralRecord record = new IntegralRecord();
        // 会员ID
        record.setMemberId(exchangeProductParam.getMemberId());
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
        Map userExchangenum = new HashMap();
        userExchangenum.put("",key.toString());
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
    public void updateByOrganizationId(IntegralExchange integralExchange, String organizationId) throws SuperCodeException {
        // 校验
        Long id = integralExchange.getId();
        validateUpdateByOrganizationId(id,organizationId,integralExchange);
        integralExchange  =addFieldWhenUpdate(integralExchange);
        // TODO 优化写个sql去除这个select
        IntegralExchange result = mapper.selectByPrimaryKey(id);
        if(organizationId.equals(organizationId)){
            int i = mapper.updateByPrimaryKeySelective(integralExchange);
            if(i != 1){
                logger.error("{组织" + organizationId + "标记积分兑换记录" + id + " 共"+i+"条}" );
                throw new SuperCodeException("编辑积分记录失败",500);
            }
        }else {
            logger.error("组织"+organizationId+"发生数据越权,数据id"+ id);
            throw new SuperCodeException("组织" + organizationId + "无法查看" + id +"数据");
        }
    }



    /**
     * 新增兑换
     * @param integralExchange
     */
    public void add(IntegralExchange integralExchange) throws SuperCodeException{
        validateAdd(integralExchange);
        // 根据业务补充数据
        integralExchange = addFeildByBuzWhenAdd(integralExchange);
        int i = mapper.insertSelective(integralExchange);
        if(1 != i){
            throw new SuperCodeException("插入兑换记录失败",500);
        }

    }

    /**
     * 编辑兑换记录的属性转换与添加
     * @param integralExchange
     * @return
     */
    private IntegralExchange addFieldWhenUpdate(IntegralExchange integralExchange) {
        // TODO 编辑兑换记录的属性转换与添加
        return  integralExchange;
    }

    /**
     * 新增兑换时候补充数据
     * @param integralExchange
     * @return
     */
    private IntegralExchange addFeildByBuzWhenAdd(IntegralExchange integralExchange) {
        // TODO 新增兑换时候补充数据

        return integralExchange;
    }


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
