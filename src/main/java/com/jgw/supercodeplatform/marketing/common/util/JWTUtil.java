package com.jgw.supercodeplatform.marketing.common.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.modelmapper.ModelMapper;

import java.util.*;

/**
 * JWT安全授权,暂时专用于用户
 */
public class JWTUtil {
	/**
	 * 创建信息
	 * @param jwtUser
	 * @return
	 */
	public static String createTokenWithClaim(H5LoginVO jwtUser) throws SuperCodeException{

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			Map<String, Object> map = new HashMap<String, Object>();
			Date nowDate = new Date();
			Date expireDate = getAfterDate(nowDate,0,0,0,2,0,0);//2小过期
			map.put("alg", "HS256");
			map.put("typ", "JWT");

			String token = JWT.create()
					/*设置头部信息 Header*/
					.withHeader(map)
					/*设置 载荷 Payload*/
					.withClaim("jwtUser", JSONObject.toJSONString(jwtUser))
					.withIssuer("JGW CJM COMPANY")//签名是有谁生成 例如 服务器
					.withSubject("H5 SECUCITY")//签名的主题
					.withAudience("APP")//签名的观众 也可以理解谁接受签名的
//					.withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的.
//					.withIssuedAt(nowDate) //生成签名的时间 签名何时启用
					.withExpiresAt(expireDate)//签名过期的时间
					/*签名 Signature */
					.sign(algorithm);
			return token;
		} catch (JWTCreationException exception){
			exception.printStackTrace();
			throw new SuperCodeException("创建授权信息失败");
		}
	}
	/**
	 * 返回一定时间后的日期
	 * @param date 开始计时的时间
	 * @param year 增加的年
	 * @param month 增加的月
	 * @param day 增加的日
	 * @param hour 增加的小时
	 * @param minute 增加的分钟
	 * @param second 增加的秒
	 * @return
	 */
	public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
		if(date == null){
			date = new Date();
		}


//		Calendar cal = new GregorianCalendar ();
		// 格林威治时间存在时间不一致问题
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(year != 0){
			cal.add(Calendar.YEAR, year);
		}
		if(month != 0){
			cal.add(Calendar.MONTH, month);
		}
		if(day != 0){
			cal.add(Calendar.DATE, day);
		}
		if(hour != 0){
			cal.add(Calendar.HOUR_OF_DAY, hour);
		}
		if(minute != 0){
			cal.add(Calendar.MINUTE, minute);
		}
		if(second != 0){
			cal.add(Calendar.SECOND, second);
		}
		return cal.getTime();
	}

	/**
	 * 解析信息
	 * @param token
	 * @return
	 * @throws SuperCodeException
	 */
	public static H5LoginVO verifyToken(String token) throws SuperCodeException{
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("JGW CJM COMPANY")
					// 时间校验容许60s误差，精度到毫秒 解决服务器不同节点时钟不同步的问题
					.acceptLeeway(1*60)
					.build(); //Reusable verifier instance
			DecodedJWT jwt = verifier.verify(token);

			Map<String, Claim> claims = jwt.getClaims();
			// 直接json转对象由于反射对非空属性没处理会报错
			Map jwtU = (Map) JSONObject.parse(claims.get("jwtUser").asString());
			ModelMapper mm = new ModelMapper();
			H5LoginVO userInfo = mm.map(jwtU, H5LoginVO.class);
			return userInfo;
		} catch (TokenExpiredException ex){
			throw new SuperCodeException("jwt-token已过期",403);
		}catch (Exception exception){
			exception.printStackTrace();
			throw new SuperCodeException("获取授权信息失败");
		}
	}

}