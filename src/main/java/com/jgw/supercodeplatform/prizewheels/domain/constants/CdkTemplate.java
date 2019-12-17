package com.jgw.supercodeplatform.prizewheels.domain.constants;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author fangshiping
 * @date 2019/11/8 9:02
 */
@Configuration
public class CdkTemplate   {
    public static String URL;

    public  String getCdkKey() {
        return URL;
    }
    // 2404b1c888824c8e8f3266a806537b24
    @Value("${marketing.prizeWheels.cdktemplate.excel}")
    public  void setCdkKey(String cdkKey) {
        URL =cdkKey;
    }


}
