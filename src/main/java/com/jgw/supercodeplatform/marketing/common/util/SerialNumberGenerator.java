package com.jgw.supercodeplatform.marketing.common.util;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @desc 各种序列号生成器
 */
public class SerialNumberGenerator {
    public static SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 订单号生成器
     * @return
     */
    public static String generatorDateAndFifteenNumber(){
        return new StringBuffer(formart.format(new Date())).append(getGUID(15)).toString();
    }

    /**
     * 随机数生成器
     * @param num 生成数量
     * @return
     */
    public static String getGUID(int num) {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < num; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }
}
