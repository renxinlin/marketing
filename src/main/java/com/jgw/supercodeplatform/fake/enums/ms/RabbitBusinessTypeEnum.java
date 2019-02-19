package com.jgw.supercodeplatform.fake.enums.ms;

/**  业务
 * @author Created by jgw136 on 2018/01/27.
 */
public enum RabbitBusinessTypeEnum {
    /**
     * 修改码扩展属性
     */
    CODE_EXTENSION_ATTR("esCodeExtensionInfo"),

    /**
     * 修改es批次数据中的使用账户
     */
    MODIFY_ES_INNERUSERACCOUNT("allBatchTransfer")

    /**
     * 生码和嵌套生成文件并删除缓存
     */
    ,CREATE_CODE_NEST("CreateCodeNestCodeFile")
    
    /**
     * 替换单标es数据替换
     */
    ,STANDA_RDREOLACEMENT("standardReplacement")

    ,PUBLIC_SEND("PublicMsMessage")
    ;

    private String businessBean;

    RabbitBusinessTypeEnum(String businessBean){
        setBusinessBean(businessBean);
    }

    public String getBusinessBean() {
        return businessBean;
    }

    public void setBusinessBean(String businessBean) {
        this.businessBean = businessBean;
    }
}
