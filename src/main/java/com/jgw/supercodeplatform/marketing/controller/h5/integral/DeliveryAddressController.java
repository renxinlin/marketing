package com.jgw.supercodeplatform.marketing.controller.h5.integral;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dto.integral.DeliveryAddressDetailParam;
import com.jgw.supercodeplatform.marketing.dto.integral.DeliveryAddressParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import com.jgw.supercodeplatform.marketing.service.integral.DeliveryAddressService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
@RequestMapping("/marketing/address")
@Api(tags = "积分地址管理")
public class DeliveryAddressController extends CommonUtil {


    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MarketingMembersMapper membersMapper;

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "列表5个地址", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息")
    })
    public RestResult getAll(@ApiIgnore H5LoginVO jwtUser) throws Exception {
        Long memberId = jwtUser.getMemberId();
        List<DeliveryAddress> deliveryAddresses = deliveryAddressService.selectByUser(memberId);



        // 转换VO
        List<DeliveryAddressParam> deliveryAddressesVO = new LinkedList<>();
        for( DeliveryAddress deliveryAddress: deliveryAddresses){
            // 转换VO
            DeliveryAddressParam deliveryAddressVO = modelMapper.map(deliveryAddress, DeliveryAddressParam.class);
            if(deliveryAddressVO.getDefaultUsing() == (byte)1){
                deliveryAddressVO.setDefaultUsingWeb(false);
            }else {
                deliveryAddressVO.setDefaultUsingWeb(true);
            }
            deliveryAddressesVO.add(deliveryAddressVO);
        }
        return RestResult.success("success",deliveryAddressesVO);
    }


    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "获取地址详情", notes = "")
    @ApiImplicitParams(value= {  @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult get(@RequestParam("id") Long id, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        Long memberId = jwtUser.getMemberId();
        DeliveryAddress deliveryAddress = deliveryAddressService.selectById(id, memberId);


        // 转换VO
        DeliveryAddressParam deliveryAddressVO = modelMapper.map(deliveryAddress, DeliveryAddressParam.class);
        if(deliveryAddressVO.getDefaultUsing() == (byte)1){
            deliveryAddressVO.setDefaultUsingWeb(false);
        }else {
            deliveryAddressVO.setDefaultUsingWeb(true);
        }

        // 转换结构
        List pcccodes = new LinkedList();
        Map pcccode = new HashMap<>();
        pcccode.put("areaCode",deliveryAddressVO.getProvinceCode());
        pcccode.put("areaName",deliveryAddressVO.getProvince());
        pcccodes.add(pcccode);
        Map pcccode1 = new HashMap<>();
        pcccode1.put("areaCode",deliveryAddressVO.getProvinceCode());
        pcccode1.put("areaName",deliveryAddressVO.getProvince());
        pcccodes.add(pcccode1);
        Map pcccode2 = new HashMap<>();
        pcccode2.put("areaCode",deliveryAddressVO.getProvinceCode());
        pcccode2.put("areaName",deliveryAddressVO.getProvince());
        pcccodes.add(pcccode2);
        deliveryAddressVO.setPcccode(JSONObject.toJSONString(pcccodes));


        return RestResult.success("success",modelMapper.map(deliveryAddressVO, DeliveryAddressDetailParam.class));
    }



    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增地址不超过5个", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult<DeliveryAddress> add(@RequestBody DeliveryAddressParam deliveryAddress, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        // 前端格式转换
        DeliveryAddress deliveryAddressDto = modelMapper.map(deliveryAddress, DeliveryAddress.class);
        if(deliveryAddress.getDefaultUsingWeb()){
            deliveryAddressDto.setDefaultUsing((byte)0);
        }else{
            // 不是默认
            deliveryAddressDto.setDefaultUsing((byte)1);
        }
        Long memberId = jwtUser.getMemberId();
        deliveryAddressDto.setMemberId(memberId);
        deliveryAddressDto.setMemberName(membersMapper.getMemberById(memberId).getUserName());
        DeliveryAddress toWeb = deliveryAddressService.add(deliveryAddressDto);
        // TODO 优化
        toWeb.setDetailAll(toWeb.getProvince()+toWeb.getCity()+toWeb.getCountry()+toWeb.getDetail());
        return RestResult.success("success",toWeb);
    }



    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑地址|与设置默认地址", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult update(@RequestBody  DeliveryAddressParam deliveryAddress, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        // 前端格式转换
        DeliveryAddress deliveryAddressDto = modelMapper.map(deliveryAddress, DeliveryAddress.class);
        if(deliveryAddress.getDefaultUsingWeb()){
            deliveryAddressDto.setDefaultUsing((byte)0);
        }else{
            // 不是默认
            deliveryAddressDto.setDefaultUsing((byte)1);

        }
        Long memberId = jwtUser.getMemberId();
        deliveryAddressDto.setMemberId(memberId);
        int i = deliveryAddressService.update(deliveryAddressDto);
        return RestResult.success();
    }



    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value = "删除地址", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult deleteProduct(@RequestParam("id") Long id, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        Long memberId = jwtUser.getMemberId();
        int i = deliveryAddressService.delete(id,memberId);
        return RestResult.success();
    }


}
