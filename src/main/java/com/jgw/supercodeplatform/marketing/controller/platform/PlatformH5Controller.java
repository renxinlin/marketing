package com.jgw.supercodeplatform.marketing.controller.platform;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.dto.activity.LotteryOprationDto;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.platform.AbandonPlatform;
import com.jgw.supercodeplatform.marketing.pojo.platform.LotteryPlatform;
import com.jgw.supercodeplatform.marketing.service.LotteryService;
import com.jgw.supercodeplatform.marketing.service.PlatformLotteryService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityChannelService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingPlatformOrganizationService;
import com.jgw.supercodeplatform.marketing.service.activity.PlatformActivityService;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformScanStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/marketing/activity/platform/h5")
@Api(tags = "全平台活动H5页面")
public class PlatformH5Controller {

    @Autowired
    private CommonService commonService;

    @Autowired
    private MarketingMembersService marketingMembersService;

    @Autowired
    private PlatformActivityService platformActivityService;

    @Autowired
    private MarketingActivitySetService marketingActivitySetService;

    @Autowired
    private PlatformLotteryService platformLotteryService;

    @ApiOperation("获取该码是否被扫过<true表示被扫过，false表示没有被扫过>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeId", paramType = "query", value = "码", required = true),
            @ApiImplicitParam(name = "codeTypeId", paramType = "query", value = "码制", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", value = "组织ID", required = true)})
    @GetMapping("/scanStatus")
    public RestResult<PlatformScanStatusVo> getScanStatus(@RequestParam String codeId, @RequestParam String codeTypeId, @RequestParam String organizationId){
        commonService.checkCodeValid(codeId, codeTypeId);
        commonService.checkCodeTypeValid(Long.valueOf(codeTypeId));
        PlatformScanStatusVo platformScanStatusVo = platformActivityService.getScanStatus(codeId, organizationId);
        return RestResult.successWithData(platformScanStatusVo);
    }

    @ApiOperation("放弃抽奖")
    @PostMapping("/abandonLottery")
    public RestResult<?> abandonLottery(@RequestBody @Valid AbandonPlatform abandonPlatform){
        platformActivityService.addAbandonPlatform(abandonPlatform);
        return RestResult.success();
    }

    @ApiOperation("抽奖")
    @PostMapping("/lottery")
    public RestResult<LotteryResultMO> lottery(@RequestBody @Valid LotteryPlatform lotteryPlatform, @ApiIgnore H5LoginVO h5LoginVO, HttpServletRequest request) throws Exception {
        MarketingActivitySet marketingActivitySet = marketingActivitySetService.getOnlyPlatformActivity();
        MarketingMembers memberUser = marketingMembersService.getMemberById(lotteryPlatform.getMemberId());
        ScanCodeInfoMO scanCodeInfoMO = new ScanCodeInfoMO();
        BeanUtils.copyProperties(lotteryPlatform, scanCodeInfoMO);
        BeanUtils.copyProperties(h5LoginVO, scanCodeInfoMO);
        scanCodeInfoMO.setUserId(memberUser.getId());
        scanCodeInfoMO.setActivitySetId(marketingActivitySet.getId());
        scanCodeInfoMO.setCodeTypeId(lotteryPlatform.getCodeType());
        LotteryOprationDto lotteryOprationDto = new LotteryOprationDto();
        //检查抽奖的初始条件是否符合
        lotteryOprationDto = platformLotteryService.checkLotteryCondition(lotteryOprationDto, scanCodeInfoMO);
        RestResult<LotteryResultMO> restResult = lotteryOprationDto.getRestResult();
        if(restResult != null){
            return restResult;
        }
        //营销码判断
        platformLotteryService.holdLockJudgeES(lotteryOprationDto);
        if(lotteryOprationDto.getSuccessLottory() == 0) {
            return lotteryOprationDto.getRestResult();
        }
        //保存抽奖数据
        restResult = platformLotteryService.saveLottory(lotteryOprationDto, request.getRemoteAddr());
        return RestResult.success();
    }

}
