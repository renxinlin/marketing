package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralRecordParam;
import com.jgw.supercodeplatform.marketing.dto.integral.JwtUser;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分记录controller
 *
 */
@RestController
@RequestMapping("/marketing/integral/record")
@Api(tags = "积分记录")
public class IntegralRecordController extends CommonUtil {
    @Autowired
    private  IntegralRecordService integralRecordService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *  积分记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "积分记录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralRecordParam>>> list(IntegralRecord integralRecord) throws Exception {
        RestResult<AbstractPageService.PageResults<List<IntegralRecordParam>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRecordParam>>>();
        String organizationId = getOrganizationId();
        // 获取组织id
        if(StringUtils.isBlank(organizationId)){
            restResult.setState(500);
            restResult.setMsg("组织id获取失败");
            restResult.setResults(null);
            return restResult;
        }
        integralRecord.setOrganizationId(organizationId);
        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(integralRecord);
        restResult.setState(200);
        restResult.setMsg("success");
        List<IntegralRecord> list = pages.getList();
        List<IntegralRecordParam> listVO = new ArrayList<>();
        for (IntegralRecord ir : list){
            listVO.add(modelMapper.map(ir,IntegralRecordParam.class));
        }
        modelMapper.map(list,List.class);
        AbstractPageService.PageResults<List<IntegralRecordParam>> pagesVO = new  AbstractPageService.PageResults<List<IntegralRecordParam>>(null,pages.getPagination());
        pagesVO.setList(listVO);
        pagesVO.setOther(pages.getOther());
        restResult.setResults(pagesVO);
        return restResult;
    }




}
