package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointTemplateTbMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointTemplateTb;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
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

    public void buryPointTemplateTb(String templateId, H5LoginVO user){
        BuryPointTemplateTb buryPointTemplateTb =new BuryPointTemplateTb();
        buryPointTemplateTb.setCreateUser(user.getMemberName());
        buryPointTemplateTb.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointTemplateTb.setCreateDate(new Date());
        buryPointTemplateTb.setOrganizationId(user.getOrganizationId());
        buryPointTemplateTb.setOrganizationName(user.getOrganizationName());
        buryPointTemplateTb.setTemplateId(templateId);
        try{
            buryPointTemplateTbMapper.insert(buryPointTemplateTb);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
