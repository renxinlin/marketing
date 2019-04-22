package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralOrderMapperExt;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralOrderPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralOrderService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 会员积分记录列表
 *
 */
@RestController
@RequestMapping("/marketing/h5/order")
@Api(tags = "H5会员订单列表")
public class H5OrderRecordController {
    @Autowired
    private IntegralOrderService service;

    @Autowired
    private ModelMapper modelMapper;
    /**
     *  会员订单记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/memberList",method = RequestMethod.GET)
    @ApiOperation(value = "会员订单记录列表", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "积分类型|null所有,0奖励,1消耗",name="integralType"),
            @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult<AbstractPageService.PageResults<List<IntegralOrderPageParam>>> memberList(DaoSearchWithOrganizationIdParam search, @ApiIgnore H5LoginVO jwtuser) throws Exception {
        // 查询参数
        IntegralOrder searchParams = modelMapper.map(search,IntegralOrder.class);
        searchParams.setMemberId(jwtuser.getMemberId());
        AbstractPageService.PageResults< List<IntegralOrderPageParam>> objectPageResults = service.listSearchViewLike(searchParams);
        return RestResult.success("success",objectPageResults);
    }
}
