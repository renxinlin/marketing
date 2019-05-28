package com.jgw.supercodeplatform.marketing.controller.h5.activity;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.service.LotteryService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/marketing/front/lottery")
@Api(tags = "h5用户领奖方法")
public class LotteryController extends CommonUtil {
	private static Logger logger = LoggerFactory.getLogger(LotteryController.class);
	@Autowired
	private LotteryService service;
	
	@Value("${cookie.domain}")
	private String cookieDomain;
	/**
	 * 用户服务地址
	 */
	@Value("${rest.user.url}")
	private String USER_SERVICE;

    @GetMapping("/lottery")
    @ApiOperation(value = "用户点击领奖方法", notes = "")
    public RestResult<LotteryResultMO> lottery(String wxstate) throws Exception {
        return service.lottery(wxstate, request.getRemoteAddr());
    }
    
    
    @GetMapping("/previewLottery")
    @ApiOperation(value = "活动预览领奖方法", notes = "")
    public RestResult<LotteryResultMO> previewLottery(String uuid) throws Exception {
        return service.previewLottery(uuid, request);
    }
    /**
     * 扫码条件:
     *  1 活动规则
     *    1.1 活动时间【现在到未来】
     *    1.2 活动状态
     *    1.3 活动类型
     *    1.3 中奖概率
     *    1.4 每人每天领取上限【默认200】
     *    1.5 参与条件
     *    1.6 活动产品【码平台】 消息中心处理
     *    1.7 自动追加【码平台】 消息中心处理
     *
     *  2 被启用
     *
     *  3 属于该码对应组织下的销售员【不可以跨组织】
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
    @GetMapping("/salerLottery")
    @ApiOperation(value = "导购领奖方法", notes = "导购活动领取")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<String> salerLottery(String wxstate, @ApiIgnore H5LoginVO jwtUser) throws SuperCodeException, ParseException {
        return service.baselottery(wxstate);
    }
    
   
}
