package com.jgw.supercodeplatform.burypoint.prizewheels.service.get;

import com.jgw.supercodeplatform.burypoint.prizewheels.constants.RedisKeyConstants;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author fangshiping
 * @date 2019/10/22 9:05
 */
@Service
public class BuryPointDataGetService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * String pvAll
     * @return
     */
    public String getPvAll(){
        String result=null;
        try {
            result=redisUtil.get(RedisKeyConstants.PV_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * HashMap pvDay
     */
    public HashMap<String,String> getPvDay(){
        HashMap<String,String> hashResult=new HashMap<>();
        try {
            String value=redisUtil.get(RedisKeyConstants.PV_DAY);
            hashResult.put("day",value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashResult;
    }

    /**
     * String pvAll
     * @return
     */
    public String getUvAll(){
        String result=null;
        try {
            result=redisUtil.get(RedisKeyConstants.UV_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * HashMap pvDay
     */
    public HashMap<String,String> getUvDay(){
        HashMap<String,String> hashResult=new HashMap<>();
        try {
            String value=redisUtil.get(RedisKeyConstants.UV_DAY);
            hashResult.put("day",value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashResult;
    }
}
