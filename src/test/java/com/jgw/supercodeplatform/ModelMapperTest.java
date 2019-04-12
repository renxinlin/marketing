package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.dto.IntegralExchangeAddParam;
import com.jgw.supercodeplatform.marketing.dto.IntegralExchangeUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.ProductAddParam;
import com.jgw.supercodeplatform.marketing.dto.SkuInfo;
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
}
