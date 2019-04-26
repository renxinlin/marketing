package com.jgw.supercodeplatform.marketing.service.integral;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.dao.integral.DeliveryAddressMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 积分兑换地址管理
 */
@Service
public class DeliveryAddressService {
    private Logger logger = LoggerFactory.getLogger(DeliveryAddressService.class);
    /**
     * 前端组件传递时携带的areaCode key
     */
    private static final String areaCode="areaCode";
    /**
     * 前端组件传递时携带的areaName key
     */
    private static final String areaName="areaName";


    @Autowired
    private DeliveryAddressMapperExt mapper;

    /**
     * 查询用户5个地址
     * @return
     */
    public List<DeliveryAddress> selectByUser(Long memberId) {
        List<DeliveryAddress> deliveryAddresses = mapper.selectByMemberId(memberId);
        return deliveryAddresses;
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    public DeliveryAddress selectById(Long id, Long memberId) throws SuperCodeException {
        DeliveryAddress deliveryAddress = mapper.selectByPrimaryKey(id);
        if(deliveryAddress == null){
            throw new SuperCodeException("无地址信息");
        }
        if(memberId.intValue() !=deliveryAddress.getMemberId().intValue()){
             throw new SuperCodeException("非当前用户地址");
        }

        return deliveryAddress;
    }

    /**
     * 新增地址不超过5个
     * @param deliveryAddress
     * @return
     */
    public DeliveryAddress add(DeliveryAddress deliveryAddress) throws SuperCodeException{
        // 先基础校验，后业务校验
        validateBasicWithAdd(deliveryAddress);
        validateBizWithAdd(deliveryAddress);
        // 补充相关数据
        deliveryAddress = changeDtoToDo(deliveryAddress);
        Byte defaultUsing = deliveryAddress.getDefaultUsing();
        if(defaultUsing == 0 ){
            // 去除原来的默认
            DeliveryAddress oldDefaultDeliveryAddress = mapper.havingUsing(deliveryAddress.getMemberId());
            if(oldDefaultDeliveryAddress != null){
                // 由默认变成非默认
                oldDefaultDeliveryAddress.setDefaultUsing((byte)1);
                mapper.updateByPrimaryKeySelective(oldDefaultDeliveryAddress);
            }

        }
        int i = mapper.insertSelective(deliveryAddress);
        if(i != 1){
            throw new SuperCodeException("保存地址信息失败");

        }
        return deliveryAddress;
    }
    /**
     * 更新地址信息
     * @param deliveryAddress
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(DeliveryAddress deliveryAddress) throws SuperCodeException {
        //  优化成一条查询语句
        if(deliveryAddress == null ){
            throw  new SuperCodeException("地址信息不可为空");
        }
        if(deliveryAddress.getId() == null){
            throw  new SuperCodeException("无法查找地址");

        }
        if(deliveryAddress.getMemberId() == null){
            throw  new SuperCodeException("用户不存在，请尝试重新登录");

        }
        DeliveryAddress deliveryAddressByDb = mapper.selectByPrimaryKey(deliveryAddress.getId());
        if(deliveryAddressByDb.getMemberId().intValue() != deliveryAddress.getMemberId().intValue()){
            throw new SuperCodeException("非当前用户收货地址");

        }
        // 判断是不是需要设置成默认地址

        if(deliveryAddress.getDefaultUsing() == 0){
            // 默认地址只有一个
            DeliveryAddress havingUsing= mapper.havingUsing(deliveryAddress.getMemberId());
            if(havingUsing != null && havingUsing.getId().intValue() != deliveryAddress.getId().intValue()){
                // 如果存在默认地址，则将原来的默认地址设置成非默认
                havingUsing.setDefaultUsing((byte)1);
                mapper.updateByPrimaryKeySelective(havingUsing);
            }

        }
        int i =  mapper.updateByPrimaryKeySelective(changeDtoToDo(deliveryAddress));
        if(i != 1){
            throw new SuperCodeException("未成功更新收货地址");
        }
        return i;
    }

    /**
     * 删除地址信息
     * @param id
     * @return
     */
    public int delete(Long id, Long memberid) throws SuperCodeException{
        if(memberid == null || memberid.intValue() <= 0){
            throw new SuperCodeException("会员不存在");

        }
        DeliveryAddress deliveryAddress = mapper.selectByPrimaryKey(id);
        if(deliveryAddress == null){
            throw new SuperCodeException("收货地址不存在");
        }

        if(memberid.intValue() != deliveryAddress.getMemberId().intValue()){
            throw new SuperCodeException("非当前用户收货地址");
        }
        int i = mapper.deleteByPrimaryKey(id);
        if(i != 1){
            throw new SuperCodeException("未成功删除收货地址");
        }
        return i;
    }
    /**
     * 入库数据转换与补充
     * @param deliveryAddress
     * @return
     * @throws SuperCodeException
     */
    private DeliveryAddress changeDtoToDo(DeliveryAddress deliveryAddress) throws SuperCodeException{
        // 新增设置为非默认地址
        // 使用习惯上，新增的地址无需为默认地址
        if(deliveryAddress.getDefaultUsing() == null){
            deliveryAddress.setDefaultUsing((byte)1);
        }




        // 补充省市区名称
        String pcccode = deliveryAddress.getPcccode();
        List<JSONObject> objects = JSONObject.parseArray(pcccode,JSONObject.class);
        JSONObject province = objects.get(0);
        JSONObject city = objects.get(1);
        JSONObject country = objects.get(2);
        // 省市区编码
        deliveryAddress.setProvinceCode(province.getString(areaCode));
        deliveryAddress.setCityCode(city.getString(areaCode));
        deliveryAddress.setCountryCode(country.getString(areaCode));
        // 省市区
        deliveryAddress.setProvince(province.getString(areaName));
        deliveryAddress.setCity(city.getString(areaName));
        deliveryAddress.setCountry(country.getString(areaName));

        // 目前街道没有编码，系统不支持4级编码信息

//        areaCodes.add(deliveryAddress.getProvinceCode());
//        areaCodes.add(deliveryAddress.getCityCode());
//        areaCodes.add(deliveryAddress.getCountryCode());
//        areaCodes.add(deliveryAddress.getStreetCode());
//        List<MarketingAdministrativeCode> codesNames = adminstrativeCodeMapper.getCodesName(areaCodes);
//        if(CollectionUtils.isEmpty(codesNames) || codesNames.size() != 4){
//            logger.error("查询行政编码异常:"+ JSONObject.toJSONString( deliveryAddress) +"结果" + JSONObject.toJSONString(codesNames));
//            throw new SuperCodeException("系统行政信息异常");
//        }
//        for (MarketingAdministrativeCode codesName: codesNames){
//            if(deliveryAddress.getProvinceCode().equals(codesName.getAreaCode())){
//                deliveryAddress.setProvince(codesName.getCityName());
//            }
//            if(deliveryAddress.getCityCode().equals(codesName.getAreaCode())){
//                deliveryAddress.setCity(codesName.getCityName());
//            }
//            if(deliveryAddress.getCountryCode().equals(codesName.getAreaCode())){
//                deliveryAddress.setCountryCode(codesName.getCityName());
//            }
//            if(deliveryAddress.getStreetCode().equals(codesName.getAreaCode())){
//                deliveryAddress.setStreet(codesName.getCityName());
//            }
//        }
        return deliveryAddress;
    }

    /**
     * 新增地址业务校验，不超过5个
     * @param deliveryAddress
     * @throws SuperCodeException
     */
    private void validateBizWithAdd(DeliveryAddress deliveryAddress) throws SuperCodeException{
        List<DeliveryAddress> deliveryAddresses =  mapper.selectByMemberId(deliveryAddress.getMemberId());
        if(!CollectionUtils.isEmpty(deliveryAddresses) && deliveryAddresses.size() >= 5){
            throw new SuperCodeException("地址数量超过上限，请先删除");
        }
        // 从使用经验上看，新增地址无需设置为默认地址


    }

    /**
     * 新增地址基础合法性校验
     * @param deliveryAddress
     * @throws SuperCodeException
     */
    private void validateBasicWithAdd(DeliveryAddress deliveryAddress) throws SuperCodeException{
        if(deliveryAddress == null){
            throw new SuperCodeException("数据不可为空");

        }
        if(deliveryAddress.getMemberId() == null || deliveryAddress.getMemberId() <= 0){
            throw new SuperCodeException("获取会员信息失败");
        }

//        if(StringUtils.isBlank(deliveryAddress.getProvinceCode())){
//            throw new SuperCodeException("获取省编码失败");
//        }
//
//
//        if(StringUtils.isBlank(deliveryAddress.getCityCode())){
//            throw new SuperCodeException("获取市编码失败");
//        }
//
//
//        if(StringUtils.isBlank(deliveryAddress.getCountryCode())){
//            throw new SuperCodeException("获取区县编码失败");
//        }
//
//
        if(StringUtils.isBlank(deliveryAddress.getStreet())){
            deliveryAddress.setStreet("");
        }

        if(StringUtils.isBlank(deliveryAddress.getPcccode())){
            throw new SuperCodeException("获取省市区信息失败");
        }

        if(StringUtils.isBlank(deliveryAddress.getDetail())){
            throw new SuperCodeException("获取详细地址信息失败");
        }


        if(StringUtils.isBlank(deliveryAddress.getMobile())){
            throw new SuperCodeException("获取手机失败");
        }
        if(StringUtils.isBlank(deliveryAddress.getName())){
            throw new SuperCodeException("收货人必填");

        }


    }


}
