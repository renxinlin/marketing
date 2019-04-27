package com.jgw.supercodeplatform.marketing.controller.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayConstants.SignType;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/common")
@Api(tags = "公共接口")
public class CommonController extends CommonUtil {
	protected static Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private CommonService service;

    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;
    
    @RequestMapping(value = "/sendPhoneCode",method = RequestMethod.GET)
    @ApiOperation(value = "发送手机验证码", notes = "")
    @ApiImplicitParam(name = "mobile", paramType = "query", defaultValue = "13925121452", value = "手机号", required = true)
    public RestResult<String> sendPhoneCode(@RequestParam String mobile) throws Exception {
        return service.sendPhoneCode(mobile);
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
        BitMatrix byteMatrix = qrCodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 1600, 1600, hintMap);
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
    
    @RequestMapping(value = "/getJssdkInfo",method = RequestMethod.GET)
    @ApiOperation(value = "获取jssdk权限认证信息", notes = "")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "组织id", required = true),
        @ApiImplicitParam(name = "url", paramType = "query", defaultValue = "http://www.baidu.com", value = "签名页面url", required = true)
   })
    public RestResult<Map<String, String>> get(@RequestParam("organizationId")String organizationId,@RequestParam("url")String url) throws Exception {
    	logger.info("签名信息,organizationId="+organizationId+",url="+url);
    	MarketingWxMerchants mWxMerchants=marketingWxMerchantsService.selectByOrganizationId(organizationId);
    	if (null==mWxMerchants) {
           throw new SuperCodeException("无法获取商户公众号信息", 500);
		}
    	String accessToken=service.getAccessTokenByOrgId(mWxMerchants.getMchAppid(), mWxMerchants.getMerchantSecret(), organizationId);
        // TODO 测试
    	HttpClientResult result=HttpRequestUtil.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi");
    	String tickContent=result.getContent();
    	logger.info("获取到tick的数据："+tickContent);
    	
    	String noncestr=WXPayUtil.generateNonceStr();
    	long timestamp=WXPayUtil.getCurrentTimestamp();
    	Map<String, String>sinMap=new HashMap<String, String>();
    	sinMap.put("noncestr", noncestr);
    	sinMap.put("jsapi_ticket", tickContent);
    	sinMap.put("timestamp", timestamp+"");
    	sinMap.put("url", url);
    	
    	String signature=CommonUtil.generateSignature(sinMap,SignType.SHA1);
    	
    	RestResult<Map<String, String>> restResult=new RestResult<Map<String, String>>();
    	restResult.setState(200);
    	Map<String, String> data=new HashMap<String, String>();
    	data.put("noncestr", noncestr);
    	data.put("timestamp", timestamp+"");
    	data.put("appId", mWxMerchants.getMchAppid());
    	data.put("signature", signature);
    	restResult.setResults(data);
    	restResult.setState(200);
    	
        return restResult;
    }
}
