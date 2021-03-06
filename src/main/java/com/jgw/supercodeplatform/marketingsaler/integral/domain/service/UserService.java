package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.UserState;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.UserMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class UserService extends SalerCommonService<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

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
        int update = userMapper.reduceIntegral(exchangeIntegral,userPojo.getId(),userPojo.getOrganizationId());
        Asserts.check(update==1,"减少导购积分失败");
    }



    public void addIntegral(Integer addIntegral,  User userPojo) {
        int update = userMapper.addIntegral(addIntegral,userPojo.getId(),userPojo.getOrganizationId());
        Asserts.check(update==1,"导购积分奖励失败");
    }


    public User exists(H5LoginVO user) {
        User userPojo = null;
        try {
            userPojo = baseMapper.selectOne(query().eq("Id", user.getMemberId()).eq("OrganizationId", user.getOrganizationId()).getWrapper());
        } catch (Exception e) {
            log.error("获取导购用户信息失败{}", JSONObject.toJSONString(user));
            e.printStackTrace();
        }
        return userPojo;
    }
}
