package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.pv.BuryPointPageVisitTcDto;
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

    public void buryPointPageVisitTc(BuryPointPageVisitTcDto buryPointPageVisitTcDto, H5LoginVO user){
        BuryPointPageViewTc buryPointPageViewTc =new BuryPointPageViewTc();
        buryPointPageViewTc.setCreateUser(user.getMemberName());
        buryPointPageViewTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointPageViewTc.setCreateDate(new Date());
        buryPointPageViewTc.setOrganizationId(user.getOrganizationId());
        buryPointPageViewTc.setOrganizationName(user.getOrganizationName());
        buryPointPageViewTc.setDevice(buryPointPageVisitTcDto.getDevice());
        buryPointPageViewTc.setMemberType(user.getMemberType());
        buryPointPageViewTc.setMobile(buryPointPageVisitTcDto.getMobile());
        buryPointPageViewTc.setMobileModel(buryPointPageVisitTcDto.getMobileModel());
        buryPointPageViewTc.setSystemModel(buryPointPageVisitTcDto.getSystemModel());
        buryPointPageViewTc.setBrowser(buryPointPageVisitTcDto.getBrowser());
        buryPointPageViewTc.setBrowserModel(buryPointPageVisitTcDto.getBrowserModel());
        try {
            buryPointPageViewTcMapper.insert(buryPointPageViewTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
