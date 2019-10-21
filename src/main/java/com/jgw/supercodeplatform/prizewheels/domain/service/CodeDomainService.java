package com.jgw.supercodeplatform.prizewheels.domain.service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.exception.PrizeWheelsForWxErcodeException;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.prizewheels.domain.model.ScanRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeDomainService {


    @Autowired
    private CommonService commonService;


    public String vaildAndGetBatchId(String codeTypeId,String outerCodeId){
        commonService.checkCodeTypeValid(Long.parseLong(codeTypeId));
        String sbatchId = commonService.checkCodeValid(outerCodeId, codeTypeId);
        return sbatchId;
    }

    /**
     * 微信二维码存在返回微信二维码
     * @param scanRecord
     * @param wxErcode
     * @return
     */
    public boolean noscanedOrTerminated(ScanRecord scanRecord,String wxErcode)  {
        if(scanRecord == null){
            // 返回微信二维码 返回null 不显示公众号
            throw new PrizeWheelsForWxErcodeException(wxErcode);
        }
        return true;
    }
}
