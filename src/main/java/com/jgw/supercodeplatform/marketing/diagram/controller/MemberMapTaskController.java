package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.tasktime.TaskTimeCalculator;
import com.jgw.supercodeplatform.marketing.diagram.vo.MemberMapVo;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
// TODO 检查twoweek-year的代码
@RestController
@RequestMapping("/marketing/memberMapTask")
@Api(tags = "会员地域分布")
public class MemberMapTaskController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.MEMBER_MAP;
    @Autowired
    private TaskTimeCalculator taskTimeCalculator;
    // TODO 检查一遍
    // 优化方向，用编码查，返回前端时，取枚举中的编码对应的文字
    // 此段文字为前端格式切勿修改
    private String provinces = "北京,天津,上海,重庆,河北,河南,云南,辽宁,黑龙江,湖南," +
            "安徽,山东,新疆,江苏,浙江,江西,湖北,广西,甘肃,山西,内蒙古,陕西,吉林,福建," +
            "贵州,广东,青海,西藏,四川,宁夏,海南,台湾,香港,澳门";
    private static final String  SPLIT_LABEL =",";

    @Autowired
    private MarketingMembersService service;
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
            @ApiImplicitParam(paramType = "query", value = "1一周,2,3,4,5,6一年,字符串格式，按顺序一周到一年", name = "timeValue")
    })
    @GetMapping("query")
    public RestResult query(String timeValue) throws SuperCodeException {
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

    public RestResult weekTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getWeek();
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0), week.get(week.size() - 1));
        return  task(registerNumMembers);

    }

    private RestResult task(List<MarketingMembers> registerNumMembers) {
        // 转换成地图格式
        List<String> provincesList = Arrays.asList(provinces.split(SPLIT_LABEL));
        Map<String, MemberMapVo> map = new TreeMap<>();
        if(!CollectionUtils.isEmpty(registerNumMembers)){
            for (String province : provincesList){
                MemberMapVo mapVo = new MemberMapVo();
                mapVo.setName(province);
                map.put(province,mapVo);
            }
            // 求和
            for(MarketingMembers registerNumMember : registerNumMembers){
                // 向省份添加数据
                for(String province : provincesList){
                    if(registerNumMember!=null
                            && registerNumMember.getProvinceName() !=null
                            && registerNumMember.getProvinceName().indexOf(province) != -1){
                        map.get(province).add(1);
                        continue;
                    }
                }
            }
        }

        // 定义排序规则
        if(!CollectionUtils.isEmpty(map.values())){
;           List<Map.Entry<String, MemberMapVo>> list = new ArrayList<>(map.entrySet());
            // 按值降序
            Collections.sort(list,(o2,o1)->o1.getValue().getValue()-o2.getValue().getValue());
            List<MemberMapVo> listValue = new LinkedList();
            list.forEach(e->listValue.add(e.getValue()));
            MemberMapVo max = listValue.get(0);
            MemberMapVo min = listValue.get(listValue.size()-1);
            Map result = new HashMap();
            Map other = new HashMap();
            other.put("max",max.getValue());
            other.put("min",min.getValue());
            result.put("list",listValue);
            result.put("other",other);
            return RestResult.success("success",result);
        }else{
            Map result = new HashMap();
            Map other = new HashMap();
            other.put("max",0);
            other.put("min",0);
            result.put("list",new LinkedList());
            result.put("other",other);
            return RestResult.success("success",result);
        }
    }

    /**
     *
     * @return
     * @throws SuperCodeException
     */
    public RestResult twoWeekTask( ) throws SuperCodeException {
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getTwoWeek();
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0), week.get(week.size() - 1));
        return  task(registerNumMembers);

    }

    public RestResult monthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getMonth();
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0), week.get(week.size() - 1));
        return  task(registerNumMembers);

    }

    public RestResult threeMonthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getThreeMonth();
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0)
                 ,taskTimeCalculator.getYesterday(week.get(week.size() - 1)));
        return  task(registerNumMembers);
    }

    public RestResult halfYearTask( ) throws SuperCodeException{

        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getHalfYear();
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0)
                ,taskTimeCalculator.getYesterday(week.get(week.size() - 1)));
        return  task(registerNumMembers);
    }

    public RestResult yearTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getYear();
        List<String> weekString = taskTimeCalculator.getYearString();
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0)
                ,taskTimeCalculator.getYesterday(week.get(week.size() - 1)));
        return  task(registerNumMembers);


    }

    public boolean isFinished() {
        return false;
    }

}
