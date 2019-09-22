package com.jgw.supercodeplatform.marketing.service.activity;

import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dto.platform.ActivityDataParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.vo.platform.ActivityOrganizationDataVo;
import com.jgw.supercodeplatform.marketing.vo.platform.DayActivityJoinQuantityVo;
import com.jgw.supercodeplatform.marketing.vo.platform.ScanCodeDataVo;
import com.jgw.supercodeplatform.marketing.vo.platform.WinningPrizeDataVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlatformStatisticsService {

    private final static long ONE_DAY_MILLS = 24 * 60 * 60 * 1000;

    @Autowired
    private CodeEsService codeEsService;
    @Autowired
    private MarketingMembersWinRecordMapper marketingMembersWinRecordMapper;
    @Autowired
    private MarketingMembersMapper marketingMembersMapper;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Value("${rest.codemanager.url}")
    private String restCodemanagerUrl;
    /**
     * 扫码率
     * @param activityDataParam
     * @return
     */
    public List<PieChartVo> scanCodeRate(@Valid ActivityDataParam activityDataParam) {
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        String startDateStr = DateFormatUtils.format(activityDataParam.getStartDate(), "yyyy-MM-dd");
        String endDateStr = DateFormatUtils.format(activityDataParam.getEndDate(), "yyyy-MM-dd");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("start", startDateStr);
        paramMap.put("end", endDateStr);
        long produceCodeNum = 1000000; //暂时假定为一百万个
        try {
            ResponseEntity<String> responseEntity = restTemplateUtil.getRequestAndReturnJosn(restCodemanagerUrl+ CommonConstants.CODE_GETCODETOTAL,paramMap,null);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                produceCodeNum= Long.parseLong(responseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("获取指定时间内生码数量出错", e);
        }
        PieChartVo produceCodeVo = new PieChartVo("生码量", produceCodeNum);
        //扫码量
        long scanCodeNum = codeEsService.countPlatformScanCodeRecordByTime(startTime, endTime, null);
        PieChartVo scanCodeVo = new PieChartVo("扫码量", scanCodeNum);
        return Lists.newArrayList(produceCodeVo, scanCodeVo);
    }

    /**
     * 中奖率
     * @param activityDataParam
     * @return
     */
    public List<PieChartVo> winningPrize(ActivityDataParam activityDataParam){
        Date startTime = activityDataParam.getStartDate();
        Date endTime = new Date(activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS);
        long activityJoinNum = marketingMembersWinRecordMapper.countPlatformTotal(startTime,endTime);
        PieChartVo activityJoinVo = new PieChartVo("活动参与量", activityJoinNum);
        long winningPrizeNum = marketingMembersWinRecordMapper.countPlatformWining(startTime,endTime);
        PieChartVo winningPrizeVo = new PieChartVo("活动中奖量", winningPrizeNum);
        return Lists.newArrayList(activityJoinVo, winningPrizeVo);
    }

    /**
     * 活动参与率
     * @param activityDataParam
     * @return
     */
    public List<PieChartVo> activityJoin(@Valid ActivityDataParam activityDataParam){
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        long scanCodeTotalNum = codeEsService.countPlatformScanCodeRecordByTime(startTime, endTime, null);
        PieChartVo scanCodeTotalVo = new PieChartVo("扫码量", scanCodeTotalNum);
        long joinNum = codeEsService.countPlatformScanCodeRecordByTime(startTime, endTime, 1);
        PieChartVo joinVo = new PieChartVo("参与量", joinNum);
        return Lists.newArrayList(scanCodeTotalVo, joinVo);
    }

    /**
     * 获取扫码企业排行榜，前七位
     * @param activityDataParam
     * @return
     */
    public List<ActivityOrganizationDataVo> activityOrganization(ActivityDataParam activityDataParam) {
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        List<ActivityOrganizationDataVo> activityOrganizationDataVoList = codeEsService.scanOrganizationList(startTime, endTime);
        return activityOrganizationDataVoList;
    }

    /**
     *
     * @param activityDataParam
     * @param status 1为参与活动的，0为扫了码但是拒绝活动的,null查询0和1的情况综合
     * @return
     */
    public DayActivityJoinQuantityVo statiticsDayActivity(ActivityDataParam activityDataParam, Integer status){
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        List<PieChartVo> pieVoList = codeEsService.dayActivityStatistic(startTime, endTime, status);
        SortedSet<PieChartVo> dayPieSet = new TreeSet<>();
        dayPieSet.addAll(pieVoList);
        dayPieSet.addAll(DateUtil.dayFmt(activityDataParam.getStartDate(), activityDataParam.getEndDate()));
        DayActivityJoinQuantityVo dayActivityJoinQuantityVo = new DayActivityJoinQuantityVo();
        List<String> nameList = dayPieSet.stream().map(dayPie -> dayPie.getName()).collect(Collectors.toList());
        List<Long> valueList = dayPieSet.stream().map(dayPie -> dayPie.getValue()).collect(Collectors.toList());
        dayActivityJoinQuantityVo.setData(nameList);
        dayActivityJoinQuantityVo.setValue(valueList);
        long max = valueList.stream().max((v1, v2) -> v1.compareTo(v2)).get();
        long min = valueList.stream().min((v1, v2) -> v1.compareTo(v2)).get();
        dayActivityJoinQuantityVo.setMaxValue(max);
        dayActivityJoinQuantityVo.setMinValue(min);
        return dayActivityJoinQuantityVo;
    }

    /**
     * 扫码活跃
     * @param activityDataParam
     * @return
     */
    public List<PieChartVo> scanCodeActMember(ActivityDataParam activityDataParam) {
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        long allNum = marketingMembersMapper.countAllMemberNum();
        PieChartVo allPie = new PieChartVo("总会员", allNum);
        long actNum = codeEsService.countPlatformScanCodeUserByTime(startTime, endTime, 1);
        PieChartVo actPie = new PieChartVo("活跃会员", actNum);
        return Lists.newArrayList(allPie, actPie);
    }

}
