package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointTemplateTbMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointTemplateTb;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 15:30
 */
@Service
public class BuryPointTemplateTbService {
    @Autowired
    private BuryPointTemplateTbMapper buryPointTemplateTbMapper;

    @Autowired
    private CommonUtil commonUtil;

    public void buryPointTemplateTb(String templateId){
        BuryPointTemplateTb buryPointTemplateTb =new BuryPointTemplateTb();
        buryPointTemplateTb.setCreateUser(commonUtil.getUserLoginCache().getUserName());
        buryPointTemplateTb.setCreateUserId(commonUtil.getUserLoginCache().getUserId());
        buryPointTemplateTb.setCreateDate(new Date());
        buryPointTemplateTb.setOrganizationId(commonUtil.getOrganizationId());
        buryPointTemplateTb.setOrganizationName(commonUtil.getOrganizationName());
        buryPointTemplateTb.setTemplateId(templateId);
        try{
            buryPointTemplateTbMapper.insert(buryPointTemplateTb);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
