package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.dto.integral.JwtUser;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.modelmapper.ModelMapper;

import java.util.*;

/**
 * jwt时间问题参考
 *
 * 			Payload 部分也是一个 JSON 对象，用来存放实际需要传递的数据。JWT 规定了7个官方字段，供选用。
 * 			iss (issuer)：签发人
 * 			exp (expiration time)：过期时间
 * 			sub (subject)：主题
 * 			aud (audience)：受众
 * 			nbf (Not Before)：生效时间
 * 			iat (Issued At)：签发时间
 * 			jti (JWT ID)：编号
 * 其中 时间验证包含 exp nbf iat
 * 时间范围为 [ nbf && iat,exp]
 * 时间精度为ms
 * .acceptLeeway(1*60) //60s范围调整  验证时可加时间容错范围调整 影响 exp nbf iat;则不同节点之间时间误差可容许在一分钟内
 *  exp nbf iat三者互不影响,如签名不设置相关属性,则解token不进行相关校验
 */
public class JWTUtilTest {
	/**
	 * 创建信息
	 * @param jwtUser
	 * @return
	 */
	public static String createTokenWithClaim(JwtUser jwtUser) throws SuperCodeException{

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
	public static JwtUser verifyToken(String token) throws SuperCodeException{
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
			JwtUser userInfo = mm.map(jwtU, JwtUser.class);
			return userInfo;
		} catch (TokenExpiredException ex){
			throw new SuperCodeException("jwt-token已过期",403);
		}catch (Exception exception){
			exception.printStackTrace();
			throw new SuperCodeException("获取授权信息失败");
		}
	}
	
	public static void main(String[] args) throws Exception {
		JWTUtil demo = new JWTUtil();
		H5LoginVO j = new H5LoginVO();
		j.setMemberId(83L);
		j.setMemberType((byte)1);
		j.setMobile("15728043579");
		j.setOrganizationId("86ff1c47b5204e88918cb89bbd739f12");
		j.setCustomerId("86ff1c47b5204e88918cb89bbd739f12");
		j.setOrganizationName("江苏浮华文化传播有限公司");
		j.setMemberName("15728043579");
		String createTokenWithClaim = demo.createTokenWithClaim(j);
		System.out.println(createTokenWithClaim);
		Thread.sleep(2000);

		H5LoginVO jwtUser = demo.verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJINSBTRUNVQ0lUWSIsImF1ZCI6IkFQUCIsImp3dFVzZXIiOiJ7XCJjdXN0b21lcklkXCI6XCI4NmZmMWM0N2I1MjA0ZTg4OTE4Y2I4OWJiZDczOWYxMlwiLFwibWVtYmVySWRcIjo4MixcIm1lbWJlck5hbWVcIjpcIjE1NzI4MDQzNTc5XCIsXCJtZW1iZXJUeXBlXCI6MSxcIm1vYmlsZVwiOlwiMTU3MjgwNDM1NzlcIixcIm9yZ2FuaXphdGlvbklkXCI6XCI4NmZmMWM0N2I1MjA0ZTg4OTE4Y2I4OWJiZDczOWYxMlwiLFwib3JnYW5pemF0aW9uTmFtZVwiOlwi5rGf6IuP5rWu5Y2O5paH5YyW5Lyg5pKt5pyJ6ZmQ5YWs5Y-4XCIsXCJyZWdpc3RlcmVkXCI6MH0iLCJpc3MiOiJKR1cgQ0pNIENPTVBBTlkiLCJleHAiOjE1Njg3MDc5MjB9.ylw5Af_un-3Iz3dErGyjxIk2t443T6fxBCRgrcMcXbw");

		System.out.println(JSONObject.toJSONString(jwtUser));


	}

}