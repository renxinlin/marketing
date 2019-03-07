package com.jgw.supercodeplatform.marketing.pojo.pay;
/**
 * 企业付款订单表
 * @author czm
 *
 */
public class WXPayTradeOrder {
	private Long id;

	private String partnerTradeNo;//订单号
    
	private String openId;//客户openid

	private Integer amount;//支付金额单位 分

	private String tradeDate;//交易时间

	private Byte tradeStatus;// 0未支付 1支付成功 2支付失败
	
	private String organizationId;//组织id
    /**
     * SUCCESS/FAIL
                   此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
	private String returnCode;
	/**
	 * 返回信息，如非空，为错误原因 
                签名失败 
                参数格式校验错误
	 */
	private String returnMsg;
	
	/**
	 * SUCCESS/FAIL，注意：当状态为FAIL时，存在业务结果未明确的情况。如果如果状态为FAIL，请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果
	 */
	private String resultCode;
	
	/**
	 * 错误码信息，注意：出现未明确的错误码时（SYSTEMERROR等），请务必用原商户订单号重试，或通过查询接口确认此次付款的结果。
	 */
	private String errCode;
	
	/**
	 * 结果信息描述
	 */
	private String errCodeDes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}

	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Byte getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(Byte tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

}
