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

import java.util.*;

@RestController
@RequestMapping("/marketing/memberPortraitTask")
@Api(tags = "会员画像")

public class MemberPortraitController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.MEMBER_PORTRAIT;

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
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);      }

    public RestResult halfYearTask( ) throws Exception{

        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getHalfYear();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        Map result = new HashMap();
        if(!CollectionUtils.isEmpty(organizationAllMemberWithDate)){
            result = task(organizationAllMemberWithDate);
        }
        return RestResult.success("success",result);     }

    public RestResult yearTask( ) throws Exception{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getYear();
        List<MarketingMembers> organizationAllMemberWithDate = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
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

        CricleVo age0 = new CricleVo();
        age0.setItem("0-10");

        CricleVo age10 = new CricleVo();
        age10.setItem("10-20");

        CricleVo age20 = new CricleVo();
        age20.setItem("20-30");

        CricleVo age30 = new CricleVo();
        age30.setItem("30-40");

        CricleVo age40 = new CricleVo();
        age40.setItem("40-50");

        CricleVo age50 = new CricleVo();
        age50.setItem("50-60");

        CricleVo age60 = new CricleVo();
        age60.setItem("60-70");

        CricleVo age70 = new CricleVo();
        age70.setItem("70-80");

        CricleVo age80 = new CricleVo();
        age80.setItem("80-90");

        CricleVo age90 = new CricleVo();
        age90.setItem("90-100");

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
            if(!StringUtils.isBlank(birthdayStr)){
                birthday = DateUtil.yyyyMMddStrToDate(birthdayStr);

            }
            // 年龄统计，顺序不可乱！！！
            if(birthday == null){
                otherage.add(1);
            }else if( DateUtil.getAge(birthday) <= 0) {
                otherage.add(1);
            }else if(10 - DateUtil.getAge(birthday) >= 0) {
//                （1，10]
                age0.add(1);
            }else if(20 - DateUtil.getAge(birthday) >= 0) {
                age10.add(1);
            }else if(30 - DateUtil.getAge(birthday) >= 0) {
                age20.add(1);
            }else if(40 - DateUtil.getAge(birthday) >= 0) {
                age30.add(1);
            }else if(50 - DateUtil.getAge(birthday) >= 0) {
                age40.add(1);
            }else if(60 - DateUtil.getAge(birthday) >= 0) {
                age50.add(1);
            }else if(70 - DateUtil.getAge(birthday) >= 0) {
                age60.add(1);
            }else if(80 - DateUtil.getAge(birthday) >= 0) {
                age70.add(1);
            }else if(90 - DateUtil.getAge(birthday) >= 0) {
//                (80,90]
                age80.add(1);
            }else if(100 - DateUtil.getAge(birthday) >= 0) {
//                (91,100]
                age90.add(1);
            }else {
                otherage.add(1);
            }

        }

        // 性别
        // 百分比
        int sexNum = man.getCount()+woman.getCount()+other.getCount();
        man.setPercent(man.getCount()*1.00/sexNum);
        man.setPercentStr(man.getCount()*1.00/sexNum+"");
        woman.setPercent(woman.getCount()*1.00/sexNum);
        woman.setPercentStr(woman.getCount()*1.00/sexNum+"");
        other.setPercent(other.getCount()*1.00/sexNum);
        other.setPercentStr(other.getCount()*1.00/sexNum+"");
        sexCricleVos.add(man);
        sexCricleVos.add(woman);
        sexCricleVos.add(other);


        // 注册设备
        // 百分比
        int deviceNum = wxdevice.getCount()+zhifubaodevice.getCount()+appdevice.getCount()
                +browerdevice.getCount()+qqdevice.getCount()+otherDevice.getCount();
        wxdevice.setPercent(wxdevice.getCount()*1.00/deviceNum);
        wxdevice.setPercentStr(wxdevice.getCount()*1.00/deviceNum+"");

        zhifubaodevice.setPercent(zhifubaodevice.getCount()*1.00/deviceNum);
        zhifubaodevice.setPercentStr(zhifubaodevice.getCount()*1.00/deviceNum+"");

        appdevice.setPercent(appdevice.getCount()*1.00/deviceNum);
        appdevice.setPercentStr(appdevice.getCount()*1.00/deviceNum+"");

        browerdevice.setPercent(browerdevice.getCount()*1.00/deviceNum);
        browerdevice.setPercentStr(browerdevice.getCount()*1.00/deviceNum+"");

        qqdevice.setPercent(qqdevice.getCount()*1.00/deviceNum);
        qqdevice.setPercentStr(qqdevice.getCount()*1.00/deviceNum+"");

        otherDevice.setPercent(otherDevice.getCount()*1.00/deviceNum);
        otherDevice.setPercentStr(otherDevice.getCount()*1.00/deviceNum+"");



        deviceCricleVos.add(wxdevice);
        deviceCricleVos.add(zhifubaodevice);
        deviceCricleVos.add(appdevice);
        deviceCricleVos.add(browerdevice);
        deviceCricleVos.add(qqdevice);
        deviceCricleVos.add(otherDevice);


        // 年龄统计
        // 百分比
        int ageNum =
                  age0.getCount()+age10.getCount()+age20.getCount()
                + age30.getCount()+age40.getCount()+age50.getCount()
                + age60.getCount()+age70.getCount()+age80.getCount()
                + age90.getCount()+otherage.getCount();

        age0.setPercent(age0.getCount()*1.00/ageNum);
        age0.setPercentStr(age0.getCount()*1.00/ageNum+"");

        age10.setPercent(age10.getCount()*1.00/ageNum);
        age10.setPercentStr(age10.getCount()*1.00/ageNum+"");

        age20.setPercent(age20.getCount()*1.00/ageNum);
        age20.setPercentStr(age20.getCount()*1.00/ageNum+"");

        age30.setPercent(age30.getCount()*1.00/ageNum);
        age30.setPercentStr(age30.getCount()*1.00/ageNum+"");

        age40.setPercent(age40.getCount()*1.00/ageNum);
        age40.setPercentStr(age40.getCount()*1.00/ageNum+"");

        age50.setPercent(age50.getCount()*1.00/ageNum);
        age50.setPercentStr(age50.getCount()*1.00/ageNum+"");

        age60.setPercent(age60.getCount()*1.00/ageNum);
        age60.setPercentStr(age60.getCount()*1.00/ageNum+"");

        age70.setPercent(age70.getCount()*1.00/ageNum);
        age70.setPercentStr(age70.getCount()*1.00/ageNum+"");

        age80.setPercent(age80.getCount()*1.00/ageNum);
        age80.setPercentStr(age80.getCount()*1.00/ageNum+"");

        age90.setPercent(age90.getCount()*1.00/ageNum);
        age90.setPercentStr(age90.getCount()*1.00/ageNum+"");

        otherage.setPercent(otherage.getCount()*1.00/ageNum);
        otherage.setPercentStr(otherage.getCount()*1.00/ageNum+"");


        ageCricleVos.add(age0);
        ageCricleVos.add(age10);
        ageCricleVos.add(age20);
        ageCricleVos.add(age30);
        ageCricleVos.add(age40);
        ageCricleVos.add(age50);
        ageCricleVos.add(age60);
        ageCricleVos.add(age70);
        ageCricleVos.add(age80);
        ageCricleVos.add(age90);
        ageCricleVos.add(otherage);

        // 最终格式
        Map result = new HashMap();
        result.put("sex",sexCricleVos);
        result.put("age",ageCricleVos);
        result.put("device",deviceCricleVos);
        return result;
    }


}
