package com.jgw.supercodeplatform.marketing.controller.user;

import com.jgw.supercodeplatform.exception.SuperCodeException;
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

    @Value("{\"mobile\":\"手机\",\"userName\":\"用户姓名\",\"sexStr\":\"性别\", \"birthday\":\"生日\",\"provinceName\":\"省名称\",\"countyName\":\"县名称\",\"cityName\":\"市名称\",\"registDate\":\"注册时间\",\"state\":\"会员状态\",\"newRegisterFlag\":\"是否新注册的标志\",\"createDate\":\"建立日期\",\"updateDate\":\"修改日期\",\"customerName\":\"门店名称\",\"babyBirthday\":\"宝宝生日\",\"isRegistered\":\"是否已完善(1、表示已完善，0 表示未完善)\",\"haveIntegral\":\"添加可用积分\",\"memberType\":\"类型\",\"integralReceiveDate\":\"最新一次积分领取时间\",\"userSource\":\"注册来源1招募会员\",\"deviceType\":\"扫码设备类型\"}")
    private String MARKET_MEMBERS_EXCEL_FIELD_MAP;

    @Value("{\"mobile\":\"手机\",\"userName\":\"用户姓名\",\"sex\":\"性别\", \"birthday\":\"生日\",\"provinceName\":\"省名称\",\"countyName\":\"县名称\",\"cityName\":\"市名称\",\"createDate\":\"建立日期\",\"updateDate\":\"修改日期\",\"customerName\":\"门店名称\",\"customerId\":\"门店编码\",\"pCCcode\":\"省市区前端编码\",\"memberType\":\"类型\",\"state\":\"用户状态\",\"deviceType\":\"扫码设备类型\",\"haveIntegral\":\"添加可用积分\"}")
    private String MARKET_SELEMEMBERS_EXCEL_FIELD_MAP;

    private String CUSTOMER_EXCEL_FIELD_MAP;

    @PostMapping(value = "/exportMemberInfo")
    @ApiOperation(value = "导出会员资料")
    @ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "token",name="super-token")})
    public void exportInfo(HttpServletResponse response,HttpServletRequest request) throws Exception {
        /*HashMap<String,Object> hashMap= (HashMap) request.getParameterMap();
        if (hashMap != null){
            for(Map.Entry<String,Object> entry:hashMap.entrySet()){

            }
        }*/
        //自定义表头
        CUSTOMER_EXCEL_FIELD_MAP=request.getParameter("exportMetadata");
        logger.info("-----------自定义表头-----------"+CUSTOMER_EXCEL_FIELD_MAP);
        List<MarketingMembers> list = marketingMembersService.getMemberInfoList();
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            if(StringUtils.isNotBlank(CUSTOMER_EXCEL_FIELD_MAP)){
                filedMap=JsonToMapUtil.toMap(CUSTOMER_EXCEL_FIELD_MAP);
            }else {
            filedMap = JsonToMapUtil.toMap(MARKET_MEMBERS_EXCEL_FIELD_MAP);
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
    @ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "token",name="super-token")})
    public void exportSalemembers(HttpServletResponse response,HttpServletRequest request) throws Exception {
        CUSTOMER_EXCEL_FIELD_MAP=request.getParameter("exportMetadata");
        logger.info("-----------自定义表头-----------"+CUSTOMER_EXCEL_FIELD_MAP);
        // 查询组织导购员列表
        List<MarketingUser> list=service.getSalerInfoList();
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            if(StringUtils.isNotBlank(CUSTOMER_EXCEL_FIELD_MAP)){
                filedMap = JsonToMapUtil.toMap(CUSTOMER_EXCEL_FIELD_MAP);
            }else{
                filedMap = JsonToMapUtil.toMap(MARKET_SELEMEMBERS_EXCEL_FIELD_MAP);
            }

        } catch (Exception e) {
            throw new SuperCodeException("导购员资料表头解析异常", 500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "导购员资料", response);
    }


}
