package com.jgw.supercodeplatform.marketing.service.weixin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWxMerchantsParam;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import com.jgw.supercodeplatform.marketing.service.weixin.constants.BelongToJgwConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class MarketingWxMerchantsService {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private MarketingWxMerchantsMapper dao;

	@Autowired
	private GlobalRamCache globalRamCache;
	
    @Value("${weixin.certificate.path}")
    private String certificateUrl;

    @Autowired
	private MarketingWxMerchantsExtMapper marketingWxMerchantsExtMapper;
    
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
		globalRamCache.delWXMerchants(marketingWxMerchantsParam.getOrganizationId());
		int er = dao.updateWxMerchants(marketingWxMerchantsParam);
		return er;
	}

	public MarketingWxMerchants selectByOrganizationId(String organizationId) {
		MarketingWxMerchants mWxMerchants=dao.selectByOrganizationId(organizationId);
		return mWxMerchants;
	}
	
	public MarketingWxMerchants get(String organizationId){
		MarketingWxMerchants mWxMerchants=dao.get(organizationId);
		if (null==mWxMerchants) {
			mWxMerchants=dao.selectDefault();
		}
		return mWxMerchants;
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
			while((length=inputstream.read(buf))>0) {
				fileOutputStream.write(buf, 0, length);
			}
			fileOutputStream.flush();

			// 写入mysql
			UpdateWrapper<MarketingWxMerchantsExt> query = new UpdateWrapper<>();
			query.eq("OrganizationId",commonUtil.getOrganizationId());
			marketingWxMerchantsExtMapper.delete(query);
			MarketingWxMerchantsExt marketingWxMerchantsExt = new MarketingWxMerchantsExt();
			marketingWxMerchantsExt.setOrganizationId(commonUtil.getOrganizationId());
			marketingWxMerchantsExt.setBelongToJgw(BelongToJgwConstants.NO);
			marketingWxMerchantsExt.setOrganizatioIdlName(commonUtil.getOrganizationName());
			marketingWxMerchantsExt.setCertificateInfo(FileCopyUtils.copyToByteArray(file.getInputStream()));
			marketingWxMerchantsExt.setFilename(newName);
			marketingWxMerchantsExtMapper.insert(marketingWxMerchantsExt);
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
	
	public void setUseType(Byte useType){
		String organizationId = commonUtil.getOrganizationId();
		String organizationName = commonUtil.getOrganizationName();
		MarketingWxMerchants marketingWxMerchants = dao.selectByOrganizationId(organizationId);
		if (marketingWxMerchants == null){
			dao.insertNoMerchant(organizationId, organizationName);
		} else {
			dao.updateNoMerchant(organizationId, useType);
		}
	}

	public MarketingWxMerchants getJgw(){
		return dao.getJgw();
	}


}
