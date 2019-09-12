package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional // todo 单库干掉预减库存
    public void exchange(H5SalerRuleExchangeDto salerRuleExchangeDto, H5LoginVO user) {
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
        double money = calculatorSalerExcgange(salerRuleExchange);

        // 支付流程
        // 预减库存
        SalerRuleExchange updateDo = new SalerRuleExchange();
        // TODO 检查这段是否只是减库存 affectRow
        int update = baseMapper.update(updateDo, H5SalerRuleExchangeTransfer.reducePreStock(updateDo, salerRuleExchange));
        Asserts.check(update == 1,"扣减库存失败");
        // 减导购用户积分
        marketingUserService.reduceIntegral(salerRuleExchange.getExchangeIntegral(),userPojo);

        // 兑换次数
        salerExchangeNumService.save(new SalerExchangeNum(null,userPojo.getId(),userPojo.getOrganizationId(),salerRuleExchange.getId()));
        // 订单[虚拟订单]
        recordService.save(SalerRecordTransfer.buildRecord(salerRuleExchange,user,money));
        // 支付
        try {
            wxPayService.qiyePaySync(userPojo.getOpenid(),serverIp,(int)(money*100), UUID.randomUUID().toString().replaceAll("-",""),user.getOrganizationId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("积分换红包支付失败.........................参数salerRuleExchangeDto{},user{}",salerRuleExchangeDto,user);
            throw new RuntimeException("微信支付，支付失败啦！");
        }
        // TODO 减实际库存 [检查这段是否只是减库存]
        baseMapper.update(updateDo,H5SalerRuleExchangeTransfer.reduceStock(updateDo,salerRuleExchange));


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
        Asserts.check(salerRuleExchange !=null,"兑换不存在");
        Asserts.check(salerRuleExchange.getStatus().intValue() == ExchangeUpDownStatus.up ,"当前兑换已经下架");
        Asserts.check(salerRuleExchange.getPreHaveStock()!=null&& salerRuleExchange.getPreHaveStock()>0,"兑换库存不足");
        // todo 需要保持时钟同步 现象时间最早的节点优先达到后兑换不可用
        if(salerRuleExchange.getUndercarriageSetWay().intValue() == UndercarriageSetWayConstant.timecoming
                && new Date().getTime() > salerRuleExchange.getUnderCarriage().getTime()
        ) {
            throw new RuntimeException("当前兑换正在下架中...");
        }
    }

    public AbstractPageService.PageResults<List<SalerRuleExchange>>  h5PageList(DaoSearchWithOrganizationId daoSearch) {
        IPage<SalerRuleExchange> salerRuleExchangeIPage = baseMapper.selectPage(SalerRuleExchangeTransfer.getPage(daoSearch),
                SalerRuleExchangeTransfer.getH5PageParam(daoSearch.getOrganizationId()));
        return SalerRuleExchangeTransfer.toPageResults(salerRuleExchangeIPage);
    }


}
