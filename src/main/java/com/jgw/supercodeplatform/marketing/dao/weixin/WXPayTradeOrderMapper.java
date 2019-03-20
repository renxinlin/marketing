package com.jgw.supercodeplatform.marketing.dao.weixin;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;

@Mapper
public interface WXPayTradeOrderMapper {
static String allFields="Id id,PartnerTradeNo partnerTradeNo,OpenId openId,Amount amount,TradeDate tradeDate,TradeStatus tradeStatus,"
		+ "OrganizationId organizationId,ReturnCode returnCode,ReturnMsg returnMsg,ResultCode resultCode,ErrCode errCode,ErrCodeDes errCodeDes";

    @Select("select "+allFields+" from marketing_wx_trade_order where PartnerTradeNo=#{partner_trade_no}")
	WXPayTradeOrder selectByTradeNo(@Param("partner_trade_no")String partner_trade_no);
	
	
    @Insert(" INSERT INTO marketing_wx_trade_order(PartnerTradeNo,OpenId,Amount,TradeDate,TradeStatus,"
            + " OrganizationId,ReturnCode,ReturnMsg,ResultCode,ErrCode,ErrCodeDes) "
            + " VALUES(#{partnerTradeNo},#{openId},#{amount},now(),#{tradeStatus},#{organizationId} ,#{returnCode},#{returnMsg},"
            + "#{resultCode},#{errCode},#{errCodeDes}"
            + ")")
	int insert(WXPayTradeOrder tradeOrder);

    @Update(" <script>"
            + " UPDATE marketing_wx_trade_order "
            + " <set>"
            + " <if test='tradeStatus !=null '> TradeStatus = #{tradeStatus} ,</if> "
            + " <if test='returnCode !=null and returnCode != &apos;&apos; '> ReturnCode = #{returnCode} ,</if> "
            + " <if test='returnMsg !=null and returnMsg != &apos;&apos; '> ReturnMsg = #{returnMsg} ,</if> "
            + " <if test='resultCode !=null and resultCode != &apos;&apos; '> ResultCode = #{resultCode} ,</if> "
            + " <if test='errCode !=null and errCode != &apos;&apos; '> ErrCode = #{errCode} ,</if> "
            + " <if test='errCodeDes !=null and errCodeDes != &apos;&apos; '> ErrCodeDes = #{errCodeDes} ,</if> "
            + " </set>"
            + " <where> "
            + " <if test='id !=null and id != &apos;&apos; '> and Id = #{id} </if>"
            + " </where>"
            + " </script>")
	void update(WXPayTradeOrder wXTradeNo);

}
