package com.jgw.supercodeplatform.marketingsaler.integral.application.group;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.OutCodeInfoVo;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.OuterCodeInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OuterCodeInfoService {
    @Autowired
    private ModelMapper modelMapper;
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
        log.info("准备调用码服务获取层级返回如下{}", JSONObject.toJSONString(currentLevel));
        if(currentLevel!=null && currentLevel.getState() == 200){
            if( currentLevel.getResults()!=null){
                // 单码
                OutCodeInfoVo outCodeInfoVo = JSONObject.parseObject((JSONObject.toJSONString(currentLevel.getResults())), OutCodeInfoVo.class);
                return RestResult.success(currentLevel.getMsg(), outCodeInfoVo.getLevel());

            }
        }
        throw new RuntimeException("获取码服务层级信息失败");
    }

}
