package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel("导购员")
public class MarketingSaleUserParam {

    /** 序号 */
    private Long id;
    /** 手机 */
    private String mobile;

    /** 用户姓名 */
    private String userName;

    /** 门店类型 */
    @ApiModelProperty("机构类型1总部2子公司3经销商4门店5库房10子门店15地方政府16公司20销售公司25农场31其他")
    private String mechanismType;

    /** 门店名称 */
    @ApiModelProperty("机构1,机构2，机构3")
    private String customerName;

    /** 门店编码 */
    @ApiModelProperty("机构id1,机构id2，机构id3")

    private String customerId;

    /** 累计积分*/
    @ApiModelProperty("累计积分")
    private Integer totalIntegral;

    /** 可用积分*/
    @ApiModelProperty("可用积分")
    private Integer haveIntegral;


    /** 省市区前端编码 */
    private String address;

    /** 建立日期 */
    private Date createDate;

    @ApiModelProperty("用户状态(1、 待审核，2 停用3启用)导购员状态")
    /** 用户状态(1、 表示正常，0 表示下线)导购员状态 */
    private Byte state;
    private Long randomId; // 控制前端列表哪个全选打钩样式

    private Integer version;

    @ApiModelProperty("来源3、H5 4、系统后台")
    private Byte source;


}
