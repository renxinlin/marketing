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
public class CallBackConstant  implements InitializingBean {
    /**
     * 大转盘 码管理回调营销URL
     */
    public static String PRIZE_WHEELS_URL = "";

    @Value("${marketing.activity.h5page.url:http://market-dev.h5.kf315.net/}")
    String TO_WEB ;

    public static String TO_WEB_URL ;

    @Override
    public void afterPropertiesSet(){
        TO_WEB_URL = TO_WEB;
        Asserts.check(TO_WEB_URL != null,"项目初始化失败");
        log.info("营销大转盘回调前端URL{}",TO_WEB_URL);
    }
}
