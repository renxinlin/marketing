package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.tasktime.TaskTimeCalculator;
import com.jgw.supercodeplatform.marketing.diagram.vo.StatisicsVo;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/marketing/statisicsTask")
@Api(tags = "积分参与统计")
public class StatisicsTaskController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.STATISTICS;
    @Autowired
    private TaskTimeCalculator taskTimeCalculator;

    @Autowired
    private WXPayTradeOrderMapper wXPayTradeOrderMapper;

    @Autowired
    private IntegralRecordService integralRecordService;


    @Autowired
    private CodeEsService codeEsService;
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
            @ApiImplicitParam(paramType = "query", value = "1一周,2,3,4,5,6一年,字符串格式，按顺序一周到一年", name = "timeValue")
    })
    @GetMapping("query")
    public RestResult<StatisicsVo> query(String timeValue) throws SuperCodeException, ParseException {
        /**
         *
         * 活动点击量                                                 1，100,000
         * 微信红包发放累计金额  数据库的单位是分
         * 积分发放累计数值                                             1，100,000
         * 积分兑换累计数值                                             1，100,000
         */
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
        return RestResult.error("请选择时间范围...",null);
    }

    public RestResult weekTask( ) throws SuperCodeException, ParseException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getWeek();
        List<String> dateParamsString = taskTimeCalculator.getWeekString();

        // * 活动点击量
        Integer clickNum = codeEsService.countOrganizationActivityClickNumByDate(organizationId, dateParamsString.get(0), dateParamsString.get(dateParams.size() - 1));

        // * 微信红包发放累计金额【精度同微信:分】
        Integer momeyNum = wXPayTradeOrderMapper.getOrganizationIdAmoutByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // * 积分发放累计数值
        Integer integralNum = integralRecordService.sumOrganizationUsingIntegralByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // * 积分兑换累计数值
        Integer exchangeNum = integralRecordService.sumOrganizationIntegralExchangeByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        return returnVo(clickNum,integralNum,exchangeNum,momeyNum);

    }

    private RestResult returnVo(Integer clickNum, Integer integralNum, Integer exchangeNum, Integer momeyNum) {
        StatisicsVo result                       = new StatisicsVo();
        result         .setClickNum(clickNum == null ? 0 : clickNum);
        result.setIntegralNum(integralNum == null ? 0 : integralNum);
        result.setExchangeNum(exchangeNum == null ? 0 : exchangeNum);
        // 分转元
        result         .setMoneyNum(momeyNum == null ? 0F :(float)(momeyNum*1.0/100));
        return RestResult.success("success",result);
    }


    public RestResult twoWeekTask( ) throws SuperCodeException, ParseException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getTwoWeek();
        List<String> dateParamsString = taskTimeCalculator.getTwoWeekString();

        // * 活动点击量
        Integer clickNum = codeEsService.countOrganizationActivityClickNumByDate(organizationId, dateParamsString.get(0), dateParamsString.get(dateParamsString.size() - 1));

        // * 微信红包发放累计金额【精度同微信:分】
        Integer momeyNum = wXPayTradeOrderMapper.getOrganizationIdAmoutByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // * 积分发放累计数值
        Integer integralNum = integralRecordService.sumOrganizationUsingIntegralByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // * 积分兑换累计数值
        Integer exchangeNum = integralRecordService.sumOrganizationIntegralExchangeByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        return returnVo(clickNum,integralNum,exchangeNum,momeyNum);

    }

    public RestResult monthTask( ) throws SuperCodeException, ParseException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getMonth();
        List<String> dateParamsString = taskTimeCalculator.getMonthString();

        // * 活动点击量
        Integer clickNum = codeEsService.countOrganizationActivityClickNumByDate(organizationId, dateParamsString.get(0), dateParamsString.get(dateParamsString.size() - 1));

        // * 微信红包发放累计金额【精度同微信:分】
        Integer momeyNum = wXPayTradeOrderMapper.getOrganizationIdAmoutByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // * 积分发放累计数值
        Integer integralNum = integralRecordService.sumOrganizationUsingIntegralByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // * 积分兑换累计数值
        Integer exchangeNum = integralRecordService.sumOrganizationIntegralExchangeByDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        return returnVo(clickNum,integralNum,exchangeNum,momeyNum);

    }

    public RestResult threeMonthTask( ) throws SuperCodeException, ParseException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getThreeMonth();
        List<String> dateParamsString = taskTimeCalculator.getThreeMonthString();

        // * 活动点击量
        Integer clickNum = codeEsService.countOrganizationActivityClickNumByDate(organizationId, dateParamsString.get(0)
                ,taskTimeCalculator.getYesterdayStr(dateParamsString.get(dateParamsString.size() - 1)));

        // * 微信红包发放累计金额【精度同微信:分】
        Integer momeyNum = wXPayTradeOrderMapper.getOrganizationIdAmoutByDate(organizationId, dateParams.get(0)
                , dateParams.get(dateParams.size() - 1));
        // * 积分发放累计数值
        Integer integralNum = integralRecordService.sumOrganizationUsingIntegralByDate(organizationId, dateParams.get(0)
                ,dateParams.get(dateParams.size() - 1));

        // * 积分兑换累计数值
        Integer exchangeNum = integralRecordService.sumOrganizationIntegralExchangeByDate(organizationId, dateParams.get(0)
                ,dateParams.get(dateParams.size() - 1));

        return returnVo(clickNum,integralNum,exchangeNum,momeyNum);

    }

    public RestResult halfYearTask( ) throws SuperCodeException, ParseException {

        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getHalfYear();
        List<String> dateParamsString = taskTimeCalculator.getHalfYearString();


        // * 活动点击量
        // TODO 测试es时间问题
        Integer clickNum = codeEsService.countOrganizationActivityClickNumByDate(organizationId, dateParamsString.get(0)
                ,taskTimeCalculator.getYesterdayStr(dateParamsString.get(dateParamsString.size() - 1)));

        // * 微信红包发放累计金额【精度同微信:分】
        Integer momeyNum = wXPayTradeOrderMapper.getOrganizationIdAmoutByDate(organizationId, dateParams.get(0)
                , dateParams.get(dateParams.size() - 1));
        // * 积分发放累计数值
        Integer integralNum = integralRecordService.sumOrganizationUsingIntegralByDate(organizationId, dateParams.get(0)
                ,dateParams.get(dateParams.size() - 1));

        // * 积分兑换累计数值
        Integer exchangeNum = integralRecordService.sumOrganizationIntegralExchangeByDate(organizationId, dateParams.get(0)
                ,dateParams.get(dateParams.size() - 1));
        return returnVo(clickNum,integralNum,exchangeNum,momeyNum);

    }

    public RestResult yearTask( ) throws SuperCodeException, ParseException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getYear();
        List<String> dateParamsString = taskTimeCalculator.getYearString();

        // * 活动点击量
        Integer clickNum = codeEsService.countOrganizationActivityClickNumByDate(organizationId, dateParamsString.get(0)
                ,taskTimeCalculator.getYesterdayStr(dateParamsString.get(dateParamsString.size() - 1)));

        // * 微信红包发放累计金额【精度同微信:分】
        Integer momeyNum = wXPayTradeOrderMapper.getOrganizationIdAmoutByDate(organizationId, dateParams.get(0)
                , dateParams.get(dateParams.size() - 1));
        // * 积分发放累计数值
        Integer integralNum = integralRecordService.sumOrganizationUsingIntegralByDate(organizationId, dateParams.get(0)
                ,dateParams.get(dateParams.size() - 1));

        // * 积分兑换累计数值
        Integer exchangeNum = integralRecordService.sumOrganizationIntegralExchangeByDate(organizationId, dateParams.get(0)
                ,dateParams.get(dateParams.size() - 1));

        return returnVo(clickNum,integralNum,exchangeNum,momeyNum);

    }

    public boolean isFinished() {
        return false;
    }

}
