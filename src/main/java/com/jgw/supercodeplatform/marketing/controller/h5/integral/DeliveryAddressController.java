package com.jgw.supercodeplatform.marketing.controller.h5.integral;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
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

import java.util.List;

@RestController
@RequestMapping("/marketing/address")
@Api(tags = "积分地址管理")
public class DeliveryAddressController extends CommonUtil {


    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "列表5个地址", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息")
    })
    public RestResult getAll(@ApiIgnore H5LoginVO jwtUser) throws Exception {
        Long memberId = jwtUser.getMemberId();
        List<DeliveryAddress> deliveryAddresses = deliveryAddressService.selectByUser(memberId);
        return RestResult.success("success",deliveryAddresses);
    }


    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "获取地址详情", notes = "")
    @ApiImplicitParams(value= {  @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult get(@RequestParam("id") Long id, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        Long memberId = jwtUser.getMemberId();
        DeliveryAddress deliveryAddress = deliveryAddressService.selectById(id, memberId);
        return RestResult.success("success",deliveryAddress);
    }



    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增地址不超过5个", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult add(@RequestBody DeliveryAddressParam deliveryAddress, @ApiIgnore H5LoginVO jwtUser) throws Exception {
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
        int i =  deliveryAddressService.add(deliveryAddressDto);
        return RestResult.success();
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
