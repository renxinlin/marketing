package com.jgw.supercodeplatform.marketing.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.page.Page;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.dto.MarketingSaleMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingUserVO;
import com.jgw.supercodeplatform.marketing.dto.SaleMemberBatchStatusParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingSaleUserParam;
import com.jgw.supercodeplatform.marketing.dto.members.SaleMemberStatusParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/marketing/salemembers")
@Api(tags = "销售员管理")
public class MarketingSaleMembersController extends CommonUtil {
    public static void main(String[] args) {
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    @Autowired
    private MarketingSaleMemberService service;
    @Autowired
    private ModelMapper modelMapper;

    @Value("{\"id\":\"序号\",\"mobile\":\"手机\", \"userId\":\"用户Id\",\"userName\":\"用户姓名\",\"sex\":\"性别\", \"birthday\":\"生日\",\"provinceName\":\"省名称\",\"countyName\":\"县名称\",\"cityName\":\"市名称\",\"createDate\":\"建立日期\",\"updateDate\":\"修改日期\",\"customerName\":\"门店名称\",\"customerId\":\"门店编码\",\"pCCcode\":\"省市区前端编码\",\"memberType\":\"类型\",\"state\":\"用户状态\",\"deviceType\":\"扫码设备类型\",\"haveIntegral\":\"添加可用积分\"}")
    private String MARKET_SELEMEMBERS_EXCEL_FIELD_MAP;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "销售员列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<MarketingSaleUserParam>>> memberList(MarketingMembersListParam param) throws Exception {
        // 查询组织导购员列表
        param.setOrganizationId(getOrganizationId());
        PageResults<List<MarketingUser>> objectPageResults = service.listSearchViewLike(param);


        // 参数转换
        Page pagination = objectPageResults.getPagination();
        List<MarketingUser> list = objectPageResults.getList();
        List<MarketingSaleUserParam> listVo = new ArrayList<>();
        for(MarketingUser dto : list){
            MarketingSaleUserParam vo = modelMapper.map(dto, MarketingSaleUserParam.class);
            vo.setAddress(dto.getProvinceName()+dto.getCityName()+dto.getCountyName());
            vo.setRandomId(System.nanoTime());
            listVo.add(vo);
        }
        PageResults<List<MarketingSaleUserParam>> voPages = new   AbstractPageService.PageResults<List<MarketingSaleUserParam>>(listVo,pagination);
        return new RestResult(200, "success", voPages);
    }


    @RequestMapping(value = "/getMenberById",method = RequestMethod.GET)
    @ApiOperation(value = "根据会员id获取会员详细信息", notes = "返回会员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
    })
    public RestResult<MarketingUserVO> getUserMember(Long id) throws Exception {
        MarketingUser memberById = service.getMemberById(id);
        if(memberById == null){
            throw new SuperCodeException("用户不存在");
        }
        MarketingUserVO vo = modelMapper.map(memberById, MarketingUserVO.class);
        List pcccodes = new LinkedList();
        Map pcccode = new HashMap<>();
        pcccode.put("areaCode",vo.getProvinceCode());
        pcccode.put("areaName",vo.getProvinceName());
        pcccodes.add(pcccode);
        Map pcccode1 = new HashMap<>();
        pcccode1.put("areaCode",vo.getCityCode());
        pcccode1.put("areaName",vo.getCityName());
        pcccodes.add(pcccode1);
        Map pcccode2 = new HashMap<>();
        pcccode2.put("areaCode",vo.getCountyCode());
        pcccode2.put("areaName",vo.getCountyName());
        pcccodes.add(pcccode2);
        vo.setPCCcode(JSONObject.toJSONString(pcccodes));
        return new RestResult(200, "success",vo);
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑导购员", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> updateMember(@Valid @RequestBody MarketingSaleMembersUpdateParam marketingMembersUpdateParam) throws Exception {
        service.updateMembers(marketingMembersUpdateParam,getOrganizationId());
        return new RestResult(200, "success",null );
    }




    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    @ApiOperation(value = "修改导购员状态2停用3启用", notes = "修改导购员状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> enableStatus(@RequestBody SaleMemberStatusParam sale) throws SuperCodeException {
        if (null==sale) {
            throw new SuperCodeException("参数不存在...", 500);
        }
        service.updateMembersStatus(sale.getId(),sale.getState(),getOrganizationId());
        return new RestResult(200, "success", null);
    }







    @RequestMapping(value = "/batchUpdate/status", method = RequestMethod.POST)
    @ApiOperation(value = "批量修改导购员状态2停用3启用", notes = "修改导购员状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> enableStatus(@RequestBody SaleMemberBatchStatusParam sale) throws SuperCodeException {
        if (null==sale) {
            throw new SuperCodeException("参数不存在...", 500);
        }
        service.updateMembersBatchStatus(sale,getOrganizationId());
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除导购员", notes = "删除导购员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
    })
    public RestResult<String> disableStatus(Long id ) throws Exception {
         service.deleteSalerByOrg(id,getOrganizationId());
         return new RestResult(200, "success", null);
    }





    @RequestMapping(value = "/judge/status", method = RequestMethod.GET)
    @ApiOperation(value = "审核", notes = "审核通过(启用)|不通过(停用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
    })
    public RestResult<String> judge(Long id ,Integer state) throws Exception {
        if (null==id) {
            throw new SuperCodeException("id不能为空", 500);
        }
        service.updateMembersStatus(id,state,getOrganizationId());
        return new RestResult(200, "success", null);
    }

    @GetMapping(value = "/exportSalemembers")
    @ApiOperation(value = "导出导购员资料")
    @ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "token",name="super-token")})
    public void exportSalemembers(HttpServletResponse response) throws Exception {
        // 查询组织导购员列表
        List<MarketingUser> list=service.getSalerInfoList();
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            filedMap = JsonToMapUtil.toMap(MARKET_SELEMEMBERS_EXCEL_FIELD_MAP);
        } catch (Exception e) {
            throw new SuperCodeException("导购员资料表头解析异常", 500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "导购员资料", response);
    }

}
