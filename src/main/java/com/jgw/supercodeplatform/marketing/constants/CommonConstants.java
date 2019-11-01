package com.jgw.supercodeplatform.marketing.constants;

/**
 * 定义常用的常量
 */
public class CommonConstants {
    /**
     * 前端header key ,jwt-token存储jwt处理后的JwtUser信息
     */
    public static final String JWT_TOKEN = "jwt-token";
    
    /** 日期格式转换 */
    public static final String[] DATE_PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd"};
    
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
    
    /** 查询码的客户信息 */
    public static final String OUTERCODE_CUSTOMER = "/logistics/purchasesale/customer";
    
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
    
    /**
     * 窜货
     */
    public static final String JUDGE_FLEE_GOOD = "/goods/query/judgeFleeGood";

    /**
     * 查询指定时间内生码数量
     */
    public static final String CODE_GETCODETOTAL = "/code/getCodeTotal";

    /**
     * 查询码信息，包含内码
     */
    public static final String OUTER_INFO_ONE = "/outer/info/one";

    /**
     * 查询产品信息，包含内码
     */
    public static final String PRODUCT_ONE ="/product/one";

    /**
     *查询一级经销商
     */
    public static final String CUSTOMER_ENABLE_PAGE_LIST = "/customer/enable/two/page/list";

    /**
     * 查询子经销商
     */
    public static final String CUSTOMER_ENABLE_CHILD_PAGE_LIST = "/customer/enable/child/page/list";

    /**
     * 通过码和码制查询其他码
     */
    public static final String OUTER_LIST = "/outer/list";

}
