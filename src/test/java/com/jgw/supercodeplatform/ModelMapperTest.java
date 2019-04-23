package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelMapperTest {
    public static void main(String[] args) {
        ModelMapper modelMapper = new ModelMapper();
        IntegralExchangeUpdateParam integralExchange = new IntegralExchangeUpdateParam();
        integralExchange.setId(1L);
        integralExchange.setMemberType((byte) 0);
        integralExchange.setExchangeStock(10);
        integralExchange.setUnderCarriage(new Date());
        List<ProductAddParam> ps = new ArrayList<>();
        ProductAddParam p = new ProductAddParam();
        p.setProductId("11111");
        List<SkuInfo> skuinfos = new ArrayList<>();
        SkuInfo skuinfo = new SkuInfo();
        skuinfo.setSkuName("name");
        skuinfos.add(skuinfo);
        p.setSkuInfo(skuinfos);
        ps.add(p);
        integralExchange.setProducts(ps);
        IntegralExchangeAddParam integralExchangeAddParam = modelMapper.map(integralExchange, IntegralExchangeAddParam.class);
        System.out.println(JSONObject.toJSONString(integralExchangeAddParam));
        // {"exchangeStock":10,"memberType":0,"products":[{"productId":"11111","skuinfos":[{"skuName":"name"}]}],"underCarriage":1555034368243}


    }

    @Test
    public void testModelmapper1() {
        IntegralExchangeSkuDetailAndAddress result = new IntegralExchangeSkuDetailAndAddress();
        IntegralExchange integralExchanges = new IntegralExchange();
        ModelMapper modelMapper = new ModelMapper();
        integralExchanges.setOrganizationId("ssssssssssss");
        integralExchanges.setShowPrice(1.23F);
        integralExchanges.setProductId("s");
        integralExchanges.setProductName("s");
        integralExchanges.setProductPic("s");
        modelMapper.map(integralExchanges, result);
        System.out.println(JSONObject.toJSONString(result));
    }


    @Test
    public void testModelmapper2() {

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
        SkuInfo result = modelMapper.map(integralExchanges, SkuInfo.class);
        System.out.println(JSONObject.toJSONString(result));
    }


    @Test
    public void testModelMapper3() {
        ModelMapper modelMapper = new ModelMapper();
        ProductPageParam pageParam = new ProductPageParam();
        pageParam.setCurrent(10);
        pageParam.setSearch("ss");
        ProductPageFromBaseServiceParam queryCondition = modelMapper.map(pageParam, ProductPageFromBaseServiceParam.class);
    }

    @Test
    public void testModelMapper4() {
        ModelMapper modelMapper = new ModelMapper();
        String s = "{\"state\":200,\"msg\":\"s\"}";
        RestResult map = modelMapper.map(s, RestResult.class);
        Object parse = JSONObject.parse(s);
        RestResult map1 = modelMapper.map(parse, RestResult.class);
        System.out.println(JSONObject.toJSONString(map));
        System.out.println(JSONObject.toJSONString(map1));
        System.out.println(parse);
    }

    @Test
    public void testModelMapper5() {

        String response = "{\"state\":200,\"msg\":\"获取成功\",\"results\":{\"list\":[{\"productId\":\"0a109ee6f2ce490fadf3feeac8a1e584\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"3\",\"producLargeCategory\":\"0d3f9ca0b79e465081c1ee915be200c9\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"09b8e28437c64c9c954b123f411990ee\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"农副产品\",\"producMiddleCategoryName\":\"糖、加工糖及其糖副产品\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000073\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"727ddb2ad0ae4b8989504e33c760017e\",\"serialNumber\":73,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"473bf23bdd3d4cefab997aeafb8214ec\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"12\",\"producLargeCategory\":\"37917e64145845629c77bf801072bc67\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"c2b4cc35ecb54ffdb39f46fd1e80c2c7\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"印刷品、记录媒介复制品、音像制品\",\"producMiddleCategoryName\":\"磁介质复制品\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000072\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"e35812da4cfd4f989b3ab63c4a653355\",\"serialNumber\":72,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"0e66320bd4fe4743a960aefe1d187595\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"产品0415\",\"producLargeCategory\":\"0d3f9ca0b79e465081c1ee915be200c9\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"1cd15cebaa5140bba52645495d8b8704\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"农副产品\",\"producMiddleCategoryName\":\"蔬菜、水果、坚果加工品\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000065\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"ee1ba20b570f4419933d5e8ee1951df6\",\"serialNumber\":65,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"6827dcaf782e4d8598d55b816c5a114e\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"产品--201904150001\",\"producLargeCategory\":\"1b4b400952754d0ea58c24f177c12d13\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"9aa2c3e10e974d989e07278807f183c1\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"农资产品\",\"producMiddleCategoryName\":\"农机具\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000064\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"adf2ce8d8f084556b386084456dbb204\",\"serialNumber\":64,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"21262135526f4957a79c24cd2bc8ccad\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"dz包装层级3\",\"producLargeCategory\":\"020dddec67cf4660a2f44fc524402d21\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"1084fbd3dd834106a7d8f3ce8010ca77\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"建筑材料\",\"producMiddleCategoryName\":\"有机材料\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000039\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"a7e42ede5fc24e99be1690217813e714\",\"serialNumber\":39,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"8b1877394c1d4052806acec899563d6c\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"包装层级34\",\"producLargeCategory\":\"020dddec67cf4660a2f44fc524402d21\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"1084fbd3dd834106a7d8f3ce8010ca77\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"建筑材料\",\"producMiddleCategoryName\":\"有机材料\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000036\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"95f9bbbab174439e9154f3899a561b3b\",\"serialNumber\":36,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":{\"id\":1,\"productMarketId\":\"adasfagadfasdasfafafadgrg\",\"productId\":\"8b1877394c1d4052806acec899563d6c\",\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"price\":1.23,\"viewPrice\":2.22,\"priceUnitCode\":null,\"priceUnitName\":null,\"viewPriceUnitCode\":null,\"viewPriceUnitName\":null,\"createTime\":\"2019-04-15 14:03:31\",\"updateTime\":\"2019-04-15 14:03:31\",\"productDetails\":\"zdfdgafda\",\"productMarketingSkus\":[{\"id\":1,\"productMarketId\":\"adasfagadfasdasfafafadgrg\",\"productId\":\"8b1877394c1d4052806acec899563d6c\",\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"sku\":\"asd\",\"pic\":\"sadasdasdasd\",\"createTime\":\"2019-04-15 14:04:23\",\"updateTime\":\"2019-04-15 14:04:23\"}]}},{\"productId\":\"a7eb116cad13487595803e01abf267fd\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"包装层级3\",\"producLargeCategory\":\"4ec13f51f4eb48b2a1ebd4fdcbed68e4\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"61f868d38b2f4cb383e724bf7f09d40a\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"家具及其配件\",\"producMiddleCategoryName\":\"家具配件\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000035\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"8246438ab1084963894d0dbf9465a1b3\",\"serialNumber\":35,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"a1342c4ad79a4d0bb7ca426bdc6acd3b\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"大枣产品添加测试\",\"producLargeCategory\":\"020dddec67cf4660a2f44fc524402d21\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"1084fbd3dd834106a7d8f3ce8010ca77\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"建筑材料\",\"producMiddleCategoryName\":\"有机材料\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000034\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"a2b8001bbe5448a59bcf02d8cfbec26f\",\"serialNumber\":34,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"65cf8f96ffab4190968a2b2a2cd25905\",\"productSpecificationsCode\":null,\"productSpecificationsName\":null,\"productName\":\"333\",\"producLargeCategory\":\"0d3f9ca0b79e465081c1ee915be200c9\",\"productBarcode\":null,\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"09b8e28437c64c9c954b123f411990ee\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"农副产品\",\"producMiddleCategoryName\":\"糖、加工糖及其糖副产品\",\"producSmallCategoryName\":null,\"productModel\":null,\"productUrl\":null,\"productCode\":\"CP000022\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"6c6a4a5d46264d23a0fa2902e17016b1\",\"serialNumber\":22,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null},{\"productId\":\"28e9405b82214261a67a75fd01d6b989\",\"productSpecificationsCode\":null,\"productSpecificationsName\":\"阿斯顿\",\"productName\":\"阿桑大事大大事\",\"producLargeCategory\":\"1b4b400952754d0ea58c24f177c12d13\",\"productBarcode\":\"asdasd\",\"brandCode\":null,\"brandName\":null,\"producPrice\":null,\"organizationId\":\"86ff1c47b5204e88918cb89bbd739f12\",\"producMiddleCategory\":\"192f1754392a4b21b081511affc060f4\",\"producSmallCategory\":\"\",\"disableFlag\":1,\"disableFlagName\":\"正常\",\"organizationFullName\":\"江苏浮华文化传播有限公司\",\"producLargeCategoryName\":\"农资产品\",\"producMiddleCategoryName\":\"农药\",\"producSmallCategoryName\":null,\"productModel\":\"阿斯顿\",\"productUrl\":\"0d4ba7fddda84f9a9c94359d581b61e9\",\"productCode\":\"CP000019\",\"productSortLink\":\"123\",\"productSortId\":\"642b88bef3474b6293da8c26eb9c539a\",\"qrCode\":\"6c6a4a5d46264d23a0fa2902e17016b1\",\"serialNumber\":19,\"productSortName\":\"123\",\"productWareHouse\":null,\"productMarketing\":null}],\"pagination\":{\"startNumber\":0,\"current\":1,\"totalPage\":2,\"pageSize\":10,\"total\":13}}}";
        ModelMapper modelMapper = new ModelMapper();
        RestResult restResult = JSONObject.parseObject(response, RestResult.class);
        System.out.println("---");

    }


    @Test
    public void testModelMapper6() {
        // 测试发现modelmapper有坑！！！！！！！！！！！！！！！！！！！！！！！！！！
        // 源码追踪后发现
        ModelMapper modelMapper = new ModelMapper();
        // 默认策略是前缀匹配，需要严格匹配
        modelMapper.getConfiguration()
                /**  LOOSE松散策略 */
                .setMatchingStrategy(MatchingStrategies.STRICT);
        IntegralExchangeAddParam integralExchange = new IntegralExchangeAddParam();
        integralExchange.setStockWarningNum(111111);
        IntegralExchange map = modelMapper.map(integralExchange, IntegralExchange.class);

    }


}
