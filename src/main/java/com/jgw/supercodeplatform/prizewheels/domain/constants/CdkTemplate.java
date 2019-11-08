package com.jgw.supercodeplatform.prizewheels.domain.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author fangshiping
 * @date 2019/11/8 9:02
 */
@Configuration
@AutoConfigureAfter(QiNiuYunConfigConstant.class)
public class CdkTemplate {
    public static String URL;

    public  String getCdkKey() {
        return URL;
    }
    // e656173fbfd24113a3ced2e43b9a7700
    @Value("${marketing.prizeWheels.cdktemplate.excel}")
    public  void setCdkKey(String cdkKey) {
        URL = QiNiuYunConfigConstant.URL +cdkKey;
    }
}
