package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.controller.common.CommonController;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.vo.DiagramRemebermeVo;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记住默认选择
 */
@RestController
@RequestMapping("/marketing/rememberMe")
@Api(tags = "记住选择")
public class RememberMeController extends CommonUtil {

    @Autowired
    private CodeEsService service;
    @GetMapping("get")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
    })
    public RestResult remerberMe(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountCache userLoginCache = getUserLoginCache();
        String organizationId = getOrganizationId();
        String userId = userLoginCache.getUserId();
        DiagramRemebermeVo toEsVo = new DiagramRemebermeVo(organizationId,userId,null);
        DiagramRemebermeVo toWebVo = service.searchDiagramRemberMeInfo(toEsVo);
        if(StringUtils.isBlank(toWebVo.getChoose())){
            // 默认一周
            toWebVo.setOrganizationId(null);
            toWebVo.setUserId(null);
            toWebVo.setChoose(QueryEnum.WEEK.getStatus());
        }
        return RestResult.success("success",toWebVo);

    }


    @GetMapping("put")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
            @ApiImplicitParam(paramType = "query", value = "1，2，3，4，5，6", name = "choose")
    })
    public RestResult doRemerberMe(HttpServletRequest request, HttpServletResponse response,String choose) throws Exception, JsonProcessingException {
        String organizationId = getOrganizationId();
        String userId = getUserLoginCache().getUserId();
        DiagramRemebermeVo toEsVo = new DiagramRemebermeVo(organizationId,userId,choose);
        service.indexDiagramRemberMeInfo(toEsVo);
        return RestResult.success();

    }


}
