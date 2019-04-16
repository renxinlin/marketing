package com.jgw.supercodeplatform.marketing.constants;

/**
 * 定义常用的常量
 */
public class CommonConstants {
    /**
     * 前端header key ,jwt-token存储jwt处理后的JwtUser信息
     */
    public static final String JWT_TOKEN = "jwt-token";


    /**
     * 请求码管理进行码关联过的产品路径
     */
    public static final String RELATION_PRODUCT_URL = "/code/relation/list/relation/productRecord";

    /**
     * 请获取未做活动的非自卖产品以及对应的营销信息
     */
    public static final String UN_SALE_PRODUCT_URL = "/non-self-selling/product/market/no-activity";

    /**
     * 获取未做活动的产品以及对应的营销信息
     */
    public static final String SALE_PRODUCT_URL = "/product/market/no-activity";


    /**
     * 单一产品信息
     */
    public static final String SALE_PRODUCT_DETAIL_URL ="/product/market" ;
    /**
     * 单一产品信息
     */
    public static final String UN_SALE_PRODUCT_DETAIL_URL ="/non-self-selling/product/market" ;
}
