package com.jgw.supercodeplatform.marketing.controller.platform;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.dto.activity.LotteryOprationDto;
import com.jgw.supercodeplatform.marketing.dto.platform.ProductInfoDto;
import com.jgw.supercodeplatform.marketing.dto.platform.SourceLinkBuryPoint;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.pojo.platform.AbandonPlatform;
import com.jgw.supercodeplatform.marketing.pojo.platform.LotteryPlatform;
import com.jgw.supercodeplatform.marketing.service.LotteryService;
import com.jgw.supercodeplatform.marketing.service.PlatformLotteryService;
import com.jgw.supercodeplatform.marketing.service.activity.*;
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

    @Autowired
    private MarketingSourcelinkBuryService marketingSourcelinkBuryService;
    @Autowired
    private GlobalRamCache globalRamCache;

    @ApiOperation("获取该码是否被扫过<true表示被扫过，false表示没有被扫过>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeId", paramType = "query", value = "码", required = true),
            @ApiImplicitParam(name = "codeTypeId", paramType = "query", value = "码制", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", value = "组织ID", required = true)})
    @GetMapping("/scanStatus")
    public RestResult<PlatformScanStatusVo> getScanStatus(@RequestParam String codeId, @RequestParam String codeTypeId, @RequestParam String organizationId){
        commonService.checkCodeMarketFakeValid(Long.valueOf(codeTypeId));
        commonService.checkCodeValid(codeId, codeTypeId);
        String innerCode = commonService.getInnerCode(codeId, codeTypeId);
        PlatformScanStatusVo platformScanStatusVo = platformActivityService.getScanStatus(innerCode, organizationId);
        return RestResult.successWithData(platformScanStatusVo);
    }

    @ApiOperation("放弃抽奖")
    @PostMapping("/abandonLottery")
    public RestResult<?> abandonLottery(@RequestBody @Valid AbandonPlatform abandonPlatform){
        String codeId = abandonPlatform.getCodeId();
        String codeTypeId = abandonPlatform.getCodeType();
        commonService.checkCodeMarketFakeValid(Long.valueOf(codeTypeId));
        commonService.checkCodeValid(codeId, codeTypeId);
        String innerCode = commonService.getInnerCode(codeId, codeTypeId);
        ProductInfoDto productInfoDto = platformLotteryService.getProductInfo(abandonPlatform.getProductId());
        if (productInfoDto == null) {
            productInfoDto = new ProductInfoDto();
        }
        platformActivityService.addAbandonPlatform(innerCode, abandonPlatform, productInfoDto);
        return RestResult.success();
    }

    @ApiOperation("抽奖")
    @PostMapping("/lottery")
    public RestResult<LotteryResultMO> lottery(@RequestBody @Valid LotteryPlatform lotteryPlatform,  HttpServletRequest request) throws Exception {
        String codeId = lotteryPlatform.getCodeId();
        String codeTypeId = lotteryPlatform.getCodeType();
        commonService.checkCodeMarketFakeValid(Long.valueOf(codeTypeId));
        commonService.checkCodeValid(codeId, codeTypeId);
        String innerCode = commonService.getInnerCode(codeId, codeTypeId);
        MarketingActivitySet marketingActivitySet = marketingActivitySetService.getOnlyPlatformActivity();
        ScanCodeInfoMO scanCodeInfoMO = globalRamCache.getScanCodeInfoMO(lotteryPlatform.getWxstate());
        if (scanCodeInfoMO == null) {
            throw new SuperCodeExtException("获取微信信息失败");
        }
        BeanUtils.copyProperties(lotteryPlatform, scanCodeInfoMO);
        scanCodeInfoMO.setActivitySetId(marketingActivitySet.getId());
        scanCodeInfoMO.setCodeTypeId(lotteryPlatform.getCodeType());
        LotteryOprationDto lotteryOprationDto = new LotteryOprationDto();
        lotteryOprationDto.setInnerCode(innerCode);
        //检查抽奖的初始条件是否符合
        lotteryOprationDto = platformLotteryService.checkLotteryCondition(lotteryOprationDto, scanCodeInfoMO);
        //营销码判断
        platformLotteryService.holdLockJudgeES(lotteryOprationDto);
        if(lotteryOprationDto.getSuccessLottory() == 0) {
            RestResult<LotteryResultMO> restResult = lotteryOprationDto.getRestResult();
            LotteryResultMO lotteryResultMO = lotteryOprationDto.getLotteryResultMO();
            lotteryResultMO.setData(lotteryOprationDto.getSourceLink());
            restResult.setResults(lotteryResultMO);
            return restResult;
        }
        //保存抽奖数据
        WXPayTradeOrder tradeOrder = platformLotteryService.saveLottory(lotteryOprationDto, request.getRemoteAddr());
        if (tradeOrder == null) {
            LotteryResultMO lotteryResultMO = new LotteryResultMO("哎呀没中");
            lotteryResultMO.setData(lotteryOprationDto.getSourceLink());
            return RestResult.successWithData(lotteryResultMO);
        } else {
            return platformLotteryService.saveOrder(tradeOrder, lotteryOprationDto.getSourceLink());
        }
    }


    @ApiOperation("点击更多链接埋点")
    @PostMapping("/sourceLink")
    public RestResult<?> getSourceLinkBuryPoint(@RequestBody @Valid SourceLinkBuryPoint sourceLinkBuryPoint){
        MarketingSourcelinkBury marketingSourcelinkBury = new MarketingSourcelinkBury();
        BeanUtils.copyProperties(sourceLinkBuryPoint, marketingSourcelinkBury);
        marketingSourcelinkBuryService.addSourceLinkBury(marketingSourcelinkBury);
        return RestResult.success();
    }

}
