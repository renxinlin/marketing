package com.jgw.supercodeplatform.marketing.controller;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonObject;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/marketing/members")
@Api(tags = "会员管理")
public class MarketingMembersController extends CommonUtil {


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "招募会员（注册）", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult addMember(
            @ApiJsonObject(name = "addMembers", value = {
                    @ApiJsonProperty(key = "wxId", example = "wxid_asds4ad564sa56d", description = "微信号,必需"),
                    @ApiJsonProperty(key = "wxName",example = "zhangsan",description="微信姓名,必需"),
                    @ApiJsonProperty(key = "mobileId", example = "18268322268", description = "手机号,必需"),
                    @ApiJsonProperty(key = "userName",example = "zhangsan",description="用户姓名,必需"),
                    @ApiJsonProperty(key = "sex", example = "男", description = "性别Name,必需"),
                    @ApiJsonProperty(key = "birthday", example = "1979-02-21", description = "生日,必需"),
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需"),
                    @ApiJsonProperty(key = "organizationFullName",example = "中化通讯公司",description="组织全称,必需"),
                    @ApiJsonProperty(key = "provinceCode", example = "f4c1054fe0b84bd0a448070d98110b22", description = "省编码,必需"),
                    @ApiJsonProperty(key = "countyCode", example = "f4c1054fe0b84bd0a448070d98110b22", description = "县编码,必需"),
                    @ApiJsonProperty(key = "cityCode", example = "f4c1054fe0b84bd0a448070d98110b22", description = "市编码,必需"),
                    @ApiJsonProperty(key = "provinceName", example = "浙江省", description = "省名称,必需"),
                    @ApiJsonProperty(key = "countyName", example = "浙江省", description = "县名称,必需"),
                    @ApiJsonProperty(key = "cityName", example = "杭州市", description = "市名称,必需"),
                    @ApiJsonProperty(key = "stores", example = "杭州店", description = "门店或经销商"),
                    @ApiJsonProperty(key = "storesType", example = "1", description = "门店或经销商类型（1表示门店 ，2表示经销商）"),
                    @ApiJsonProperty(key = "babyBirthday", example = "1979-02-21", description = "宝宝生日")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "根据组织id分页显示会员列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "dsadsad165156163a1sddasd", value = "组织id,必需"),
            @ApiImplicitParam(name = "search", paramType = "query", defaultValue = "12", value = "搜索条件,可以根据姓名,手机号或者工号模糊搜索,非必需"),
            @ApiImplicitParam(name = "mobileId", paramType = "query", defaultValue = "18268322268", value = "高级搜索的手机号,非必需"),
            @ApiImplicitParam(name = "wxName", paramType = "query", defaultValue = "zhangsan", value = "高级搜索的微信名称,非必需"),
            @ApiImplicitParam(name = "wxId", paramType = "query", defaultValue = "wxid_asds4ad564sa56d", value = "高级搜索的微信id,非必需"),
            @ApiImplicitParam(name = "userName", paramType = "query", defaultValue = "zhangsan", value = "高级搜索的用户姓名,非必需"),
            @ApiImplicitParam(name = "sex", paramType = "query", defaultValue = "男", value = "高级搜索的性别,非必需"),
            @ApiImplicitParam(name = "birthday", paramType = "query", defaultValue = "1979-02-21", value = "高级搜索的生日,非必需"),
            @ApiImplicitParam(name = "provinceName", paramType = "query", defaultValue = "浙江省", value = "高级搜索的省名称,非必需"),
            @ApiImplicitParam(name = "countyName", paramType = "query", defaultValue = "浙江省", value = "高级搜索的县名称,非必需"),
            @ApiImplicitParam(name = "cityName", paramType = "query", defaultValue = "杭州市", value = "高级搜索的市名称,非必需"),
            @ApiImplicitParam(name = "stores", paramType = "query", defaultValue = "杭州店", value = "高级搜索的注册门店,非必需"),
            @ApiImplicitParam(name = "babyBirthday", paramType = "query", defaultValue = "1979-02-21", value = "高级搜索的宝宝生日,非必需"),
            @ApiImplicitParam(name = "newRegisterFlag", paramType = "query", defaultValue = "1", value = "高级搜索的是否新注册的标志(1  表示是，0 表示不是),非必需"),
            @ApiImplicitParam(name = "registDate", paramType = "query", defaultValue = "1979-02-21", value = "高级搜索的注册时间,非必需"),
            @ApiImplicitParam(name = "state", paramType = "query", defaultValue = "1", value = "高级搜索的状态(1、 表示正常，0 表示下线),非必需"),
            @ApiImplicitParam(name = "startTime", paramType = "query", defaultValue = "2018-10-11", value = "高级搜索的开始时间,非必需"),
            @ApiImplicitParam(name = "endTime", paramType = "query", defaultValue = "2018-10-11", value = "高级搜索的结束时间,非必需"),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
    })
    public RestResult memberList(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "organizationId");
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/getMenberByUserId",method = RequestMethod.GET)
    @ApiOperation(value = "根据会员id获取会员详细信息", notes = "返回会员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "userId", paramType = "query", defaultValue = "ad156wd15d61a56d1w56d1d1", value = "用户Id,必需", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "02a61bd8703c4b0eb6a6f62fe709b0c6", value = "组织Id,必需", required = true)
    })
    public RestResult getUserMember(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId","organizationId");
        return new RestResult(200, "success",null);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑会员", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult updateMember(
            @ApiJsonObject(name = "updateMembers", value = {
                    @ApiJsonProperty(key = "userId", example = "ad156wd15d61a56d1w56d1d1", description = "用户id,必需"),
                    @ApiJsonProperty(key = "userName",example = "zhangsan",description="用户姓名,必需"),
                    @ApiJsonProperty(key = "sex", example = "男", description = "性别Name,必需"),
                    @ApiJsonProperty(key = "birthday", example = "1979-02-21", description = "生日,必需"),
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需"),
                    @ApiJsonProperty(key = "provinceCode", example = "f4c1054fe0b84bd0a448070d98110b22", description = "省编码,必需"),
                    @ApiJsonProperty(key = "countyCode", example = "f4c1054fe0b84bd0a448070d98110b22", description = "县编码,必需"),
                    @ApiJsonProperty(key = "cityCode", example = "f4c1054fe0b84bd0a448070d98110b22", description = "市编码,必需"),
                    @ApiJsonProperty(key = "provinceName", example = "浙江省", description = "省名称,必需"),
                    @ApiJsonProperty(key = "countyName", example = "浙江省", description = "县名称,必需"),
                    @ApiJsonProperty(key = "cityName", example = "杭州市", description = "市名称,必需"),
                    @ApiJsonProperty(key = "stores", example = "杭州店", description = "门店或经销商"),
                    @ApiJsonProperty(key = "storesType", example = "1", description = "门店或经销商类型（1表示门店 ，2表示经销商）"),
                    @ApiJsonProperty(key = "babyBirthday", example = "1979-02-21", description = "宝宝生日")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/enable/status", method = RequestMethod.PUT)
    @ApiOperation(value = "启用会员", notes = "是否启用成功")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult enableStatus(
            @ApiJsonObject(name = "enableMemStatus", value = {
                    @ApiJsonProperty(key = "userId", example = "64b379cd47c843458378f479a115c322", description = "用户id,必需"),
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId");
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/disable/status", method = RequestMethod.PUT)
    @ApiOperation(value = "禁用会员", notes = "是否禁用成功")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult disableStatus(
            @ApiJsonObject(name = "disableMemStatus", value = {
                    @ApiJsonProperty(key = "userId", example = "64b379cd47c843458378f479a115c322", description = "用户id,必需"),
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId");
            return new RestResult(200, "success", null);
    }

    /**
     * 创建二维码
     * @param content
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/qrCode", method = RequestMethod.GET)
    @ApiOperation(value = "生成二维码接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "content", paramType = "query", defaultValue = "http://www.baidu.com", value = "", required = true),
    })
    public  boolean createQrCode(String content, HttpServletResponse response) throws IOException {
        return true;
    }

    @RequestMapping(value = "/getOrg",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户登录的组织信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult getUserOrg(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId","organizationId");
        getOrganization();
        return new RestResult(200, "success",null);
    }



}
