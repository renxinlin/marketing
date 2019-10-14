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
        linkedHashMap.put("prize_wheels_reward_cdk","cdk");
        String[] uniqueFields={"奖励列表"};
        List<WheelsRewardCdk> wheelsRewardCdks=ExcelUtils.excelToList(is,sheetName,WheelsRewardCdk.class,linkedHashMap,uniqueFields);
        //提取有用的cdk
        List<WheelsRewardCdk> exitWheelsRewardCdks=new ArrayList<>(wheelsRewardCdks.size()-1);

        for(int i=1;i<wheelsRewardCdks.size();i++){
            exitWheelsRewardCdks.add(wheelsRewardCdks.get(i));
        }
        //插入数据库的list对象
        List<WheelsRewardCdk> uploadwheelsRewardCdks=new ArrayList<>(exitWheelsRewardCdks.size());
        String UUID=commonUtil.getUUID();
        for (WheelsRewardCdk wheelsRewardCdk:exitWheelsRewardCdks) {
            WheelsRewardCdk wheelsRewardCdk1=new WheelsRewardCdk();
            wheelsRewardCdk1.setCdk(wheelsRewardCdk.getCdk());
            wheelsRewardCdk1.setCdkKey(UUID);
            uploadwheelsRewardCdks.add(wheelsRewardCdk1);
        }
        wheelsRewardCdkService.saveBatch(uploadwheelsRewardCdks);
        return UUID;
    }
}
