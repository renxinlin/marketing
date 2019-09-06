package com.jgw.supercodeplatform.marketing.controller.h5.activity;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.dto.activity.LotteryOprationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingDeliveryAddressParam;
import com.jgw.supercodeplatform.marketing.exception.LotteryException;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import com.jgw.supercodeplatform.marketing.service.LotteryService;
import com.jgw.supercodeplatform.marketing.service.SalerLotteryService;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralOrderExcelService;
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

    @Autowired
    private CommonService commonService;

    @Autowired
    private SalerLotteryService  salerLotteryService;
    
    @Autowired
    private IntegralOrderExcelService integralOrderExcelService;

    @Autowired
    private GlobalRamCache globalRamCache;

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
        ScanCodeInfoMO scanCodeInfoMO = globalRamCache.getScanCodeInfoMO(wxstate);
        LotteryOprationDto lotteryOprationDto = new LotteryOprationDto();
        //检查抽奖的初始条件是否符合
        service.checkLotteryCondition(lotteryOprationDto, scanCodeInfoMO);
        RestResult<LotteryResultMO> restResult = lotteryOprationDto.getRestResult();
        if(restResult != null){
            return restResult;
        }
        //营销码判断
        service.holdLockJudgeES(lotteryOprationDto);
        if(lotteryOprationDto.getSuccessLottory() == 0) {
            return lotteryOprationDto.getRestResult();
        }
        //抽奖
        service.drawLottery(lotteryOprationDto);
        //保存抽奖数据
        restResult = service.saveLottory(lotteryOprationDto, request.getRemoteAddr());
        return restResult;
    }
    
    
    @GetMapping("/previewLottery")
    @ApiOperation(value = "活动预览领奖方法", notes = "")
    public RestResult<LotteryResultMO> previewLottery(String uuid) throws Exception {
        return service.previewLottery(uuid, request);
    }

    /**
     * 除去码是否贝扫是共享数据,其他都是用户数据
     * 码存在扫码【成功】 领奖时候保存码【领奖成功】
     * @param wxstate
     * @param jwtUser
     * @return
     * @throws SuperCodeException
     * @throws ParseException
     */
    @GetMapping("/salerLottery")
    @ApiOperation(value = "导购领奖方法", notes = "导购活动领取")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<LotteryResultMO> salerLottery( String codeId,Long codeTypeId ,String wxstate, @ApiIgnore H5LoginVO jwtUser, HttpServletRequest request) throws Exception {
        // 是不是营销码制，不是不可通过
        if(codeTypeId == null|| codeTypeId != 12){
            throw new SuperCodeException("非营销码...");
        }
        commonService.checkCodeTypeValid(codeTypeId);
        commonService.checkCodeValid(codeId,codeTypeId+"");
        LotteryResultMO lotteryResultMO = salerLotteryService.salerlottery(wxstate,jwtUser,request);
        return RestResult.successWithData(lotteryResultMO);
    }
    
    
    @PostMapping("/addPrizeOrder")
    @ApiOperation(value = "中奖奖品添加收货", notes = "中奖奖品添加收货")
    @ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")
    public RestResult<String> addPrizeOrder(@RequestBody MarketingDeliveryAddressParam marketingDeliveryAddressParam, @ApiIgnore H5LoginVO jwtUser){
    	IntegralOrder integralOrder = new IntegralOrder();
    	integralOrder.setMemberId(jwtUser.getMemberId());
    	integralOrder.setMemberName(jwtUser.getMemberName());
    	integralOrder.setOrderId(jwtUser.getOrganizationId());
    	integralOrder.setOrganizationId(jwtUser.getOrganizationId());
    	integralOrder.setOrganizationName(jwtUser.getOrganizationName());
    	integralOrderExcelService.addPrizeOrder(marketingDeliveryAddressParam, integralOrder);
    	return RestResult.success();
    }
   
}
