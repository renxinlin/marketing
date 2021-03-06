package com.jgw.supercodeplatform.marketing.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.exception.base.UserSqlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * 功能描述: 全局异常接收
 *
 * @auther: corbett
 * @date: 2018/9/6 13:41
 */
@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@Value("${marketing.global.expetion.print:false}")
	private boolean showErrorInfo;

 
	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public RestResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		log.warn("缺少请求参数", e);
		RestResult RestResult = new RestResult();
		RestResult.setState(HttpStatus.BAD_REQUEST.value());
		RestResult.setMsg(e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage());
		return RestResult;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public RestResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.warn("参数解析失败", e);
		RestResult RestResult = new RestResult();
		RestResult.setState(HttpStatus.BAD_REQUEST.value());
		RestResult.setMsg("could_not_read_json");
		return RestResult;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public RestResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.warn("参数验证失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String code = error.getDefaultMessage();
		RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), code, null);
		return RestResult;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BindException.class)
	public RestResult handleBindException(BindException e) {
		log.warn("参数绑定失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String code = error.getDefaultMessage();
		RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), code, null);
		return RestResult;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ConstraintViolationException.class)
	public RestResult handleServiceException(ConstraintViolationException e) {
		log.warn("参数验证失败", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), message, null);
		return RestResult;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ValidationException.class)
	public RestResult handleValidationException(ValidationException e) {
		log.warn("参数验证失败", e);
		RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage(), null);
		return RestResult;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(InvalidFormatException.class)
	public RestResult invalidFormatException(InvalidFormatException e) {
		log.warn("参数验证失败", e);
		RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage(), null);
		return RestResult;
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public RestResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.warn("不支持当前请求方法", e);
		RestResult RestResult = new RestResult(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), null);
		return RestResult;
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public RestResult handleHttpMediaTypeNotSupportedException(Exception e) {
		log.warn("不支持当前媒体类型", e);
		RestResult RestResult = new RestResult(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), null);
		return RestResult;
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Exception.class)
	public RestResult handleException(Exception e) {
		log.warn("系统异常:" + e.getClass(), e);
		if(showErrorInfo){
			RestResult RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "server error", e.getMessage());
			return RestResult;
		}
		RestResult RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端异常", "服务端异常");
		return RestResult;
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(RuntimeException.class)
	public RestResult runtimeException(RuntimeException e) {
		log.warn("运行时异常：" + e.getClass().getName(), e);
		if(showErrorInfo){
			RestResult RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),  e.getMessage(), e.getMessage());
			return RestResult;
		}
		RestResult RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端运行异常", "服务端运行异常");
		return RestResult;
	}


	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BizRuntimeException.class)
	public RestResult bizException(BizRuntimeException e) {
		log.warn("运行时异常：" + e.getClass().getName(), e);
		RestResult restResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),  e.getMessage(), e.getObject());
		return restResult;
	}




	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(PrizeWheelsWeixinPayException.class)
	public RestResult bizException(PrizeWheelsWeixinPayException e) {
		log.warn("运行时异常：" + e.getClass().getName(), e);
		HashMap<Object, Object> weixinPayError = new HashMap<>();
		weixinPayError.put("weixinPayError",1);
		RestResult restResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),  e.getMessage(),weixinPayError );
		return restResult;
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(IllegalStateException.class)
	public RestResult bizException(IllegalStateException e) {
		log.warn("运行时异常：" + e.getClass().getName(), e);
		RestResult restResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),  e.getMessage(), null);
		return restResult;
	}

	/**
     * 销售员动态订货表
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TableSaveException.class)
    public RestResult handleTableSaveException(TableSaveException e) {
        RestResult RestResult;
        String eMessages = e.getMessage();
        if (eMessages.contains("doesn't exist")) {
            RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请先【设置表单】", "请先【设置表单】");
        } else {
            RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage(), null);
        }
        return RestResult;
    }

	/**
	 * 操作数据库出现异常:名称重复，外键关联
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public RestResult handleException(DataIntegrityViolationException e) {
		log.error("操作数据库出现异常:", e);
		RestResult RestResult;

		String eMessages = e.getMessage();

		String eLocalMessages = e.getCause().getLocalizedMessage();

		if (eMessages.indexOf("Duplicate entry") > 0) {
			RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "存在相同key值", eMessages);
		} else {
			RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage(), null);
		}
		return RestResult;
	}


	/**
	 * 自定义异常
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(SuperCodeException.class)
	public RestResult codePlatformException(SuperCodeException e) {
		log.warn("自义定异常：" + e.getClass().getName(), e);
		RestResult RestResult = new RestResult(e.getStatus() == 0 ? HttpStatus.INTERNAL_SERVER_ERROR.value() : e.getStatus(), e.getMessage(), null);
		return RestResult;
	}


	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(PrizeWheelsForWxErcodeException.class)
	public RestResult prizeWheelsException(PrizeWheelsForWxErcodeException e) {
		log.warn("自义定异常：" + e.getClass().getName(), e);
		HashMap hashMap = new HashMap<>();
		hashMap.put("scanType",1); // 前端根据该字段提示相关错误
		hashMap.put("wxErcode",e.getMessage());
		RestResult RestResult = new RestResult(200,"来迟啦,码已经被扫啦!" , hashMap); // 状态码前端需求
		return RestResult;
	}


	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(NotGetPrizeWheelsException.class)
	public RestResult notGetPrizeWheelsException(NotGetPrizeWheelsException e) {
		log.warn("大转盘概率计算器计算未获取奖：" + e.getClass().getName(), e);
		RestResult RestResult = new RestResult(200,"未中奖!!!" , e.getMessage());// 状态码前端需求
		return RestResult;
	}




	/**
	 * 自定义异常
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(SuperCodeExtException.class)
	public RestResult codePlatformException(SuperCodeExtException e) {
		log.warn("自义定异常：" + e.getClass().getName(), e);
		RestResult RestResult = new RestResult(e.getStatus() == 0 ? HttpStatus.INTERNAL_SERVER_ERROR.value() : e.getStatus(), e.getMessage(), null);
		return RestResult;
	}





	/**
	 * 自定义异常
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(UserSqlException.class)
	public RestResult<?> commonException(UserSqlException e) {
		log.warn("自义定异常：" + e.getClass().getName(), e);
		RestResult<?> RestResult = new RestResult<>(e.getStatus() == 0 ? HttpStatus.INTERNAL_SERVER_ERROR.value() : e.getStatus(), e.getMessage(), null);
		return RestResult;
	}


	/**
	 * 401 - 业务401
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(UserExpireException.class)
	public RestResult<?> handleMissingUserExpireException(UserExpireException e) {
		// 未登录异常
		log.warn("会员未登录异常{}",e.getMessage());
		RestResult<?> restResult = new RestResult<>();
		restResult.setState(401);
		restResult.setMsg(e.getMessage());
		restResult.setResults(null);
		return restResult;
	}



	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(SalerLotteryException.class)
	public RestResult<LotteryResultMO> handlerSalerLotteryException(SalerLotteryException e) {
		log.warn("会员未登录异常{}",e.getMessage());
		RestResult<LotteryResultMO> restResult = new RestResult<>();
		// 前端格式:不可修改
		restResult.setState(200);
		restResult.setMsg(e.getMessage());
		// 前端格式:不可修改
		restResult.setResults(new LotteryResultMO(e.getMessage()));
		return restResult;
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(LotteryException.class)
	public RestResult<LotteryResultMO> handlerSalerLotteryException(LotteryException e) {
		LotteryResultMO lotteryResultMO = new LotteryResultMO(); 
		RestResult<LotteryResultMO> restResult = new RestResult<>();
		restResult.setState(e.getStatus() == 0?HttpStatus.INTERNAL_SERVER_ERROR.value():e.getStatus());
		restResult.setMsg(e.getMessage());
		lotteryResultMO.setWinnOrNot(0);
		lotteryResultMO.setMsg(e.getMessage());
		restResult.setResults(lotteryResultMO);
		return restResult;
	}

}
