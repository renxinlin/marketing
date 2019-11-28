package com.jgw.supercodeplatform.marketing.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.*;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author fangshiping
 * @date 2019/11/12 10:07
 */
@Controller
@RequestMapping("/marketing/export")
@Api(tags = "会员导购员导出")
public class ExportController extends CommonUtil {
    protected static Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private MarketingMembersService marketingMembersService;

    @Autowired
    private MarketingSaleMemberService service;

    @Value("{\"mobile\":\"手机\",\"userName\":\"用户\",\"sexStr\":\"性别\",\"iDNumber\":\"身份证号\",\"birthday\":\"生日\",\"babyBirthday\":\"宝宝生日\",\"provinceName\":\"省名称\",\"cityName\":\"市名称\",\"countyName\":\"县名称\",\"detailAddress\":\"详细地址\",\"customerName\":\"门店名称\",\"totalIntegral\":\"累计积分\",\"registrationApproach\":\"注册途径\"}")
    private String MARKET_MEMBERS_EXCEL_FIELD_MAP;

    @Value("{\"mobile\":\"手机\",\"userName\":\"用户\",\"mechanismType\":\"机构类型\", \"customerId\":\"机构代码\", \"customerName\":\"机构名\",\"provinceName\":\"省名称\",\"countyName\":\"县名称\",\"cityName\":\"市名称\",\"haveIntegral\":\"可用积分\",\"totalIntegral\":\"累计积分\",\"source\":\"来源\",\"state\":\"用户状态\",\"createDate\":\"建立日期\"}")
    private String MARKET_SELEMEMBERS_EXCEL_FIELD_MAP;

    private String CUSTOMER_EXCEL_FIELD_MAP;

    @PostMapping(value = "/exportMemberInfo")
    @ApiOperation(value = "导出会员资料")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", value = "token", name = "super-token")})
    public void exportInfo(HttpServletResponse response, HttpServletRequest request) throws Exception {
        logger.info("导出会员资料入参==={}",JSONObject.toJSONString(request.getParameterMap()));
        logger.info("导出会员资料入参==={}", request.getParameter("dataList"));
        List<MarketingMembers> list;
        //自定义表头
        String dataList = request.getParameter("dataList");
        if (StringUtils.isNotBlank(dataList)) {
            list = JSONObject.parseArray(dataList, MarketingMembers.class);
        } else {
            list = marketingMembersService.getMemberInfoList();
        }
        //转换字符
        list = marketingMembersService.changeList(list);
        //处理表头
        CUSTOMER_EXCEL_FIELD_MAP = request.getParameter("exportMetadata");
        String NEW_EXCEL_FIELD = null;
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(SexConstants.SEX) != -1) {
            NEW_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP.replaceAll("sex", "sexStr");
        }
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(PcccodeConstants.pCCcode)!=-1){
            if (NEW_EXCEL_FIELD == null){
                NEW_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP;
            }
            NEW_EXCEL_FIELD=NEW_EXCEL_FIELD.replaceAll("pCCcode","codeStr");
        }
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(StateConstants.STATE) != -1) {
            if (NEW_EXCEL_FIELD == null){
                NEW_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP;
            }
            NEW_EXCEL_FIELD=NEW_EXCEL_FIELD.replaceAll("state","stateStr");
        }
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(RegistrationApproachConstants.registrationApproach)!=-1){
            if (NEW_EXCEL_FIELD == null){
                NEW_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP;
            }
            NEW_EXCEL_FIELD=NEW_EXCEL_FIELD.replaceAll("registrationApproach","registrationApproachStr");
        }
        logger.info("-----------自定义表头-----------" + CUSTOMER_EXCEL_FIELD_MAP);
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            if (StringUtils.isNotBlank(NEW_EXCEL_FIELD)) {
                filedMap = JsonToMapUtil.toMap(NEW_EXCEL_FIELD);
            } else {
                filedMap = JsonToMapUtil.toMap(CUSTOMER_EXCEL_FIELD_MAP);
            }
        } catch (Exception e) {
            throw new SuperCodeException("会员资料表头解析异常", 500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "会员资料", response);
    }


    @ResponseBody
    @PostMapping(value = "/exportSalemembers")
    @ApiOperation(value = "导出导购员资料")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", value = "token", name = "super-token")})
    public void exportSalemembers(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 查询组织导购员列表
        List<MarketingUser> list;
        String dataList = request.getParameter("dataList");
        if (StringUtils.isNotBlank(dataList)) {
            list = JSONObject.parseArray(dataList, MarketingUser.class);
        } else {
            list = service.getSalerInfoList();
        }
        //转换字符
        list = service.changeList(list);

        CUSTOMER_EXCEL_FIELD_MAP = request.getParameter("exportMetadata");
        logger.info("-----------自定义表头-----------" + CUSTOMER_EXCEL_FIELD_MAP);
        String NEW_USER_EXCEL_FIELD = null;
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(MechanismTypeConstants.mechanismType)!=-1){
            NEW_USER_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP.replaceAll("mechanismType","mechanismTypeStr");
        }
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(StateConstants.STATE) != -1) {
            if (NEW_USER_EXCEL_FIELD == null){
                NEW_USER_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP;
            }
            NEW_USER_EXCEL_FIELD=NEW_USER_EXCEL_FIELD.replaceAll("state","stateStr");
        }
        if (CUSTOMER_EXCEL_FIELD_MAP.indexOf(UserSourceConstants.source) != -1) {
            if (NEW_USER_EXCEL_FIELD == null){
                NEW_USER_EXCEL_FIELD = CUSTOMER_EXCEL_FIELD_MAP;
            }
            NEW_USER_EXCEL_FIELD=NEW_USER_EXCEL_FIELD.replaceAll("source","sourceStr");
        }
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            if (StringUtils.isNotBlank(NEW_USER_EXCEL_FIELD)) {
                filedMap = JsonToMapUtil.toMap(NEW_USER_EXCEL_FIELD);
            } else {
                filedMap = JsonToMapUtil.toMap(CUSTOMER_EXCEL_FIELD_MAP);
            }

        } catch (Exception e) {
            throw new SuperCodeException("导购员资料表头解析异常", 500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "导购员资料", response);
    }


}
