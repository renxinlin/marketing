package com.jgw.supercodeplatform.burypoint.prizewheels.service;

import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointTemplateTbcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointTemplateTbc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 15:30
 */
@Service
public class BuryPointTemplateTbcService {
    @Autowired
    private BuryPointTemplateTbcMapper buryPointTemplateTbcMapper;

    public void buryPointTemplateTbc(String templateId, H5LoginVO user){
        BuryPointTemplateTbc buryPointTemplateTbc=new BuryPointTemplateTbc();
        buryPointTemplateTbc.setCreateUser(user.getMemberName());
        buryPointTemplateTbc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointTemplateTbc.setCreateDate(new Date());
        buryPointTemplateTbc.setOrganizationId(user.getOrganizationId());
        buryPointTemplateTbc.setOrganizationName(user.getOrganizationName());
        buryPointTemplateTbc.setTemplateId(templateId);
        try{
            buryPointTemplateTbcMapper.insert(buryPointTemplateTbc);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
