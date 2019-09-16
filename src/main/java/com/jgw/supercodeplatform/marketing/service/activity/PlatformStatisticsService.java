package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dto.platform.ActivityDataParam;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.vo.platform.ScanCodeDataVo;
import com.jgw.supercodeplatform.marketing.vo.platform.WinningPrizeDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class PlatformStatisticsService {

    private final static long ONE_DAY_MILLS = 24 * 60 * 60 * 1000;

    @Autowired
    private CodeEsService codeEsService;
    @Autowired
    private MarketingMembersWinRecordMapper marketingMembersWinRecordMapper;

    /**
     * 扫码率
     * @param activityDataParam
     * @return
     */
    public ScanCodeDataVo scanCodeRate(@Valid ActivityDataParam activityDataParam) {
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        //TODO 去码平台获取指定时间段内生码数量
        long produceCodeNum = 1000000; //暂时假定为一百万个
        //扫码量
        long scanCodeNum = codeEsService.countPlatformScanCodeRecordByTime(startTime, endTime, null);
        //计算扫码率
        String scanCodeRate = new BigDecimal(scanCodeNum * 100).divide(new BigDecimal(produceCodeNum), 2, BigDecimal.ROUND_HALF_UP).toString() + "%";
        return new ScanCodeDataVo(produceCodeNum, scanCodeNum, scanCodeRate);
    }

    /**
     * 中奖率
     * @param activityDataParam
     * @return
     */
    public WinningPrizeDataVo winningPrize(ActivityDataParam activityDataParam){
        Date startTime = activityDataParam.getStartDate();
        Date endTime = new Date(activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS);
        long activityJoinNum = marketingMembersWinRecordMapper.countPlatformTotal(startTime,endTime);
        long winningPrizeNum = marketingMembersWinRecordMapper.countPlatformWining(startTime,endTime);
        String winningPrizeRate = new BigDecimal(winningPrizeNum * 100).divide(new BigDecimal(activityJoinNum), 2, BigDecimal.ROUND_HALF_UP).toString() + "%";
        return new WinningPrizeDataVo(activityJoinNum, winningPrizeNum, winningPrizeRate);
    }

}
