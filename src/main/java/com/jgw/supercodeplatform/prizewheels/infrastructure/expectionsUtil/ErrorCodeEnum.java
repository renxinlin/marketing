package com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil;

public enum ErrorCodeEnum implements BaseErrorCode {
    /** 系统异常 */
    SYSTEM_ERROR(500 , "9000001" , "系统异常"),

    /** 服务异常 */
    SERVICE_ERROR(500 , "2000001" , "服务异常"),
    SERVICE_MQ_PRODUCER_ERROR(500 , "2000002" , "生产者消息队列服务异常"),
    SERVICE_MQ_CONSUMER_ERROR(500 , "2000003" , "消费者消息队列服务异常"),

    /** 参数异常 */
    PARAMS_ERROR(500 , "2000103" , "参数{}不正确"),


    NULL_ERROR(500 , "2000104" , "参数不存在"),
    BIZ_VALID_ERROR(500 , "2000105" , "业务异常"),
    NOT_EXITS_ERROR(500 , "2000106" , "数据不存在");
    ;

    private int code;
    private String msg;
    private String internalCode;

    private ErrorCodeEnum(int code, String internalCode, String msg){
        this.code = code;
        this.msg = msg;
        this.internalCode = internalCode;
    }

    @Override
    public int getHttpCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return msg;
    }

    @Override
    public String getInternalErrorCode() {
        return internalCode;
    }
}