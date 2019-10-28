package com.jgw.supercodeplatform.burypoint.prizewheels.constants;

/**
 * @author fangshiping
 * @date 2019/10/22 9:27
 */
public interface RedisKeyConstants {
    /**
     * pv、uv常量
    */
    String PV_DAY="storm:marketing_prizewheels_bury_point_page_view_tc:pv:day";
    String PV_ALL="storm:marketing_prizewheels_bury_point_page_view_tc:pv:all";
    String UV_DAY="storm:marketing_prizewheels_bury_point_page_view_tc:uv:day";
    String UV_ALL="storm:marketing_prizewheels_bury_point_page_view_tc:uv:all";
    /**
     * outerchain 外链
     */
    String OUTER_CONFIG="storm:marketing_prizewheels_bury_point_outer_chain_tb:outUrl:config:num";
    String OUTER_CLICK_ALL="storm:marketing_prizewheels_bury_point_outer_chain_tc:click:all";
    String OUTER_CLICK_DAY="storm:marketing_prizewheels_bury_point_outer_chain_tc:click:day";
    /**
     * reward 奖励发放
     */
    String REWARD_OUT_ALL="storm:marketing_prizewheels_bury_point_reward_tbc:out:all";
    String REWARD_OUT_RewardId_ALL="storm:marketing_prizewheels_bury_point_reward_tbc:out:rewardId:all";
    String REWARD_OUT_DAY="storm:marketing_prizewheels_bury_point_reward_tbc:out:day";
    /**
     * template 模板
     */
    String TEMPLATE_CONFIG_ALL_TOP_TEN="storm:marketing_prizewheels_bury_point_template_tb:organization:all:topten";
    String TEMPLATE_CONFIG_MONTH_TOP_TEN="storm:marketing_prizewheels_bury_point_template_tb:organization:month:topten";
    String TEMPLATE_SCAN_ALL="storm:marketing_prizewheels_bury_point_template_tc:scan:all";
    String TEMPLATE_SCAN_DAY="storm:marketing_prizewheels_bury_point_template_tc:scan:day";
    String TEMPLATE_SCAN_HOUR="storm:marketing_prizewheels_bury_point_template_tc:scan:hour";
    /**
     * wheels click大转盘点击
     */
    String WHEELS_CLICK_ALL="storm:marketing_prizewheels_bury_point_wheels_click_tc:click:all";
    String WHEELS_CLICK_ID_ALL="storm:marketing_prizewheels_bury_point_wheels_click_tc:click:all";
    String WHEELS_CLICK_DAY="storm:marketing_prizewheels_bury_point_wheels_click_tc:click:day";
    /**
     * wx 微信关注
     */
    String WX_CONFIG_ALL="storm:marketing_prizewheels_bury_point_wx_merchants_tb:config:all";
    String WX_FOLLOW_ALL="storm:marketing_prizewheels_bury_point_wx_merchants_tc:follow:all";
    String WX_FOLLOW_DAY="storm:marketing_prizewheels_bury_point_wx_merchants_tc:follow:day";
}
