package com.jgw.supercodeplatform.marketing.controller.h5.member.saler;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.SaleInfo;
import com.jgw.supercodeplatform.marketing.dto.integral.ExchangeProductParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralExchangeSkuDetailAndAddress;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 销售员扫码领红包
 */
@RestController
@RequestMapping("/marketing/saleMember/")
@Api(tags = "销售员H5")
public class SaleMemberController {
    @Autowired
    private IntegralRecordService service;
    @Autowired
    private CodeEsService es;
    /**
     * 扫码条件:
     *  1 活动规则
     *    1.1 活动时间【现在到未来】
     *    1.2 活动状态
     *    1.3 活动类型
     *    1.3 中奖概率
     *    1.4 每人每天领取上限【默认200】
     *    1.5 参与条件
     *    1.6 活动产品【码平台】
     *    1.7 自动追加【码平台】
     *
     *  2 被启用
     *
     *  3 属于该码对应组织下的销售员
     *  4 活动码没有被扫过
     *  5 配置了活动规则
     *
     *  6
     *  获取结果=》【中奖金额，随机/固定】
     *  微信公众号相关信息支付配置
     *  支付相关配置信息配置
     *  ====================异步;对接微信处理中奖金额账本=====================
     *  【微信成功处理后如网页中断则通过查询记录看自己的数据/或者微信看自己的数据】
     *  7 返回中奖或没中奖金额
     *
     *
     *  备注:多人同时扫码的并发处理
     */
    @RequestMapping(value = "lottery",method = RequestMethod.POST)
    @ApiOperation(value = "lottery", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<IntegralExchangeSkuDetailAndAddress> exchanging(String wxstate, @ApiIgnore H5LoginVO jwtUser) throws SuperCodeException {
//       return marketingMembersService.lottery(wxstate);
//
        return null;
    }


    @RequestMapping(value = "info")
    @ApiOperation(value = "销售员中心", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult info(@ApiIgnore H5LoginVO jwtUser, DaoSearchWithOrganizationIdParam search) throws Exception {
        if(MemberTypeEnums.SALER.getType().intValue()!=jwtUser.getMemberType()){
            throw new SuperCodeException("会员角色错误...");
        }
        SaleInfo saleInfo = new SaleInfo();



        // 1 获取红包统计信息
        Map acquireMoneyAndAcquireNums = service.getAcquireMoneyAndAcquireNums(jwtUser.getMemberId(), jwtUser.getMemberType(), jwtUser.getOrganizationId());

        // 2 获取红包信息
        // 分页信息传递
        IntegralRecord params = new IntegralRecord();
        params.setMemberType(MemberTypeEnums.SALER.getType());
        // 一个导购只能是一个组织
        params.setOrganizationId(jwtUser.getOrganizationId());
        params.setSalerId(jwtUser.getMemberId());
        params.setStartNumber(search.getStartNumber());
        params.setPageSize(search.getPageSize());
        // 查询
        AbstractPageService.PageResults<IntegralRecord> objectPageResults = service.listSearchViewLike(params);

        // 3 获取扫码信息
        Integer scanNum = es.searchScanInfoNum(jwtUser.getMemberId(), jwtUser.getMemberType());
        // 4 数据转换
        saleInfo.setScanQRCodeNum(scanNum);
        saleInfo.setScanAmoutNum((Integer) acquireMoneyAndAcquireNums.get("count"));
        saleInfo.setAmoutNum((Float) acquireMoneyAndAcquireNums.get("sum"));
        saleInfo.setAmoutNumStr(saleInfo.getAmoutNum()+"");
        // TOFO page转前端格式
        saleInfo.setPageInfo(objectPageResults);
        return RestResult.success("success",saleInfo);
    }
}
