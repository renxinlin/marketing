package com.jgw.supercodeplatform.marketing.pojo.integral;

public class IntegralRuleProduct {
    /** 主键 */
    private Long id;

    /** 积分规则主键 */
    private Integer integralruleid;

    /** 产品id */
    private String productid;

    /** 产品名称|注意基础信息可以发生改变 */
    private String productname;

    /** 产品价格 */
    private Float productprice;

    /** 奖励对象0会员 */
    private Boolean rewardobject;

    /** 0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分） */
    private Boolean rewardrule;

    /** 每消费多少元 */
    private Float perconsume;

    /** 奖励积分 */
    private Integer rewardintegral;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIntegralruleid() {
        return integralruleid;
    }

    public void setIntegralruleid(Integer integralruleid) {
        this.integralruleid = integralruleid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Float getProductprice() {
        return productprice;
    }

    public void setProductprice(Float productprice) {
        this.productprice = productprice;
    }

    public Boolean getRewardobject() {
        return rewardobject;
    }

    public void setRewardobject(Boolean rewardobject) {
        this.rewardobject = rewardobject;
    }

    public Boolean getRewardrule() {
        return rewardrule;
    }

    public void setRewardrule(Boolean rewardrule) {
        this.rewardrule = rewardrule;
    }

    public Float getPerconsume() {
        return perconsume;
    }

    public void setPerconsume(Float perconsume) {
        this.perconsume = perconsume;
    }

    public Integer getRewardintegral() {
        return rewardintegral;
    }

    public void setRewardintegral(Integer rewardintegral) {
        this.rewardintegral = rewardintegral;
    }
}