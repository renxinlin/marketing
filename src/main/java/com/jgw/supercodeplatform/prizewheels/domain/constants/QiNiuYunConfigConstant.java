package com.jgw.supercodeplatform.prizewheels.domain.constants;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
public class QiNiuYunConfigConstant  implements InitializingBean {

    @Value("${qiniuyun.url:http://filetest.cjm.so/}")
    String TO_WEB ;
    /**
     * 大转盘回调网页
     */
    public static String URL  ;


    @Override
    public void afterPropertiesSet() throws Exception {
        URL = TO_WEB;
        Asserts.check(URL != null,"项目初始化失败");
        log.info("七牛云地址{}",URL);
    }
}
