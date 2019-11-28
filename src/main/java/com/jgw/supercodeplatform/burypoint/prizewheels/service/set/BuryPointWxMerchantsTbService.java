package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.wx.BuryPointWxMerchantsTbDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointWxMerchantsTbMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointWxMerchantsTb;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 16:10
 */
@Service
public class BuryPointWxMerchantsTbService {
    @Autowired
    private BuryPointWxMerchantsTbMapper buryPointWxMerchantsTbMapper;

    @Autowired
    private CommonUtil commonUtil;

    public void buryPointWxMerchantsTb(BuryPointWxMerchantsTbDto buryPointWxMerchantsTbDto){
        BuryPointWxMerchantsTb buryPointWxMerchantsTb=new BuryPointWxMerchantsTb();
        buryPointWxMerchantsTb.setCreateUser(commonUtil.getUserLoginCache().getUserName());
        buryPointWxMerchantsTb.setCreateUserId(commonUtil.getUserLoginCache().getUserId());
        buryPointWxMerchantsTb.setCreateDate(new Date());
        buryPointWxMerchantsTb.setOrganizationId(commonUtil.getOrganizationId());
        buryPointWxMerchantsTb.setOrganizationName(commonUtil.getOrganizationName());
        buryPointWxMerchantsTb.setWxPicture(buryPointWxMerchantsTbDto.getWxPicture());
        buryPointWxMerchantsTb.setMobile(buryPointWxMerchantsTbDto.getMobile());
        buryPointWxMerchantsTb.setMobileModel(buryPointWxMerchantsTbDto.getMobileModel());
        buryPointWxMerchantsTb.setSystemModel(buryPointWxMerchantsTbDto.getSystemModel());
        buryPointWxMerchantsTb.setBrowser(buryPointWxMerchantsTbDto.getBrowser());
        buryPointWxMerchantsTb.setBrowserModel(buryPointWxMerchantsTbDto.getBrowserModel());
        try {
            buryPointWxMerchantsTbMapper.insert(buryPointWxMerchantsTb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
