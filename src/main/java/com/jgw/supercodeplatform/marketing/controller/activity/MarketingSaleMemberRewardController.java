package com.jgw.supercodeplatform.marketing.controller.activity;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdAndSetIdParam;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.SalerIntegralRecordParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralRecordParam;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingMembersWinRecordService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 销售员奖励查询导出
 *
 */
@RestController
@RequestMapping("/marketing/saler/winRecord")
@Api(tags = "导购记录")
public class MarketingSaleMemberRewardController  extends CommonUtil {
    // todo 积分记录插入和查询都需要【区分会员和导购以及其他】
    protected static Logger logger = LoggerFactory.getLogger(MarketingMembersWinRecordController.class);
    @Autowired
    private MarketingMembersWinRecordService service;







    // 	@Value("${marketing.winRecord.sheetHead}")
    @Value("{\"customerName\":\"所属机构\",\"salerMobile\":\"销售员手机\",\"salerName\":\"姓名\", \"productName\":\"产品\",\"outerCodeId\":\"码\",\"salerAmount\":\"红包金额\", \"integralReason\":\"参与条件\",\"mobile\":\"会员手机\",\"createDate\":\"参与时间\",\"status\":\"状态\"}")
    private String EXCEL_FIELD_MAP;

    @Autowired
    private IntegralRecordService integralRecordService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *  积分记录列表
     * @param
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "导购参与记录", notes = "")
    @ApiImplicitParams(value= { @ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token") 	})
    public RestResult<AbstractPageService.PageResults<List<SalerIntegralRecordParam>>> list(DaoSearchWithOrganizationIdAndSetIdParam integralRecord) throws Exception {
        RestResult<AbstractPageService.PageResults<List<SalerIntegralRecordParam>>> restResult= new RestResult<AbstractPageService.PageResults<List<SalerIntegralRecordParam>>>();
        String organizationId = getOrganizationId();

        IntegralRecord record = modelMapper.map(integralRecord, IntegralRecord.class);
        // 导购员
        record.setMemberType(MemberTypeEnums.SALER.getType());
        record.setOrganizationId(organizationId);
        record.setActivitySetId(integralRecord.getId());
        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(record);

        // 结果转换
        restResult.setState(200);
        restResult.setMsg("success");
        List<IntegralRecord> list = pages.getList();
        if(CollectionUtils.isEmpty(list)){
            return  restResult;
        }
        List<SalerIntegralRecordParam> listVO = new ArrayList<>();
        for (IntegralRecord ir : list){
            listVO.add(modelMapper.map(ir, SalerIntegralRecordParam.class));
        }
        AbstractPageService.PageResults<List<SalerIntegralRecordParam>> pagesVO = new  AbstractPageService.PageResults<List<SalerIntegralRecordParam>>(null,pages.getPagination());
        pagesVO.setList(listVO);
        pagesVO.setOther(pages.getOther());
        restResult.setResults(pagesVO);
        return restResult;
    }


    @RequestMapping(value ="/export",method = RequestMethod.GET)
    @ApiOperation(value = "导购参与记录导出", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
            ,@ApiImplicitParam(paramType="search",value = "查询条件",name="search")
    })
    public void littleWinRecordOutExcelWithOrganization(String search,Long id) throws SuperCodeException, UnsupportedEncodingException, Exception {

        // step-1: 参数设置
        String organizationId = getOrganizationId();
        IntegralRecord record =  new IntegralRecord();
        // 导购员
        record.setMemberType(MemberTypeEnums.SALER.getType());
        // 组织
        record.setOrganizationId(organizationId);
        record.setActivitySetId(id);

        // 分页
        record.setStartNumber(1);
        record.setPageSize(Integer.MAX_VALUE);
        // 查询条件
        record.setSearch(search);

        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(record);





        // step-2: 获取结果
        List<IntegralRecord> list = pages.getList();
        // 导出
        Map filedMap = null;
        try {
            filedMap = JsonToMapUtil.toMap(EXCEL_FIELD_MAP);
        } catch (Exception e){
            logger.error("{desc:记录表头解析异常" + e.getMessage() + "}");
            throw new SuperCodeException("表头解析异常",500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "中奖记录", response);

    }
}
