package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.vo.CricleVo;
import com.jgw.supercodeplatform.marketing.diagram.tasktime.TaskTimeCalculator;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/marketing/memberPortraitTask")
@Api(tags = "会员画像")

public class MemberPortraitController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.MEMBER_PORTRAIT;
    static final String SPLIT="-";

    @Autowired
    private MarketingMembersService service;
    @Autowired
    private TaskTimeCalculator taskTimeCalculator;
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
            @ApiImplicitParam(paramType = "query", value = "1一周,2,3,4,5,6一年,字符串格式，按顺序一周到一年", name = "timeValue")
    })
    @GetMapping("query")
    public RestResult query(String timeValue) throws Exception {
        if(QueryEnum.WEEK.getStatus().equals(timeValue)){
            return weekTask();
        }
        if(QueryEnum.TWO_WEEK.getStatus().equals(timeValue)){
            return twoWeekTask();
        }
        if(QueryEnum.MONTH.getStatus().equals(timeValue)){
            return monthTask();
        }
        if(QueryEnum.THREE_MONTH.getStatus().equals(timeValue)){
            return threeMonthTask();
        }
        if(QueryEnum.HALF_YEAR.getStatus().equals(timeValue)){
            return halfYearTask();
        }
        if(QueryEnum.YEAR.getStatus().equals(timeValue)){
            return yearTask();
        }


        return RestResult.error("请选择时间范围...");
    }

    public RestResult weekTask( ) throws Exception {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getWeek();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);
    }

    public RestResult twoWeekTask( ) throws Exception {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getTwoWeek();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);
    }

    public RestResult monthTask( ) throws Exception{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getMonth();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);      }

    public RestResult threeMonthTask( ) throws Exception{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getThreeMonth();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0)
                ,taskTimeCalculator.getYesterday(dateParams.get(dateParams.size() - 1)));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);      }

    public RestResult halfYearTask( ) throws Exception{

        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getHalfYear();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0)
                ,taskTimeCalculator.getYesterday(dateParams.get(dateParams.size() - 1)));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);     }

    public RestResult yearTask( ) throws Exception{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getYear();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0)
                ,taskTimeCalculator.getYesterday(dateParams.get(dateParams.size() - 1)));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);
    }

    public boolean isFinished() {
        return false;
    }


    /**
     * 统计
     * @param organizationAllMemberWithDate
     * @return
     * @throws Exception
     */
    private Map task(List<MarketingMembers> organizationAllMemberWithDate) throws Exception{
        // 饼图
        List<CricleVo> sexCricleVos = new LinkedList();
        List<CricleVo> deviceCricleVos = new LinkedList();
        List<CricleVo> ageCricleVos = new LinkedList();

        // 性别图三项
        CricleVo man = new CricleVo();
        man.setItem("男");
        CricleVo woman = new CricleVo();
        woman.setItem("女");
        CricleVo other = new CricleVo();
        other.setItem("其他");

        // 注册设备
        CricleVo wxdevice = new CricleVo();
        wxdevice.setItem("微信");

        CricleVo zhifubaodevice = new CricleVo();
        zhifubaodevice.setItem("支付宝");

        CricleVo appdevice = new CricleVo();
        appdevice.setItem("app");

        CricleVo browerdevice = new CricleVo();
        browerdevice.setItem("浏览器");

        CricleVo qqdevice = new CricleVo();
        qqdevice.setItem("qq");

        CricleVo otherDevice = new CricleVo();
        otherDevice.setItem("其他");



        // 生日
        CricleVo[] agex0 = new CricleVo[10];
        int i = 0;
        // 设置ITEM [0-10,10-20...90-100]共十个元素
        for(int preI=0;preI<100;preI+=10) {
            CricleVo age = new CricleVo();
            age.setItem(preI+SPLIT+(preI+10));
            agex0[i]=age;
            i++;
        }

        // 100岁以上和0岁以下以及其他都归属其他
        CricleVo otherage = new CricleVo();
        otherage.setItem("其他");




        for(MarketingMembers marketingMember : organizationAllMemberWithDate){
            // 性别统计
            if(marketingMember.getSex() == null){
                other.add(1);
            }else if(marketingMember.getSex().equals("1")){
                woman.add(1);
            }else if(marketingMember.getSex().equals("0")){
                man.add(1);
            }else{
                other.add(1);
            }
            // 注册设备统计
            if(marketingMember.getDeviceType() == null){
                otherDevice.add(1);
            }else if(marketingMember.getDeviceType().intValue() == 1) {
                wxdevice.add(1);
            }else if(marketingMember.getDeviceType().intValue() == 2) {
                zhifubaodevice.add(1);
            }else if(marketingMember.getDeviceType().intValue() == 3) {
                appdevice.add(1);
            }else if(marketingMember.getDeviceType().intValue() == 4) {
                browerdevice.add(1);
            }else if(marketingMember.getDeviceType().intValue() == 5) {
                qqdevice.add(1);
            }else if(marketingMember.getDeviceType().intValue() == 6) {
                otherDevice.add(1);
            }else{
                otherDevice.add(1);
            }
            // 年龄统计
            String birthdayStr = marketingMember.getBirthday();
            Date birthday = null;

            int age = 0;
            if(!StringUtils.isBlank(birthdayStr)){
                birthday = DateUtil.yyyyMMddStrToDate(birthdayStr);
                age = DateUtil.getAge(birthday);
            }

            final int finalAge = age;
            final AtomicBoolean notAddeed = new AtomicBoolean(true);
            Arrays.asList(agex0).forEach(e->{
                String[] ageRange = e.getItem().split(SPLIT);
                int lowwer = Integer.parseInt(ageRange[0]);
                int upper = Integer.parseInt(ageRange[1]);
                if(finalAge > lowwer && finalAge <= upper){
                    e.add(1);
                    notAddeed.set(false);
                }
            });
            if(notAddeed.get()){
                otherage.add(1);
            }


        }

        // 性别
        // 百分比
        int sexNum = man.getCount()+woman.getCount()+other.getCount();

        BigDecimal manBD = new BigDecimal(man.getCount()*1.00/sexNum);
        double mandouble = manBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        man.setPercent(mandouble);
        man.setPercentStr(mandouble+"");

        BigDecimal womanBD = new BigDecimal(woman.getCount()*1.00/sexNum);
        double womandouble = womanBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        woman.setPercent(womandouble);
        woman.setPercentStr(womandouble+"");


        double percentSexDoubleSumWithLast = womandouble+mandouble;
        other.setPercent(1.00D-percentSexDoubleSumWithLast);
        other.setPercentStr(1.00D-percentSexDoubleSumWithLast+"");
        if(man.getCount()> 0 ){

            sexCricleVos.add(man);

        }
        if(woman.getCount()> 0 ){
            sexCricleVos.add(woman);

        }
        if(other.getCount()> 0 ){
            sexCricleVos.add(other);

        }


        // 注册设备
        // 百分比
        int deviceNum = wxdevice.getCount()+zhifubaodevice.getCount()+appdevice.getCount()
                +browerdevice.getCount()+qqdevice.getCount()+otherDevice.getCount();

        BigDecimal wxdeviceBD = new BigDecimal(wxdevice.getCount()*1.00/deviceNum);
        double wxdevicedouble = wxdeviceBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        wxdevice.setPercent(wxdevicedouble);
        wxdevice.setPercentStr(wxdevicedouble+"");

        BigDecimal zhifubaodeviceBD = new BigDecimal(zhifubaodevice.getCount()*1.00/deviceNum);
        double zhifubaodevicedouble = zhifubaodeviceBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        zhifubaodevice.setPercent(zhifubaodevicedouble);
        zhifubaodevice.setPercentStr(zhifubaodevicedouble+"");

        BigDecimal appdeviceBD = new BigDecimal(appdevice.getCount()*1.00/deviceNum);
        double appdevicedouble = appdeviceBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        appdevice.setPercent(appdevicedouble);
        appdevice.setPercentStr(appdevicedouble+"");

        BigDecimal browerdeviceBD = new BigDecimal(browerdevice.getCount()*1.00/deviceNum);
        double browerdevicedouble = browerdeviceBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        browerdevice.setPercent(browerdevicedouble);
        browerdevice.setPercentStr(browerdevicedouble+"");


        BigDecimal qqdeviceBD = new BigDecimal(qqdevice.getCount()*1.00/deviceNum);
        double qqdevicedouble = qqdeviceBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        qqdevice.setPercent(qqdevicedouble);
        qqdevice.setPercentStr(qqdevicedouble+"");

        double percentDeviceDoubleSumWithLast = wxdevicedouble+zhifubaodevicedouble+appdevicedouble+browerdevicedouble+qqdevicedouble;
        otherDevice.setPercent(1.00D-percentDeviceDoubleSumWithLast);
        otherDevice.setPercentStr(1.00D-percentDeviceDoubleSumWithLast+"");


        if(wxdevice.getCount()> 0 ){
            deviceCricleVos.add(wxdevice);
        }
        if(zhifubaodevice.getCount()> 0 ){
            deviceCricleVos.add(zhifubaodevice);
        }
        if(appdevice.getCount()> 0 ){
            deviceCricleVos.add(appdevice);
        }
        if(browerdevice.getCount()> 0 ){
            deviceCricleVos.add(browerdevice);
        }
        if(qqdevice.getCount()> 0 ){
            deviceCricleVos.add(qqdevice);
        }
        if(otherDevice.getCount()> 0 ){
            deviceCricleVos.add(otherDevice);
        }



        // 年龄统计
        // 百分比
        int ageNum = otherage.getCount();
        ageNum += Arrays.stream(agex0).mapToInt(CricleVo::getCount).sum();
        final int finalAgeNum = ageNum;
        AtomicReference<Double> percentAgeDoubleSumWithLast = new AtomicReference<>(0.00D);
        Arrays.asList(agex0).forEach(e ->{
            BigDecimal eBD = new BigDecimal(e.getCount()*1.00/finalAgeNum);
            double edouble = eBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            percentAgeDoubleSumWithLast.updateAndGet(v -> new Double((double) (v + edouble)));
            e.setPercent(edouble);
            e.setPercentStr(edouble +"");
        });

        otherage.setPercent(1.00D- percentAgeDoubleSumWithLast.get());
        otherage.setPercentStr(1.00d- percentAgeDoubleSumWithLast.get()+"");

        List<CricleVo> cricleVos = Arrays.asList(agex0);
        for(CricleVo vo:cricleVos){
            if(vo.getCount()> 0){
                ageCricleVos.add(vo);
            }
        }
        if(otherage.getCount()> 0){
            ageCricleVos.add(otherage);
        }

        // 最终格式
        Map result = new HashMap();
        result.put("sex",sexCricleVos);
        result.put("age",ageCricleVos);
        result.put("device",deviceCricleVos);
        return result;
    }


    public static void main(String[] args) {
        double d = 0.14511111;
         BigDecimal b = new BigDecimal(d);
        d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
         System.out.println(d);
    }
}
