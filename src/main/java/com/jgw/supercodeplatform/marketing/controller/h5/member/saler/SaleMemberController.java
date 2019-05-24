package com.jgw.supercodeplatform.marketing.controller.h5.member.saler;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.dto.SaleInfo;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMemberAndScanCodeInfoParam;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.LotteryService;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

import java.util.Map;

/**
 * 销售员扫码领红包
 */
@RestController
@RequestMapping("/marketing/saleMember/")
@Api(tags = "销售员H5")
public class SaleMemberController {
    private static Logger logger = LoggerFactory.getLogger(SaleMemberController.class);
    @Autowired
    private IntegralRecordService service;
    @Autowired
    private CodeEsService es;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private GlobalRamCache globalRamCache;

    @Autowired
    private MarketingSaleMemberService marketingSaleMemberService;

    @GetMapping("info")
    @ApiOperation(value = "销售员中心", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<SaleInfo> info(@ApiIgnore H5LoginVO jwtUser) throws Exception {
        if(jwtUser.getMemberType() == null){
            throw new SuperCodeException("服务器未指定用户角色...");

        }
        if(MemberTypeEnums.SALER.getType().intValue()!=jwtUser.getMemberType()){
            throw new SuperCodeException("会员角色错误...");
        }
        SaleInfo saleInfo = new SaleInfo();
        // 1 获取红包统计信息
        Map acquireMoneyAndAcquireNums = service.getAcquireMoneyAndAcquireNums(jwtUser.getMemberId(), jwtUser.getMemberType(), jwtUser.getOrganizationId());
        // 3 获取扫码信息
        Integer scanNum = es.searchScanInfoNum(jwtUser.getMemberId(), MemberTypeEnums.SALER.getType());
        // 4 数据转换


        MarketingUser marketingUser = marketingSaleMemberService.selectById(jwtUser.getMemberId());
        saleInfo.setUserName(marketingUser != null ? marketingUser.getUserName():null);
        saleInfo.setUserName(marketingUser != null ? marketingUser.getWechatHeadImgUrl():null);
        saleInfo.setScanQRCodeNum(scanNum);
        saleInfo.setScanAmoutNum((Integer) acquireMoneyAndAcquireNums.get("count"));
        saleInfo.setAmoutNum((Float) acquireMoneyAndAcquireNums.get("sum"));
        saleInfo.setAmoutNumStr(saleInfo.getAmoutNum()+"");


      return RestResult.success("success",saleInfo);
    }




    @GetMapping("page")
    @ApiOperation(value = "销售员中心page", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult page(@ApiIgnore H5LoginVO jwtUser, DaoSearch search) throws Exception {
        if(jwtUser.getMemberType()==null|| MemberTypeEnums.SALER.getType().intValue()!=jwtUser.getMemberType()){
            throw new SuperCodeException("会员角色错误...");
        }
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
        // 4 数据转换
        return RestResult.success("success",objectPageResults);
    }

    /**
     * 应前端要求将扫码信息和埋点整合一个接口
     * @param orgId
     * @param wxstate
     * @param jwtUser
     * @return
     */
    @GetMapping("getOrgName")
    @ApiOperation(value = "获取组织名称并且传递wxstate", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<Map<String,String>> getOrgNameAndAnsycPushScanIfo(@RequestParam("organizationId") String orgId ,@RequestParam("wxstate")String wxstate, @ApiIgnore H5LoginVO jwtUser) throws SuperCodeException {
        // 数据埋点
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // 获取扫码信息
                MarketingMemberAndScanCodeInfoParam infoParam = new MarketingMemberAndScanCodeInfoParam();
                try{
                    ScanCodeInfoMO scanCodeInfoMO = globalRamCache.getScanCodeInfoMO(wxstate);
                    BeanUtils.copyProperties(scanCodeInfoMO, infoParam);
                    infoParam.setUserId(jwtUser.getMemberId());
                    infoParam.setUserName(jwtUser.getMemberName());
                    infoParam.setMemberType(jwtUser.getMemberType());
                    commonService.indexScanInfo(infoParam);


                }catch (Exception e){
                    logger.info("扫码信息插入失败");
                    logger.info(e.getMessage(), e);
                }


            }
        });
        // 业务处理: 获取企业名称
        boolean haveOrgId = validateParam(orgId, wxstate);
        return getNameByIdWithDefaultWhenError(orgId,haveOrgId);
    }
    private RestResult<Map<String, String>> getNameByIdWithDefaultWhenError(String orgId,boolean haveOrgId) {
        String defaultValue = "企业";
        Map<String, String> orgInfo = new HashMap<>();
        if(!haveOrgId){
            orgInfo.put("organizationName",defaultValue);
        }else{
            String organizationName = null;
            try {
                organizationName = commonService.getOrgNameByOrgId(orgId);
            } catch (SuperCodeException e) {
                e.printStackTrace();
            }
            if(StringUtils.isBlank(organizationName)){
                organizationName = defaultValue;
            }
            orgInfo.put("organizationName",organizationName);

        }
        return RestResult.success("success",orgInfo);
    }

    private boolean validateParam(String orgId, String wxstate)   {
        if(StringUtils.isBlank(orgId)){
            return false;
        }
        if(StringUtils.isBlank(wxstate)){
            logger.error("导购领奖:获取微信state 失败");
        }
        return true;
    }


}
