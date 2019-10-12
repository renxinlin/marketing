package com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author liujianqiang
 * @Date 2019/6/11
 * @Description
 **/
@Data
public class EsRelationcode extends CommonPage {

    private String esId;//es数据的唯一id
    private String outerCodeId;//外码id
    private String relationId;//关联id
    private String sysId;//系统id
    private String sysManager;//系统名称
    private String relationType;//关联方式 1、按照号码段 2、单码 3、生码批次号
    private String isRelation;//是否关联 0 重置码关联 1 表示关联了,2  表示清空码关联
    private String singleCode;//单码
    private String globalBatchId;//生码批次id ============================================== sbatchId
    private Integer localCodeBatch;//当前组织的第几次生码
    private String startCode;//开始码
    private String endCode;//结束码
    private Date createDate;//创建时间
    private Date updateDate;//修改时间
    private String createUserId;//创建人id
    private String createUserName;//创建人名称
    private String organizationId;//组织id
    private String organizationFullName;//组织名称
    private String codeTypeId;//码制
    private String productId;//产品id
    private String productName;//产品名称
    private String productBatchId;//产品批次id
    private String productBatchName;//产品批次名称
    private Long codeTotal;//关联数量
    private Long realGenerateTotal;//真实关联数量
    private List<String> productBatchIdList;//产品批次id列表
    private String productNameSearch;//产品名称搜索
    private List<String> mustNotProductIdList;

}
