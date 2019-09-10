package com.jgw.supercodeplatform.marketingsaler.integral.application.group;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.CodeManagerFallbackFeign;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.OuterCodeInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Service
public class OuterCodeInfoService {
    @Autowired
    OuterCodeInfoFeign outerCodeInfoFeign;

    /**
     * 服务返回运行时异常
     * @param outCodeInfoDto
     * @return
     */
    public RestResult<Long> getCurrentLevel(OutCodeInfoDto outCodeInfoDto){
        log.info("准备调用码服务获取层级{}",outCodeInfoDto);
        RestResult<Object> currentLevel = outerCodeInfoFeign.getCurrentLevel(outCodeInfoDto);
        if(currentLevel!=null && currentLevel.getState() == 200){
            if( currentLevel.getResults()!=null &&  currentLevel.getResults() instanceof Long){
                // 单码
                return RestResult.success(currentLevel.getMsg(), (Long) currentLevel.getResults());
            }
        }
        throw new RuntimeException("获取码服务层级信息失败");
    }

}
