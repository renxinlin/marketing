package com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Ip工具类
 *
 * @author liujianqiang
 * @date 2019年1月4日
 */
public class IpUtils {

    private static Logger logger = LoggerFactory.getLogger(IpUtils.class);


    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     * @author liujianqiang
     * @data 2019年1月4日
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            logger.info("ip==========X-Forwarded-For,XFor= " + XFor);
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            logger.info("ip==========X-Real-IP,XFor= " + XFor);
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
            logger.info("ip==========Proxy-Client-IP,XFor= " + XFor);
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
            logger.info("ip==========WL-Proxy-Client-IP,XFor= " + XFor);
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
            logger.info("ip==========HTTP_CLIENT_IP,XFor= " + XFor);
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.info("ip==========HTTP_X_FORWARDED_FOR,XFor= " + XFor);
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
            logger.info("ip==========RemoteAddr,XFor= " + XFor);
        }
        return XFor;
    }


}
