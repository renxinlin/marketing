package com.jgw.supercodeplatform.marketing.controller.user;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/marketing/members")
@Api(tags = "会员管理")
public class MarketingMembersController extends CommonUtil {

    @Autowired
    private MarketingMembersService marketingMembersService;

    @Value("{\"id\":\"序号\", \"wxName\":\"微信昵称\",\"mobile\":\"手机\",\"userId\":\"用户Id\",\"userName\":\"用户姓名\",\"sexStr\":\"性别\", \"birthday\":\"生日\",\"provinceName\":\"省名称\",\"countyName\":\"县名称\",\"cityName\":\"市名称\",\"registDate\":\"注册时间\",\"state\":\"会员状态\",\"newRegisterFlag\":\"是否新注册的标志\",\"createDate\":\"建立日期\",\"updateDate\":\"修改日期\",\"customerName\":\"门店名称\",\"babyBirthday\":\"宝宝生日\",\"isRegistered\":\"是否已完善(1、表示已完善，0 表示未完善)\",\"haveIntegral\":\"添加可用积分\",\"memberType\":\"类型\",\"integralReceiveDate\":\"最新一次积分领取时间\",\"userSource\":\"注册来源1招募会员\",\"deviceType\":\"扫码设备类型\"}")
    private String MARKET_MEMBERS_EXCEL_FIELD_MAP;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "会员列表", notes = "")
    @ApiImplicitParam(name = "sudper-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<Map<String, Object>>>> memberList(MarketingMembersListParam param) throws Exception {
        return new RestResult(200, "success", marketingMembersService.listSearchViewLike(param));
    }


    @RequestMapping(value = "/getMenberById",method = RequestMethod.GET)
    @ApiOperation(value = "根据会员id获取会员详细信息", notes = "返回会员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
    })
    public RestResult<String> getUserMember(Long id) throws Exception {
        return new RestResult(200, "success",marketingMembersService.getMemberById(id));
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑会员", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> updateMember(@Valid @RequestBody MarketingMembersUpdateParam marketingMembersUpdateParam) throws Exception {
        marketingMembersService.updateMembers(marketingMembersUpdateParam);
        return new RestResult(200, "success",null );
    }




    @RequestMapping(value = "/enable/status", method = RequestMethod.GET)
    @ApiOperation(value = "启用会员", notes = "是否启用成功")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
        @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
})
    public RestResult<String> enableStatus(Long id) throws Exception {
    	if (null==id) {
			throw new SuperCodeException("id不能为空", 500);
		}
        marketingMembersService.updateMembersStatus(id,1);
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/disable/status", method = RequestMethod.GET)
    @ApiOperation(value = "禁用会员", notes = "是否禁用成功")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
        @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
})
    public RestResult<String> disableStatus(Long id ) throws Exception {
    	if (null==id) {
			throw new SuperCodeException("id不能为空", 500);
		}
        marketingMembersService.updateMembersStatus(id,0);
        return new RestResult(200, "success", null);
    }


    @GetMapping(value = "/exportMemberInfo")
    @ApiOperation(value = "导出会员资料")
    @ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "token",name="super-token")})
    public void exportInfo(HttpServletResponse response) throws Exception {
        List<MarketingMembers> list = marketingMembersService.getMemberInfoList();
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            filedMap = JsonToMapUtil.toMap(MARKET_MEMBERS_EXCEL_FIELD_MAP);
        } catch (Exception e) {
            throw new SuperCodeException("会员资料表头解析异常", 500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "会员资料", response);
    }


}
