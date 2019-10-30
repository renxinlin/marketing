package com.jgw.supercodeplatform.marketing.service.weixin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
		UpdateWrapper<MarketingWxMerchants> updateWrapper = Wrappers.<MarketingWxMerchants>update().set("DefaultUse", 0).eq("OrganizationId", organizationId).eq("DefaultUse", 1);
		dao.update(null, updateWrapper);
		marketingWxMerchantsParam.setOrganizatioIdlName(organizationName);
		marketingWxMerchantsParam.setOrganizationId(organizationId);
		globalRamCache.delWXMerchants(marketingWxMerchantsParam.getOrganizationId());
		return dao.addWxMerchants(marketingWxMerchantsParam);
	}

	public int updateWxMerchants(MarketingWxMerchantsParam marketingWxMerchantsParam){
		String organizationId = marketingWxMerchantsParam.getOrganizationId();
		UpdateWrapper<MarketingWxMerchants> updateWrapper = Wrappers.<MarketingWxMerchants>update().set("DefaultUse", 0).eq("OrganizationId", organizationId).eq("DefaultUse", 1);
		dao.update(null, updateWrapper);
		globalRamCache.delWXMerchants(organizationId);
		MarketingWxMerchants marketingWxMerchants = new MarketingWxMerchants();
		BeanUtils.copyProperties(marketingWxMerchantsParam, marketingWxMerchants);
		marketingWxMerchants.setDefaultUse((byte)1);
		UpdateWrapper<MarketingWxMerchants> updateMerchants = Wrappers.<MarketingWxMerchants>update().eq("OrganizationId", organizationId).eq("MchAppid", marketingWxMerchants.getMchAppid());
		int er = dao.update(marketingWxMerchants, updateMerchants);
		return er;
	}

	public MarketingWxMerchants selectByOrganizationId(String organizationId) {
		return get(organizationId);
	}

	public MarketingWxMerchants selectByOrganizationId(String organizationId, String appid) {
		QueryWrapper<MarketingWxMerchants> queryWrapper = Wrappers.<MarketingWxMerchants>query().eq("OrganizationId", organizationId).eq("MchAppid", appid);
		return dao.selectOne(queryWrapper);
	}

	public MarketingWxMerchants get(String organizationId){
		QueryWrapper<MarketingWxMerchants> queryWrapper = Wrappers.<MarketingWxMerchants>query().eq("OrganizationId", organizationId).eq("DefaultUse", 1);
		MarketingWxMerchants mWxMerchants=dao.selectOne(queryWrapper);
		return mWxMerchants;
	}

	private void updateNotDefaultMerchant(String organizationId){
		UpdateWrapper<MarketingWxMerchants> updateWrapper = Wrappers.<MarketingWxMerchants>update().set("DefaultUse", 0).eq("OrganizationId", organizationId).eq("DefaultUse", 1);
		dao.update(null, updateWrapper);
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

	public MarketingWxMerchants getJgw(Long jgwId){
		return dao.getJgw(jgwId);
	}

	public MarketingWxMerchants getDefaultJgw() {
		return dao.getDefaultJgw();
	}

	public MarketingWxMerchants getByAppid(String appid) {
		List<MarketingWxMerchants> merchats = dao.selectList(Wrappers.<MarketingWxMerchants>query().eq("mchAppid", appid));
		if (!CollectionUtils.isEmpty(merchats)) {
			return merchats.get(0);
		}
		return null;
	}

}
