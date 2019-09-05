package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.User;
import com.jgw.supercodeplatform.marketingsaler.integral.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/salerUser")
@Api(value = "", tags = "")
public class UserController extends SalerCommonController {

//    @Autowired
//    private UserService service;
//
//    @PostMapping("/save")
//    @ApiOperation(value = "", notes = "")
//    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
//    public RestResult save(@RequestBody User obj) throws CommonException {
//        return RestResult.success();
//    }
//
//    @PostMapping("/update")
//    @ApiOperation(value = "", notes = "")
//    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
//    public RestResult update(@RequestBody User obj) throws CommonException {
//        return RestResult.success();
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "", notes = "")
//    public RestResult getById(@PathVariable("id") String id) throws CommonException {
//        return null;
//    }
//
//    @GetMapping("/list")
//    @ApiOperation(value = "", notes = "")
//    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
//    public RestResult list(User obj) throws CommonException {
//        return null;
//    }


}

