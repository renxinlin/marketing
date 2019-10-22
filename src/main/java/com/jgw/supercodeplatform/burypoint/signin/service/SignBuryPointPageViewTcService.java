package com.jgw.supercodeplatform.burypoint.signin.service;

import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointPageViewTcMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointPageViewTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 16:27
 */
@Service
public class SignBuryPointPageViewTcService {
    @Autowired
    private SignBuryPointPageViewTcMapper signBuryPointPageViewTcMapper;

    public void buryPointPageVisitTc(String device, H5LoginVO user){
        BuryPointPageViewTc buryPointPageViewTc =new BuryPointPageViewTc();
        buryPointPageViewTc.setCreateUser(user.getMemberName());
        buryPointPageViewTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointPageViewTc.setCreateDate(new Date());
        buryPointPageViewTc.setOrganizationId(user.getOrganizationId());
        buryPointPageViewTc.setOrganizationName(user.getOrganizationName());
        buryPointPageViewTc.setDevice(device);
        try {
            signBuryPointPageViewTcMapper.insert(buryPointPageViewTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
