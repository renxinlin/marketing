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
    public static final String CODEMANAGER_RELATION_PRODUCT_URL = "/code/relation/list/relation/productRecord";

    
    /**
     * 请求码管理进行码关联过的产品和产品批次
     */
    public static final String CODEMANAGER_RELATION_PRODUCT_PRODUCT_BATCH = "/code/relation/list/product/forRelation";
    
    /**
     * 请求基础平台，通过产品id集合获取批次集合
     */
    public static final String USER_REQUEST_PRODUCT_BATCH = "/product-batch/array/batch";
    
    /**
     * 请求基础平台，通过组织id数据请求组织信息集合
     */
    public static final String USER_REQUEST_ORGANIZATION_BATCH = "/org/ids";
    
    /**
     * 请求基础平台，批量更新产品营销信息接口
     */
    public static final String USER_BATCH_UPDATE_PRODUCT_MARKETING_INFO = "/product/market/synchronize/batch";
    
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
    public static final String SALE_PRODUCT_DETAIL_URL ="/product/market/no-filter" ;
    /**
     * 单一产品信息
     */
    public static final String UN_SALE_PRODUCT_DETAIL_URL ="/non-self-selling/product/market/no-filter" ;

    /**
     * 非登录下基于组织获取组织信息
     */
    public static final String ORGANIZATION_NAME ="/getByOrganizationId"  ;
}
