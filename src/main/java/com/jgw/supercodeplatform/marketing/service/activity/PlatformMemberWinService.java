package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dto.platform.JoinResultPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.vo.platform.JoinPrizeRecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformMemberWinService extends AbstractPageService<JoinResultPage> {

    @Autowired
    private MarketingMembersWinRecordMapper marketingMembersWinRecordMapper;

    @Override
    protected List<MarketingMembersWinRecord> searchResult(JoinResultPage searchParams) throws Exception {
        searchParams.setStartNumber((searchParams.getCurrent()-1)*searchParams.getPageSize());
        return marketingMembersWinRecordMapper.listWinRecord(searchParams);
    }

    @Override
    protected int count(JoinResultPage searchParams) throws Exception {
        return marketingMembersWinRecordMapper.countWinRecord(searchParams);
    }

    public PageResults<List<JoinPrizeRecordVo>> listJoinPirzeRecord(JoinResultPage joinResultPage) throws Exception {
        PageResults<List<MarketingMembersWinRecord>> recordPage = listSearchViewLike(joinResultPage);
        List<JoinPrizeRecordVo> joinPrizeRecordVoList = recordPage.getList().stream().map(record -> {
            JoinPrizeRecordVo joinPrizeRecordVo = new JoinPrizeRecordVo();
            BeanUtils.copyProperties(record, joinPrizeRecordVo);
            if (record.getWinningAmount() != null && record.getWinningAmount() > 0) {
                joinPrizeRecordVo.setWinningResult("已中奖");
            } else {
                joinPrizeRecordVo.setWinningResult("未中奖");
            }
            return joinPrizeRecordVo;
        }).collect(Collectors.toList());
        PageResults<List<JoinPrizeRecordVo>> joinRecordPage = new PageResults<>();
        joinRecordPage.setPagination(recordPage.getPagination());
        joinRecordPage.setList(joinPrizeRecordVoList);
        return joinRecordPage;
    }

}
