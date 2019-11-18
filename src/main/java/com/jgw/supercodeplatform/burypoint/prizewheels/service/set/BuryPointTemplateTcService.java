package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointTemplateTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointTemplateTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 15:30
 */
@Service
public class BuryPointTemplateTcService {
    @Autowired
    private BuryPointTemplateTcMapper buryPointTemplateTcMapper;

    public void buryPointTemplateTc(String templateId, H5LoginVO user){
        BuryPointTemplateTc buryPointTemplateTc =new BuryPointTemplateTc();
        buryPointTemplateTc.setCreateUser(user.getMemberName());
        buryPointTemplateTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointTemplateTc.setCreateDate(new Date());
        buryPointTemplateTc.setOrganizationId(user.getOrganizationId());
        buryPointTemplateTc.setOrganizationName(user.getOrganizationName());
        buryPointTemplateTc.setTemplateId(templateId);
        buryPointTemplateTc.setMemberType(user.getMemberType());
        try{
            buryPointTemplateTcMapper.insert(buryPointTemplateTc);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
