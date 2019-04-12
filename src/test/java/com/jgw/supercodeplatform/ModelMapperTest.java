package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelMapperTest {
    public static void main(String[] args) {
        ModelMapper modelMapper = new ModelMapper();
        IntegralExchangeUpdateParam integralExchange = new IntegralExchangeUpdateParam();
        integralExchange.setId(1L);
        integralExchange.setMemberType((byte)0);
        integralExchange.setExchangeStock(10);
        integralExchange.setUnderCarriage(new Date());
        List<ProductAddParam> ps = new ArrayList<>();
        ProductAddParam p = new ProductAddParam();
        p.setProductId("11111");
        List<SkuInfo> skuinfos = new ArrayList<>();
        SkuInfo skuinfo = new SkuInfo();
        skuinfo.setSkuName("name");
        skuinfos.add(skuinfo);
        p.setSkuinfos(skuinfos);
        ps.add(p);
        integralExchange.setProducts(ps);
        IntegralExchangeAddParam integralExchangeAddParam = modelMapper.map(integralExchange, IntegralExchangeAddParam.class);
        System.out.println(JSONObject.toJSONString(integralExchangeAddParam));
        // {"exchangeStock":10,"memberType":0,"products":[{"productId":"11111","skuinfos":[{"skuName":"name"}]}],"underCarriage":1555034368243}




    }
    @Test
    public void testModelmapper1(){
        IntegralExchangeSkuDetailAndAddress result = new IntegralExchangeSkuDetailAndAddress();
        IntegralExchange integralExchanges = new IntegralExchange();
        ModelMapper modelMapper = new ModelMapper();
        integralExchanges.setOrganizationId("ssssssssssss");
        integralExchanges.setShowPrice(1.23F);
        integralExchanges.setProductId("s");
        integralExchanges.setProductName("s");
        integralExchanges.setProductPic("s");
        modelMapper.map(integralExchanges,result);
        System.out.println(JSONObject.toJSONString(result));
    }


    @Test
    public void testModelmapper2(){

        IntegralExchange integralExchanges = new IntegralExchange();
        ModelMapper modelMapper = new ModelMapper();
        integralExchanges.setOrganizationId("ssssssssssss");
        integralExchanges.setShowPrice(1.23F);
        integralExchanges.setProductId("s");
        integralExchanges.setProductName("s");
        integralExchanges.setProductPic("s");
        integralExchanges.setHaveStock(1);
        integralExchanges.setSkuName("sku");
        integralExchanges.setSkuUrl("url");
        SkuInfo result= modelMapper.map(integralExchanges,SkuInfo.class);
        System.out.println(JSONObject.toJSONString(result));
    }
}
