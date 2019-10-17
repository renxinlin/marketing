package com.jgw.supercodeplatform.burypoint.prizewheels.service;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointOuterChainTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointOuterChainTc;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fangshiping
 * @date 2019/10/16 13:18
 */
@Service
public class BuryPointOuterChainTcService {
    private Logger logger = LoggerFactory.getLogger(BuryPointOuterChainTcService.class);
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuryPointOuterChainTcMapper buryPointOuterChainTcMapper;

    public void buryPointOuterChainTc(BuryPointOuterChainTcDto buryPointOuterChainTcDto){
        Publisher publisher=new Publisher();
        publisher.initUserInfoWhenFirstPublish(commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        BuryPointOuterChainTc buryPointOuterChainTc=modelMapper.map(publisher,BuryPointOuterChainTc.class);
        buryPointOuterChainTc.setThirdUrl(buryPointOuterChainTcDto.getThirdUrl());
        buryPointOuterChainTc.setActivityId(buryPointOuterChainTcDto.getActivityId());
        buryPointOuterChainTc.setOrganizationId(commonUtil.getOrganizationId());
        buryPointOuterChainTc.setOrganizationName(commonUtil.getOrganizationName());
        try {
            logger.info("插入c端大转盘链接埋点数据出错："+buryPointOuterChainTc.toString());
            buryPointOuterChainTcMapper.insert(buryPointOuterChainTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("成功插入c端大转盘链接埋点数据："+buryPointOuterChainTc.toString());

    }

}
