package com.housekeeper.core.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.housekeeper.core.web.ResponseBody;
import com.housekeeper.core.web.ResponseConstants;

/**
 * @author yezy
 * @since  2019/1/24
 * 异常统一处理Handler
 *******************
 *  404拦截处理：修改配置：spring.mvc.throw-exception-if-no-handler-found: true (默认false) spring.mvc.static-path-pattern: /statics/** (默认/**)
 *******************
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private static final String LINE_BREAK = "\n";

    /**
     * 200 内部异常信息
     * @param ex ApiErrorException
     * @return ResponseBody
     */
    @ExceptionHandler(value = { ApiErrorException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseBody apiErrorExceptionHandler(ApiErrorException ex) {
        logger.info(ex.getMessage());
        return ResponseBody.error(ex.getCode()).message(ex.getMessage()).data(ex.getData());
    }

    /**
     * 200 内部二次确认异常信息
     * @param ex ApiErrorException
     * @return ResponseBody
     */
    @ExceptionHandler(value = { ApiWarnException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseBody apiErrorExceptionHandler(ApiWarnException ex) {
        logger.info(ex.getMessage());
        return ResponseBody.error(ex.getCode()).message(ex.getMessage()).data(ex.getData());
    }

    /**
     * 400 数据校验异常
     * @param ex ConstraintViolationException
     * @return ResponseBody
     */
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody vonstraintViolationExceptionHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> allErrors = ex.getConstraintViolations();
        List<String> errorMessages = CollectionUtils.isEmpty(allErrors) ? new ArrayList<>() : allErrors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        String errorMessage = StringUtils.join(errorMessages, LINE_BREAK);
        logger.warn(errorMessage, ex);
        return ResponseBody.error(ResponseConstants.VIOLATION_ERROR).message(errorMessage);
    }

    /**
     * 400 controller数据校验异常
     * @param ex MethodArgumentNotValidException
     * @return ResponseBody
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        List<String> errorMessages = CollectionUtils.isEmpty(allErrors) ? new ArrayList<>() : allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = StringUtils.join(errorMessages, LINE_BREAK);
        logger.warn(errorMessage, ex);
        return ResponseBody.error(ResponseConstants.VIOLATION_ERROR).message(errorMessage);
    }

    /**
     * 400 controller参数类型异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        String errorMessage = ex.getMessage();
        logger.warn(errorMessage, ex);
        return ResponseBody.error(ResponseConstants.ARGUMENT_TYPE_ERROR).message(errorMessage);
    }

    /**
     * 400 controller参数缺失异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { HttpMessageNotReadableException.class, MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody httpMessageNotReadableExceptionHandler(Exception ex) {
        String errorMessage = ex.getMessage();
        logger.warn(errorMessage, ex);
        return ResponseBody.error(ResponseConstants.ARGUMENT_MISS_ERROR).message(errorMessage);
    }

    /**
     * 400 Request method 'xxx' not supported
     * @param ex HttpRequestMethodNotSupportedException
     * @return ResponseBody
     */
    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseBody.error(ResponseConstants.METHOD_NOT_SUPPORTED_ERROR).message(ex.getMessage());
    }

    /**
     * 404 找不到URL路径
     * @param ex No handler found
     * @return ResponseBody
     */
    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBody noFoundExceptionHandler(NoHandlerFoundException ex) {
        logger.warn(ex.getMessage(), ex);
        return ResponseBody.error(ResponseConstants.NO_FOUND_ERROR).message(ex.getMessage());
    }

    /**
     * 500
     * Throwable 未知异常
     * @param ex Throwable
     * @return ResponseBody
     */
    @ExceptionHandler(value = { Throwable.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody noknownThrowableHandler(Throwable ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseBody.error(ResponseConstants.API_ERROR_MESSAGE).message("系统错误，请联系系统管理员。");
    }

}