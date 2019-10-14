package com.jgw.supercodeplatform.prizewheels.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.WheelsRewardCdkService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardCdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author fangshiping
 * @date 2019/10/14 14:24
 */
@Service
public class ExcelApplication {
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WheelsRewardCdkService wheelsRewardCdkService;

    private static final String sheetName = "Sheet1";
    /**
     * 上传奖励报表excel
     * @param is
     */
    public String uploadExcel(InputStream is) throws ExcelException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("cdk","cdk"); // excel:cdk ,table:cdk
        String[] uniqueFields={"cdk"}; // cdk不可重复
        List<WheelsRewardCdk> wheelsRewardCdks=ExcelUtils.excelToList(is,sheetName,WheelsRewardCdk.class,linkedHashMap,uniqueFields);

        //插入数据库的list对象
         String cdkKey=commonUtil.getUUID();
        for (WheelsRewardCdk wheelsRewardCdk:wheelsRewardCdks) {
            wheelsRewardCdk.setCdkKey(cdkKey);
            wheelsRewardCdk.setOrganizationId(commonUtil.getOrganizationId());
            wheelsRewardCdk.setOrganizationName(commonUtil.getOrganizationName());
        }
        wheelsRewardCdkService.saveBatch(wheelsRewardCdks);
        return cdkKey;
    }
}
