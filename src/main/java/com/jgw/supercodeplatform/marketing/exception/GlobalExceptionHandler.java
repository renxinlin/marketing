package com.jgw.supercodeplatform.marketing.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.exception.base.UserSqlException;

/**
 *
 * 功能描述: 全局异常接收
 *
 * @auther: corbett
 * @date: 2018/9/6 13:41
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RestResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
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
        logger.error("参数解析失败", e);
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
        logger.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), message, null);
        return RestResult;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public RestResult handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), message, null);
        return RestResult;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败", e);
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
        logger.error("参数验证失败", e);
        RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage(), null);
        return RestResult;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidFormatException.class)
    public RestResult invalidFormatException(InvalidFormatException e) {
        logger.error("参数验证失败", e);
        RestResult RestResult = new RestResult(HttpStatus.BAD_REQUEST.value(), e.getCause().getLocalizedMessage() == null ? e.getMessage() : e.getCause().getLocalizedMessage(), null);
        return RestResult;
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        RestResult RestResult = new RestResult(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), null);
        return RestResult;
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RestResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        RestResult RestResult = new RestResult(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), null);
        return RestResult;
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public RestResult handleException(Exception e) {
        logger.error("系统异常:" + e.getClass(), e);
        RestResult RestResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "server error", e.getMessage());
        return RestResult;
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public RestResult handleException(DataIntegrityViolationException e) {
        logger.error("操作数据库出现异常:", e);
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
        logger.error("自义定异常：" + e.getClass().getName(), e);
        RestResult RestResult = new RestResult(e.getStatus() == 0 ? HttpStatus.INTERNAL_SERVER_ERROR.value() : e.getStatus(), e.getMessage(), null);
        return RestResult;
    }

    /**
     * 自定义异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserSqlException.class)
    public RestResult commonException(UserSqlException e) {
        logger.error("自义定异常：" + e.getClass().getName(), e);
        RestResult RestResult = new RestResult(e.getStatus() == 0 ? HttpStatus.INTERNAL_SERVER_ERROR.value() : e.getStatus(), e.getMessage(), null);
        return RestResult;
    }
}
