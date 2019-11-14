package com.jgw.supercodeplatform.marketing.dao.weixin;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface WXPayTradeOrderMapper extends CommonSql{
static String allFields="Id id,PartnerTradeNo partnerTradeNo,OpenId openId,Amount amount,TradeDate tradeDate,TradeStatus tradeStatus,ReferenceRole referenceRole,"
		+ "OrganizationId organizationId,RemoteAddr remoteAddr,WinningCode winningCode,ReturnCode returnCode,ReturnMsg returnMsg,ResultCode resultCode,ErrCode errCode,ErrCodeDes errCodeDes, ActivityId activityId";

    @Select("select "+allFields+" from marketing_wx_trade_order where PartnerTradeNo=#{partner_trade_no}")
	WXPayTradeOrder selectByTradeNo(@Param("partner_trade_no")String partner_trade_no);
	
	
    @Insert(" INSERT INTO marketing_wx_trade_order(PartnerTradeNo,OpenId,Amount,TradeDate,TradeStatus,"
            + " OrganizationId,RemoteAddr,WinningCode,ReturnCode,ReturnMsg,ResultCode,ErrCode,ErrCodeDes,ReferenceRole,ActivityId) "
            + " VALUES(#{partnerTradeNo},#{openId},#{amount},now(),#{tradeStatus},#{organizationId},#{remoteAddr},#{winningCode},#{returnCode},#{returnMsg},"
            + "#{resultCode},#{errCode},#{errCodeDes},#{referenceRole},#{activityId}"
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
            + " Id = #{id} "
            + " </where>"
            + " </script>")
	void update(WXPayTradeOrder wXTradeNo);


    

	
	
	 @Select(startScript
	 		+ "select "+allFields+" from marketing_wx_trade_order"
	 		+startWhere
	 		+ search
	 		+endWhere
	 		+endScript)
	List<WXPayTradeOrder> commonSearch(DaoSearch daoSearch);

	/**
	 * 获取组织交易成功的金额
	 * @param organizationId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select(" select sum(Amount) from marketing_wx_trade_order where organizationId = #{organizationId} " +
			" and TradeStatus = 1 " +
			" and TradeDate between #{startDate} and #{endDate} ")
	Integer getOrganizationIdAmoutByDate(String organizationId, Date startDate, Date endDate);

	@Select("SELECT "+allFields+" FROM marketing_wx_trade_order WHERE WinningCode = #{winningCode} AND PartnerTradeNo = #{tradeNo}")
	WXPayTradeOrder selectByCodeId(@Param("tradeNo") String tradeNo, @Param("winningCode") String winningCode);

	//查找到支付失败的订单，
//	@Select("SELECT "+allFields+" FROM marketing_wx_trade_order WHERE TradeDate > #{limitDate} AND TradeStatus = 2 AND ReSend = 0 AND ActivityId = #{activityId}")
	@Select("SELECT "+allFields+" FROM marketing_wx_trade_order WHERE TradeDate > #{limitDate} AND TradeStatus = 2 AND ReSend = 0")
	List<WXPayTradeOrder> searchFailOrder(@Param("limitDate")Date limitDate);

}
