package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.RewardSelectType;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.SalerRecordTransfer;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;


import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import    com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.SalerRecordMapper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author renxinlin
 * @since 2019-09-02
 */
@Service
public class SalerRecordService extends SalerCommonService<SalerRecordMapper, SalerRecord> {
   private Calendar calendar = Calendar.getInstance(); //得到日历


    public AbstractPageService.PageResults<List<SalerRecord>>  selectPage(DaoSearch daoSearch) {
        IPage<SalerRecord> salerRecordIPage = baseMapper.selectPage(SalerRecordTransfer.getPage(daoSearch)
                , SalerRecordTransfer.getPageParam(daoSearch, commonUtil.getOrganizationId()));
        return SalerRecordTransfer.toPageResult(salerRecordIPage);
    }

    public List<SalerRecord>  seletLastThreeMonth(int type,H5LoginVO user) {
        Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()),"组织Id不存在");
        Asserts.check(user.getMemberId() != null,"用户Id不存在");
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -3);  //设置为前3月

         List<SalerRecord> records = baseMapper.selectList(query()
                .eq("OrganizationId", user.getOrganizationId())
                .eq("SalerId", user.getMemberId())
                .ge("CreateDate", calendar.getTime())
                .lt(type == RewardSelectType.consume, "IntegralNum", 0)
                .gt(type == RewardSelectType.reward, "IntegralNum", 0)
                .getWrapper());
        if(CollectionUtils.isEmpty(records)){
            return new ArrayList<>();
        }
         return records;
    }
}
