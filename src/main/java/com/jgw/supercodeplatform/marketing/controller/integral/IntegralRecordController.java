package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralRecordParam;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public RestResult<AbstractPageService.PageResults<List<IntegralRecordParam>>> list(DaoSearchWithOrganizationIdParam integralRecord) throws Exception {
        RestResult<AbstractPageService.PageResults<List<IntegralRecordParam>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRecordParam>>>();
        String organizationId = getOrganizationId();

        IntegralRecord record = modelMapper.map(integralRecord, IntegralRecord.class);
        record.setOrganizationId(organizationId);
        record.setMemberType(MemberTypeEnums.VIP.getType());
        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(record);
        restResult.setState(200);
        restResult.setMsg("success");
        List<IntegralRecord> list = pages.getList();
        List<IntegralRecordParam> listVO = new ArrayList<>();
        for (IntegralRecord ir : list){
            listVO.add(modelMapper.map(ir,IntegralRecordParam.class));
        }
        AbstractPageService.PageResults<List<IntegralRecordParam>> pagesVO = new  AbstractPageService.PageResults<List<IntegralRecordParam>>(null,pages.getPagination());
        pagesVO.setList(listVO);
        pagesVO.setOther(pages.getOther());
        restResult.setResults(pagesVO);
        return restResult;
    }




}
