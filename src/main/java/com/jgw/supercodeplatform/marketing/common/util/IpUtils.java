package com.jgw.supercodeplatform.marketing.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Ip工具类
 *
 * @author liujianqiang
 * @date 2019年1月4日
 */
@Slf4j
public class IpUtils {

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     * @author liujianqiang
     * @data 2019年1月4日
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        final String unknown = "unknown";
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "X-Forwarded-For");
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "X-Real-IP");
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "Proxy-Client-IP");
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "WL-Proxy-Client-IP");
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "HTTP_CLIENT_IP");
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "HTTP_X_FORWARDED_FOR");
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || unknown.equalsIgnoreCase(XFor)) {
            log.info("获取IP地址 from {}", "request.getRemoteAddr()");
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


}
