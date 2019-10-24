package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CdkStatus;
import com.jgw.supercodeplatform.prizewheels.domain.constants.QiNiuYunConfigConstant;
import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.WheelsRewardCdkService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardCdkPojo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 按照组件不可换修改新的业务接口
 */
@Slf4j
@Component
public class CdkEventSubscriberImplV2 implements CdkEventSubscriber {

    private static final String sheetName = "Sheet1";

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WheelsRewardCdkService wheelsRewardCdkService;

    @Override
    public void handle(CdkEvent cdkEvent) {
        log.info("cdkEvent{}", JSONObject.toJSONString(cdkEvent));

        // 根据cdkkey去七牛云读取文件
        String cdkKey = cdkEvent.getCdkUuid();
        String excelUrl = QiNiuYunConfigConstant.URL + cdkKey;

        // 读取excel流
        InputStream is = downExcelStream(excelUrl);
        // excel转list
        List<WheelsRewardCdkPojo> wheelsRewardCdkPojos = excelStreamToList(is);
        log.info("excel输出{}", JSONObject.toJSONString(wheelsRewardCdkPojos));
        // 业务属性补充
        wheelsRewardCdkPojos = addOtherFields(wheelsRewardCdkPojos,cdkKey,cdkEvent.getPrizeRewardId());
        log.info("addOtherFields{}", JSONObject.toJSONString(wheelsRewardCdkPojos));

        // 持久化
        try {
            wheelsRewardCdkService.saveBatch(wheelsRewardCdkPojos);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException("cdk保存失败,excel格式错误或cdk重复");
        }


    }

    private List<WheelsRewardCdkPojo> addOtherFields(List<WheelsRewardCdkPojo> wheelsRewardCdkPojos, String cdkKey, Long rewardId) {
        for (WheelsRewardCdkPojo wheelsRewardCdkPojo : wheelsRewardCdkPojos) {
            wheelsRewardCdkPojo.setCdkKey(cdkKey);
            wheelsRewardCdkPojo.setStatus(CdkStatus.BEFORE_REWARD);
            wheelsRewardCdkPojo.setPrizeRewardId(rewardId);
            wheelsRewardCdkPojo.setOrganizationId(commonUtil.getOrganizationId());
            wheelsRewardCdkPojo.setOrganizationName(commonUtil.getOrganizationName());
        }
        return wheelsRewardCdkPojos;
    }

    private List excelStreamToList(InputStream is) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("cdk", "cdk"); // excel:cdk ,table:cdk
        String[] uniqueFields = {"cdk"}; // cdk不可重复
        try {
            List<WheelsRewardCdkPojo> wheelsRewardCdkPojos = ExcelUtils.excelToList(is, sheetName, WheelsRewardCdkPojo.class, linkedHashMap, uniqueFields);
            return wheelsRewardCdkPojos;
        } catch (ExcelException e) {
            e.printStackTrace();
            throw new RuntimeException("excel解析失败");
        }

    }

    private InputStream downExcelStream(String url) {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                return is;
            } else {
                log.error("七牛云响应失败{}", resp.isSuccessful());
                throw new RuntimeException("七牛云excel读取失败");
            }
        } catch (IOException e) {
            log.error("excel读取失败{}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("七牛云excel读取失败");
        }
    }


}
