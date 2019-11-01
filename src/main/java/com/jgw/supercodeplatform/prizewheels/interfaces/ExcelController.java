package com.jgw.supercodeplatform.prizewheels.interfaces;

import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.prizewheels.application.service.ExcelApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fangshiping
 * @date 2019/10/14 16:38
 */
@RestController
@RequestMapping("marketing/excel")
@Api(value = "excel", tags = "excel")
public class ExcelController extends SalerCommonController {

    @Autowired
    private ExcelApplication excelApplication;

    @PostMapping("/uploadExcel")
    @ApiOperation(value = "上传Excel文件",notes = "文件唯一id")
    @ApiImplicitParams({@ApiImplicitParam(name = "super-token",paramType ="header", value="token信息",required=true)
            ,@ApiImplicitParam(name = "uploadFile",paramType ="file",required=true)})
    public RestResult<String> uploadExcel(@RequestParam MultipartFile uploadFile) throws Exception{
        return success(excelApplication.uploadExcel(uploadFile.getInputStream()));
    }
}
