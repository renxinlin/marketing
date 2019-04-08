package com.jgw.supercodeplatform.marketing.service.integral;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分记录
 */
@Service
public class IntegralRecordService  extends AbstractPageService<IntegralRecord > {
    private Logger logger  = LoggerFactory.getLogger(IntegralRecordService.class);

    @Autowired
    private IntegralRecordMapperExt recordMapper;

    @Override
    protected List<IntegralRecord> searchResult(IntegralRecord integralRecord) throws Exception {
        List<IntegralRecord> list=recordMapper.list(integralRecord);
        return list;
    }


    @Override
    protected int count(IntegralRecord integralRecord) throws Exception {
        return recordMapper.count(integralRecord);
    }




}
