package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointPageViewTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointPageViewTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 16:27
 */
@Service
public class BuryPointPageViewTcService {
    @Autowired
    private BuryPointPageViewTcMapper buryPointPageViewTcMapper;

    public void buryPointPageVisitTc(String device, H5LoginVO user){
        BuryPointPageViewTc buryPointPageViewTc =new BuryPointPageViewTc();
        buryPointPageViewTc.setCreateUser(user.getMemberName());
        buryPointPageViewTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointPageViewTc.setCreateDate(new Date());
        buryPointPageViewTc.setOrganizationId(user.getOrganizationId());
        buryPointPageViewTc.setOrganizationName(user.getOrganizationName());
        buryPointPageViewTc.setDevice(device);
        buryPointPageViewTc.setMemberType(user.getMemberType());
        try {
            buryPointPageViewTcMapper.insert(buryPointPageViewTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
