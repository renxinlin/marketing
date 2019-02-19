package com.jgw.supercodeplatform.marketing.common.util;

/**
 * Created by corbett on 2018/9/17.
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jgw.supercodeplatform.marketing.exception.base.SuperCodeException;


/**
 * @author Created by jgw136 on 2018/05/23.
 */
public class Md5Util {

    /**
     * 小写md5返回值
     * @param str
     * @return
     * @throws SuperCodeException
     */
    public static String md5ToLower(String str) throws SuperCodeException {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new SuperCodeException("获取md5对象失败");
        }
        try {
            md5.update((str).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SuperCodeException("待转字符串转字节数组失败");
        }
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    /**
     * 获取大写md5值
     * @param str
     * @return
     * @throws SuperCodeException
     */
    public static String md5ToUpper(String str) throws SuperCodeException {
        return md5ToLower(str).toUpperCase();
    }
}

