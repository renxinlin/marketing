package com.jgw.supercodeplatform.signin.controller;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.signin.service.SignInExchangeService;
import com.jgw.supercodeplatform.signin.pojo.SignInExchange;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fangshiping
 * @since 2019-10-12
 */
@RestController
@RequestMapping("/b2b//signInExchange")
    @Api(value = "", tags = "")
    public class SignInExchangeController  extends SalerCommonController{
// 可在模版中添加相应的controller通用方法，编辑模版在resources/templates/controller.java.vm文件中

        @Autowired
        private SignInExchangeService service;

        @PostMapping("/save")
        @ApiOperation(value = "", notes = "")
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
        public RestResult save(@RequestBody SignInExchange obj) throws CommonException {
                return RestResult.success();
        }

        @PostMapping("/update")
        @ApiOperation(value = "", notes = "")
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
        public RestResult update(@RequestBody SignInExchange obj) throws CommonException {
            return RestResult.success();
        }

        @GetMapping("/{id}")
        @ApiOperation(value = "", notes = "")
        public RestResult getById(@PathVariable("id") String id) throws CommonException {
            return null;
        }

        @GetMapping("/list")
        @ApiOperation(value = "", notes = "")
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
        public RestResult list(SignInExchange obj) throws CommonException {
            return null;
        }





}

