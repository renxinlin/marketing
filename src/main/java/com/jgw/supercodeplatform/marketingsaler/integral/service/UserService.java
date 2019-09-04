package com.jgw.supercodeplatform.marketingsaler.integral.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.UserState;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.UserMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.User;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@Service
public class UserService extends SalerCommonService<UserMapper, User> {


    public User canExchange(H5LoginVO user, Integer exchangeIntegral) {
        User salerUser = baseMapper.selectById(user.getMemberId());
        Asserts.check(salerUser !=null ,"用户不存在");
        Asserts.check(salerUser.getOrganizationId() !=null  && salerUser.getOrganizationId().equals(user.getOrganizationId()),"用户越权");
        Asserts.check(salerUser.getState() !=null    ,"用户状态不明");
        // 1、 待审核，2 停用3启用
        Asserts.check(salerUser.getState() == UserState.enable    ,"用户未启动");
        Asserts.check(salerUser.getHaveIntegral()!=null ,"积分不足");
        Asserts.check(salerUser.getHaveIntegral().intValue()>= exchangeIntegral.intValue() ,"积分不足");
        return salerUser;

    }

    public void reduceIntegral(Integer exchangeIntegral,  User userPojo) {
        User value = new User();
        Wrapper<User> updateWrapper = new UpdateWrapper<>(value);
        ((UpdateWrapper<User>) updateWrapper).apply("haveIntegral = haveIntegral - {0}",exchangeIntegral).set("Id",userPojo.getId()).set("OrganizationId",userPojo.getOrganizationId());
        int update = baseMapper.update(value, updateWrapper); // 默认是匹配的行数
        Asserts.check(update==1,"减少导购积分失败");
    }
}
