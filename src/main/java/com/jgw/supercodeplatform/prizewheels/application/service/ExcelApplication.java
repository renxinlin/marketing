package com.jgw.supercodeplatform.prizewheels.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardCdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private static final String sheetName = "Sheet1";
    /**
     * 上传奖励报表excel
     * @param is
     */
    public void uploadExcel(InputStream is) throws ExcelException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("prize_wheels_reward_cdk","cdk");
        String[] uniqueFields={"奖励列表"};
        List<WheelsRewardCdk> wheelsRewardCdks=ExcelUtils.excelToList(is,sheetName,WheelsRewardCdk.class,linkedHashMap,uniqueFields);
        List<WheelsRewardCdk> uploadwheelsRewardCdks=new ArrayList<>(wheelsRewardCdks.size()-1);

        for (WheelsRewardCdk wheelsRewardCdk:wheelsRewardCdks) {
            WheelsRewardCdk wheelsRewardCdk1=new WheelsRewardCdk();
            wheelsRewardCdk1.setCdk(wheelsRewardCdk.getCdk());
            wheelsRewardCdk1.setCdkKey(commonUtil.getUUID());
            uploadwheelsRewardCdks.add(wheelsRewardCdk1);
        }
        
    }
}
