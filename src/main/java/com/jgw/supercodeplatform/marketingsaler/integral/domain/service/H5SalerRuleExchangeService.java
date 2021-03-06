package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.ExchangeUpDownStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.UndercarriageSetWayConstant;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.DaoSearchWithOrganizationId;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.H5SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.SalerRuleExchangeMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerExchangeNum;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.H5SalerRuleExchangeTransfer;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.SalerRecordTransfer;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.SalerRuleExchangeTransfer;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.jgw.supercodeplatform.marketingsaler.integral.infrastructure.MoneyCalculator.*;

@Service
@Slf4j
public class H5SalerRuleExchangeService  extends SalerCommonService<SalerRuleExchangeMapper, SalerRuleExchange> {
    @Autowired
    private AutoUndercarriageService autoUndercarriageService;

    @Autowired
    private  UserService marketingUserService;

    @Autowired
    private  SalerExchangeNumService salerExchangeNumService;

    @Value("${marketing.server.ip}")
    private String serverIp;

    @Autowired
    private  SalerRecordService recordService;
    @Autowired
    private WXPayService wxPayService;


    @Autowired
    private SalerRuleExchangeMapper salerRuleExchangeMapper;



    @Transactional // todo 单库干掉预减库存
    public RestResult exchange(H5SalerRuleExchangeDto salerRuleExchangeDto, H5LoginVO user) {
        log.info("销售员积分兑换红包入参 salerRuleExchangeDto{}",salerRuleExchangeDto);
        log.info("扫码入参 H5LoginVO{}",JSONObject.toJSONString(user));
        // 校验
        Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()),"用户未注册到相关组织");
        SalerRuleExchange salerRuleExchange = baseMapper.selectOne(query().eq("Id", salerRuleExchangeDto.getId()).getWrapper());
        Asserts.check(salerRuleExchange !=null,"兑换活动不存在");
        Asserts.check(salerRuleExchange.getOrganizationId().equals(user.getOrganizationId()),"非当前组织兑换活动");
        // 自动下架
        autoUndercarriage(salerRuleExchange);
        // 检测兑换是否可以执行
        checkExchangeStatus(salerRuleExchange);
        // 检测用户域数据是否可以执行
        User userPojo = marketingUserService.canExchange(user, salerRuleExchange.getExchangeIntegral());

        // 检测兑换上限
        salerExchangeNumService.canExchange(userPojo,salerRuleExchange);
        // 根据抽奖概率计算金额
        double money = 0D;
        try {
            money = calculatorSalerExcgange(salerRuleExchange);
        } catch (Exception e) {
            marketingUserService.reduceIntegral(salerRuleExchange.getExchangeIntegral(),userPojo);
            // 兑换次数
            salerExchangeNumService.save(new SalerExchangeNum(null,userPojo.getId(),userPojo.getOrganizationId(),salerRuleExchange.getId()));
            // 积分消耗 获取0元
            recordService.save(SalerRecordTransfer.buildRecord(salerRuleExchange,user,money));
            e.printStackTrace();
            return RestResult.error(e.getMessage(),e.getMessage());
        }
        log.info("销售员兑换红包 => money{} USER{} salerRuleExchangeDto{}",money,userPojo,salerRuleExchange);

        if(money != 0D){
           // 支付流程
           // 预减库存
           int update = salerRuleExchangeMapper.updateReduceHaveStock(salerRuleExchange);
           Asserts.check(update==1,"扣减预库存失败");
           // 减导购用户积分
           marketingUserService.reduceIntegral(salerRuleExchange.getExchangeIntegral(),userPojo);
           // 兑换次数
           salerExchangeNumService.save(new SalerExchangeNum(null,userPojo.getId(),userPojo.getOrganizationId(),salerRuleExchange.getId()));
           // 订单[虚拟订单]
           recordService.save(SalerRecordTransfer.buildRecord(salerRuleExchange,user,money));
           // 支付
           try {
               wxPayService.qiyePaySyncWithResend(user.getOpenid(), CallBackConstant.serverIp,(int)(money*100), UUID.randomUUID().toString().replaceAll("-",""),user.getOrganizationId(),1);
           } catch (Exception e) {
               e.printStackTrace();
               log.error("积分换红包支付失败.........................参数salerRuleExchangeDto{},user{}",salerRuleExchangeDto,JSONObject.toJSONString(user));
               throw new BizRuntimeException("微信支付，支付失败啦！");
           }
           int i = salerRuleExchangeMapper.reduceHaveStock(salerRuleExchange);
           Asserts.check(i==1,"扣减库存失败");

       }
       log.info("中奖金额" +  money);
        String moneyStr = String.format("%.2f", money);
        log.info("中奖金额moneyStr" +  moneyStr);
       return RestResult.success( moneyStr,moneyStr);

    }


    /**
     * 自动下架
     * @param salerRuleExchange
     */
    private void autoUndercarriage(SalerRuleExchange salerRuleExchange) {
        if(salerRuleExchange.getUndercarriageSetWay().intValue() == UndercarriageSetWayConstant.zerostork
                && (salerRuleExchange.getHaveStock() != null && salerRuleExchange.getHaveStock()== 0)
        ){
            autoUndercarriageService.listenAutoUnder(H5SalerRuleExchangeTransfer.buildAutoUndercarriageEvent(salerRuleExchange.getId()));
        }
        if(salerRuleExchange.getUndercarriageSetWay().intValue() == UndercarriageSetWayConstant.timecoming
                && new Date().getTime() > salerRuleExchange.getUnderCarriage().getTime()
        ) {
            autoUndercarriageService.listenAutoUnder(H5SalerRuleExchangeTransfer.buildAutoUndercarriageEvent(salerRuleExchange.getId()));
        }
    }

    /**
     * 检查兑换状态
     * @param salerRuleExchange
     */
    private void checkExchangeStatus(SalerRuleExchange salerRuleExchange) {
        log.info("checkExchangeStatus参数{}",salerRuleExchange);
        Asserts.check(salerRuleExchange !=null,"兑换不存在");
        Asserts.check(salerRuleExchange.getStatus().intValue() == ExchangeUpDownStatus.up ,"当前兑换已经下架");
        Asserts.check(salerRuleExchange.getPreHaveStock()!=null&& salerRuleExchange.getPreHaveStock()>0,"兑换库存不足");
        // todo 需要保持时钟同步 现象时间最早的节点优先达到后兑换不可用
        if(salerRuleExchange.getUndercarriageSetWay().intValue() == UndercarriageSetWayConstant.timecoming
                && new Date().getTime() > salerRuleExchange.getUnderCarriage().getTime()
        ) {
            throw new BizRuntimeException("当前兑换正在下架中...");
        }
    }

    public AbstractPageService.PageResults<List<SalerRuleExchange>>  h5PageList(DaoSearchWithOrganizationId daoSearch) {
        IPage<SalerRuleExchange> salerRuleExchangeIPage = baseMapper.selectPage(SalerRuleExchangeTransfer.getPage(daoSearch),
                SalerRuleExchangeTransfer.getH5PageParam(daoSearch.getOrganizationId()));
        return SalerRuleExchangeTransfer.toPageResults(salerRuleExchangeIPage);
    }


}
