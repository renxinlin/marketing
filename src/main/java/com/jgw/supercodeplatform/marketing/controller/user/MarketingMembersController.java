package com.jgw.supercodeplatform.marketing.controller.user;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonObject;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonProperty;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/marketing/members")
@Api(tags = "会员管理")
public class MarketingMembersController extends CommonUtil {

    @Autowired
    private MarketingMembersService marketingMembersService;


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiOperation(value = "会员列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<Map<String, Object>>>> memberList(@RequestBody MarketingMembersListParam param) throws Exception {
        return new RestResult(200, "success", marketingMembersService.listSearchViewLike(param));
    }


    @RequestMapping(value = "/getMenberByUserId",method = RequestMethod.GET)
    @ApiOperation(value = "根据会员id获取会员详细信息", notes = "返回会员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "userId", paramType = "query", defaultValue = "ad156wd15d61a56d1w56d1d1", value = "用户Id,必需", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "02a61bd8703c4b0eb6a6f62fe709b0c6", value = "组织Id,必需", required = true)
    })
    public RestResult<String> getUserMember(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId","organizationId");
        return new RestResult(200, "success",marketingMembersService.getMemberById(params));
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑会员", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> updateMember(@Valid @RequestBody MarketingMembersUpdateParam marketingMembersUpdateParam) throws Exception {
        marketingMembersService.updateMembers(marketingMembersUpdateParam);
        return new RestResult(200, "success",null );
    }




    @RequestMapping(value = "/enable/status", method = RequestMethod.PUT)
    @ApiOperation(value = "启用会员", notes = "是否启用成功")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> enableStatus(
            @ApiJsonObject(name = "enableMemStatus", value = {
                    @ApiJsonProperty(key = "userId", example = "64b379cd47c843458378f479a115c322", description = "用户id,必需"),
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId","organizationId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", params.get("userId").toString());
        map.put("organizationId", params.get("organizationId").toString());
        map.put("state","1");
        marketingMembersService.updateMembersStatus(map);
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/disable/status", method = RequestMethod.PUT)
    @ApiOperation(value = "禁用会员", notes = "是否禁用成功")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> disableStatus(
            @ApiJsonObject(name = "disableMemStatus", value = {
                    @ApiJsonProperty(key = "userId", example = "64b379cd47c843458378f479a115c322", description = "用户id,必需"),
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "userId","organizationId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", params.get("userId").toString());
        map.put("organizationId", params.get("organizationId").toString());
        map.put("state","0");
        marketingMembersService.updateMembersStatus(map);
        return new RestResult(200, "success", null);
    }

    /**
     * 创建二维码
     * @param content
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/qrCode", method = RequestMethod.GET)
    @ApiOperation(value = "生成二维码接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "content", paramType = "query", defaultValue = "http://www.baidu.com", value = "", required = true),
    })
    public  boolean createQrCode(String content,HttpServletResponse response) throws WriterException, IOException,Exception{
        //设置二维码纠错级别ＭＡＰ
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        sb.append("?organizationId=");
        sb.append(getOrganization().getOrganizationId());
        BitMatrix byteMatrix = qrCodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 900, 900, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth-200, matrixWidth-200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++){
            for (int j = 0; j < matrixWidth; j++){
                if (byteMatrix.get(i, j)){
                    graphics.fillRect(i-100, j-100, 1, 1);
                }
            }
        }
        return ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    @RequestMapping(value = "/getOrg",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户登录的组织信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getUserOrg(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        return new RestResult(200, "success",getOrganization());
    }



}
