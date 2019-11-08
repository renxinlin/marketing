package com.jgw.supercodeplatform.prizewheels.interfaces;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.prizewheels.application.service.WheelsPublishAppication;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CdkTemplate;
import com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl.CdkEventSubscriberImplV2;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.PrizeWheelsOrderPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ActivityStatus;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.DaoSearchWithPrizeWheelsIdDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("marketing/prizeWheels")
@Api(value = "", tags = "大转盘")
public class WheelsController extends SalerCommonController {
    private static Logger logger = LoggerFactory.getLogger(WheelsController.class);
    @Autowired
    private WheelsPublishAppication appication;

    @Autowired
    private CdkEventSubscriberImplV2 cdkEventSubscriberImplV2;

    @Value("")
    private String cdkKey;

    @Value("{\"userName\":\"姓名\",\"mobile\":\"手机号\", \"rewardName\":\"奖项名称\",\"createTime\":\"领奖时间\"}")
    private String EXCEL_FIELD_MAP;

    @Value("{\"organizationId\":\"组织ID\",\"organizationName\":\"组织名称\", \"receiverName\":\"收货人\",\"mobile\":\"用户手机\",\"receiverMobile\":\"收货手机\",\"address\":\"用户地址\",\"content\":\"物品\",\"createDate\":\"下单时间\"}")
    private String EXCEL_ORDER_FIELD_MAP;
    @ResponseBody
    @PostMapping("/add")
    @ApiOperation(value = "添加", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult add(@Valid  @RequestBody WheelsDto wheelsDto)   {
        appication.publish(wheelsDto);
        return success();
    }

    @ResponseBody
    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@Valid  @RequestBody WheelsUpdateDto wheelsDto)   {
        appication.update(wheelsDto);
        return success();
    }

    @ResponseBody
    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<WheelsDetailsVo> detail(@RequestParam("id") Long id)   {
        WheelsDetailsVo wheelsDetailsVo = appication.getWheelsDetails(id);
        return success(wheelsDetailsVo);
    }

    @ResponseBody
    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult delete(@RequestParam("id") Long id)   {
        appication.deletePrizeWheelsById(id);
        return success();
    }


    @ResponseBody
    @PostMapping("/changeStatus")
    @ApiOperation(value = "活动停用启用", notes = "活动状态(1、表示上架进展，0 表示下架)")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult status(@Valid  @RequestBody ActivityStatus activityStatus)   {

        appication.upadteStatus(activityStatus);
        return success();
    }


    @ResponseBody
    @GetMapping("/record")
    @ApiOperation(value = "参与记录", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<WheelsRecordPojo>> > record(DaoSearchWithPrizeWheelsIdDto daoSearch)   {
        return success(appication.records(daoSearch));
    }


    @ResponseBody
    @GetMapping("/pageOrder")
    @ApiOperation(value = "订单分页")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<PrizeWheelsOrderPojo>>> orderPage(DaoSearchWithPrizeWheelsIdDto daoSearch){
        return success(appication.orderRecords(daoSearch));
    }



    @GetMapping("/export")
    @ApiOperation(value = "导出参与记录",notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void exportRecords(DaoSearchWithPrizeWheelsIdDto daoSearch, HttpServletResponse response) throws SuperCodeException {
        //导出十万条
        daoSearch.setCurrent(1);
        daoSearch.setPageSize(100000);
        // step-1 查询记录
        AbstractPageService.PageResults<List<WheelsRecordPojo>> pageResults=appication.records(daoSearch);
        // step-2 获取记录
        List<WheelsRecordPojo> list=pageResults.getList();
        //导出
        Map<String,String> filedMap;
        try {
            filedMap= JsonToMapUtil.toMap(EXCEL_FIELD_MAP);
        } catch (Exception e) {
            logger.error("{desc：记录表头解析异常"+e.getMessage()+"}");
            throw new SuperCodeException("表头解析异常",500);
        }
        ExcelUtils.listToExcel(list, filedMap, "参与记录",response);
    }





    @GetMapping("/exportOrder")
    @ApiOperation(value = "订单导出")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void orderExport(DaoSearchWithPrizeWheelsIdDto daoSearch, HttpServletResponse response) throws SuperCodeException {
        //导出十万条
        daoSearch.setCurrent(1);
        daoSearch.setPageSize(100000);
        // step-1 查询记录
        AbstractPageService.PageResults<List<PrizeWheelsOrderPojo>> pageResults=appication.orderRecords(daoSearch);
        // step-2 获取记录
        List<PrizeWheelsOrderPojo> list=pageResults.getList();
        //导出
        Map<String,String> filedMap;
        try {
            filedMap= JsonToMapUtil.toMap(EXCEL_ORDER_FIELD_MAP);
        } catch (Exception e) {
            logger.error("{desc：记录表头解析异常"+e.getMessage()+"}");
            throw new SuperCodeException("表头解析异常",500);
        }
        ExcelUtils.listToExcel(list, filedMap, "订单记录",response);
    }

    /**
     * @author fangshiping
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("cdktemplate/download")
    @ApiOperation(value = "下载模板",notes = "")
    public void down(HttpServletResponse response) throws IOException {
        // 根据cdkkey去七牛云读取文件
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=cdktemplate.xls");

        // 读取excel流
        InputStream in = cdkEventSubscriberImplV2.downExcelStream(CdkTemplate.URL);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            byte[] buf=new byte[1024];
            int len=0;
            while((len=in.read(buf))!=-1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null!=in ) {
                in.close();
            }
            outputStream.close();
        }
    }
}
