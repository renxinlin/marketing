package com.jgw.supercodeplatform.marketing.service.weixin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWxMerchantsParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;

@Service
public class MarketingWxMerchantsService {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private MarketingWxMerchantsMapper dao;
	
    @Value("${weixin.certificate.path}")
    private String certificateUrl;
    
	public RestResult<MarketingWxMerchants> get() throws SuperCodeException {
		RestResult<MarketingWxMerchants> restResult=new RestResult<MarketingWxMerchants>();
		String organizationId=commonUtil.getOrganizationId();
		MarketingWxMerchants merchants=dao.get(organizationId);
		if (null==merchants) {
			restResult.setResults(new MarketingWxMerchants());
		}else {
			restResult.setResults(merchants);
		}
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}

	public int addWxMerchants(MarketingWxMerchantsParam marketingWxMerchantsParam) throws SuperCodeException{
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();
		marketingWxMerchantsParam.setOrganizatioIdlName(organizationName);
		marketingWxMerchantsParam.setOrganizationId(organizationId);
		return dao.addWxMerchants(marketingWxMerchantsParam);
	}

	public int updateWxMerchants(MarketingWxMerchantsParam marketingWxMerchantsParam){
		return dao.updateWxMerchants(marketingWxMerchantsParam);
	}

	public MarketingWxMerchants selectByOrganizationId(String organizationId) {
		return dao.selectByOrganizationId(organizationId);
	}
	
	public MarketingWxMerchants get(String organizationId){
		return dao.get(organizationId);
	}
    
	/**
	 * 上传文件
	 * @param file
	 * @return 
	 * @throws IOException
	 * @throws SuperCodeException
	 */
	public String uploadFile(MultipartFile file) throws IOException, SuperCodeException {
		InputStream inputstream=file.getInputStream();
		String name=file.getOriginalFilename();
		int suffixlen=name.lastIndexOf(".");
		String suffix=name.substring(suffixlen+1, name.length());
		String prefix = commonUtil.getUUID();
		FileOutputStream fileOutputStream=null;
		String newName=prefix+"."+suffix;
		try {
			String wholeFilePath=certificateUrl+File.separator+commonUtil.getOrganizationId();
			File dir=new File(wholeFilePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String wholeName=wholeFilePath+File.separator+newName;
			
			fileOutputStream=new FileOutputStream(wholeName);
			byte[]buf=new byte[1024];
			int length=0;
			while((length=inputstream.read(buf, 0, length))>0) {
				fileOutputStream.write(buf, 0, length);
				fileOutputStream.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (null!=inputstream) {
				inputstream.close();
			}
			
			if (null!=fileOutputStream) {
				fileOutputStream.close();
			}
		}
		return newName;
	}
	


}
