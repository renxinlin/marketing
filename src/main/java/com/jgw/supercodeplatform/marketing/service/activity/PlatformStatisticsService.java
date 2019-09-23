package com.jgw.supercodeplatform.marketing.service.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
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
import com.jgw.supercodeplatform.marketing.vo.platform.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class PlatformStatisticsService {

    private final static long ONE_DAY_MILLS = 24 * 60 * 60 * 1000;
    @Autowired
    private CommonUtil commonUtil;
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

    private final String[] provinces = {"北京", "天津", "上海", "重庆", "河北", "河南", "云南", "辽宁", "黑龙江", "湖南",
            "安徽", "山东", "新疆", "江苏", "浙江", "江西", "湖北", "广西", "甘肃", "山西", "内蒙古", "陕西", "吉林", "福建",
            "贵州", "广东", "青海", "西藏", "四川", "宁夏", "海南", "台湾", "香港", "澳门"};
    /**
     * 扫码率
     * @param activityDataParam
     * @return
     */
    public List<PieChartVo> scanCodeRate(@Valid ActivityDataParam activityDataParam) {
        long startTime = activityDataParam.getStartDate().getTime();
        long endTime = activityDataParam.getEndDate().getTime() + ONE_DAY_MILLS;
        String startDateStr = DateFormatUtils.format(startTime, "yyyy-MM-dd");
        String endDateStr = DateFormatUtils.format(endTime, "yyyy-MM-dd");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("start", startDateStr);
        paramMap.put("end", endDateStr);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("super-token", commonUtil.getSuperToken());
        long produceCodeNum = 0; //暂时假定为一百万个
        try {
            ResponseEntity<String> responseEntity = restTemplateUtil.getRequestAndReturnJosn(restCodemanagerUrl+ CommonConstants.CODE_GETCODETOTAL,paramMap,headMap);
            String resBody = responseEntity.getBody();
            if (responseEntity.getStatusCode().equals(HttpStatus.OK) && StringUtils.isNotBlank(resBody)) {
                JSONObject resJson = JSON.parseObject(resBody);
                if (resJson.getIntValue("state") == HttpStatus.OK.value()) {
                    produceCodeNum = resJson.getLong("results");
                }
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
        long joinNum = marketingMembersWinRecordMapper.countPlatformTotal(new Date(startTime), new Date(endTime));
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
        long actNum = marketingMembersWinRecordMapper.countActUser(new Date(startTime), new Date(endTime));
        PieChartVo actPie = new PieChartVo("活跃会员", actNum);
        return Lists.newArrayList(allPie, actPie);
    }

    /**
     * 会员画像统计
     * @return
     */
    public MemberPortraitDataVo memberPortrait(){
        //性别
        Map<String, Long> sexStatitics = marketingMembersMapper.statisticSex();
        PieChartVo malePieChartVo = new PieChartVo("男",sexStatitics.get("male"));
        PieChartVo femalePieChartVo = new PieChartVo("女", sexStatitics.get("female"));
        PieChartVo otherSexPieChartVo = new PieChartVo("未知", sexStatitics.get("other"));
        List<PieChartVo> sexList = Lists.newArrayList(malePieChartVo, femalePieChartVo, otherSexPieChartVo);
        //年龄
        Map<String, Long> ageStatitics = marketingMembersMapper.statistcAge();
        PieChartVo tenPieChartVo = new PieChartVo("0-10",ageStatitics.get("ten"));
        PieChartVo twentyPieChartVo = new PieChartVo("10-20",ageStatitics.get("twenty"));
        PieChartVo thirtyPieChartVo = new PieChartVo("20-30",ageStatitics.get("thirty"));
        PieChartVo fortyPieChartVo = new PieChartVo("30-40",ageStatitics.get("forty"));
        PieChartVo fiftyPieChartVo = new PieChartVo("40-50",ageStatitics.get("fifty"));
        PieChartVo sixtyPieChartVo = new PieChartVo("50-60",ageStatitics.get("sixty"));
        PieChartVo seventyPieChartVo = new PieChartVo("60-70",ageStatitics.get("seventy"));
        PieChartVo eightyPieChartVo = new PieChartVo("70-80",ageStatitics.get("eighty"));
        PieChartVo ninetyPieChartVo = new PieChartVo("80-90",ageStatitics.get("ninety"));
        PieChartVo hundredPieChartVo = new PieChartVo("90-100",ageStatitics.get("hundred"));
        PieChartVo otherAgePieChartVo = new PieChartVo("未知",ageStatitics.get("other"));
        List<PieChartVo> ageList = Lists.newArrayList(tenPieChartVo, twentyPieChartVo, thirtyPieChartVo,fortyPieChartVo,
                fiftyPieChartVo,sixtyPieChartVo,seventyPieChartVo,eightyPieChartVo,ninetyPieChartVo,hundredPieChartVo,otherAgePieChartVo);
        //扫码来源
        Map<String, Long> browserStatitics = marketingMembersMapper.statisticBrowser();
        PieChartVo wxChartVo = new PieChartVo("微信",ageStatitics.get("wx"));
        PieChartVo zfbPieChartVo = new PieChartVo("支付宝",ageStatitics.get("zzb"));
        PieChartVo ddPieChartVo = new PieChartVo("钉钉",ageStatitics.get("dd"));
        PieChartVo llqPieChartVo = new PieChartVo("浏览器",ageStatitics.get("llq"));
        PieChartVo qqPieChartVo = new PieChartVo("QQ",ageStatitics.get("qq"));
        PieChartVo otherBrowserPieChartVo = new PieChartVo("未知",ageStatitics.get("other"));
        List<PieChartVo> browserLit = Lists.newArrayList(wxChartVo, zfbPieChartVo, ddPieChartVo, llqPieChartVo, qqPieChartVo, otherBrowserPieChartVo);
        MemberPortraitDataVo memberPortraitDataVo = new MemberPortraitDataVo();
        memberPortraitDataVo.setSex(sexList);
        memberPortraitDataVo.setScanSource(browserLit);
        memberPortraitDataVo.setAge(ageList);
        return memberPortraitDataVo;
    }


    public MemberAreaVo memberRegion(){
        Set<PieChartVo> areaPieChartSet = marketingMembersMapper.statisticArea();
        areaPieChartSet.stream().forEach(area -> {
            String name = area.getName();
            if (name.contains("黑龙江") || name.contains("内蒙古")) {
                area.setName(name.substring(0, 3));
            } else {
                area.setName(name.substring(0, 2));
            }
        });
        Set<PieChartVo> pieChartVoSet = Stream.of(provinces).map(area -> new PieChartVo(area, 0L)).collect(Collectors.toSet());
        Set<PieChartVo> pieChartVoHashSet = new HashSet<>();
        pieChartVoHashSet.addAll(areaPieChartSet);
        pieChartVoHashSet.addAll(pieChartVoSet);
        long maxNum = pieChartVoHashSet.stream().max((p1, p2) -> p1.getValue().compareTo(p2.getValue())).get().getValue();
        long minNum = pieChartVoHashSet.stream().min((p1, p2) -> p1.getValue().compareTo(p2.getValue())).get().getValue();
        List<PieChartVo> pieChartVoList = new ArrayList<>(pieChartVoHashSet);
        //从大到小排序，
        Collections.sort(pieChartVoList, (v1, v2) -> v2.getValue().compareTo(v1.getValue()));
        MemberAreaVo memberAreaVo = new MemberAreaVo();
        memberAreaVo.setMinNum(minNum);
        memberAreaVo.setMaxNum(maxNum);
        memberAreaVo.setRegionList(pieChartVoList);
        return memberAreaVo;
    }



}
