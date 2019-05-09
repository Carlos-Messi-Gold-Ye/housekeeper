package com.housekeeper.core.log.action;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.housekeeper.core.log.LogConstants;
import com.housekeeper.core.log.LogValuePair;


/**
 * @author yezy
 * @since 2018/4/18
 */
public class ActionLog {

    public static final Logger LOGGER = LoggerFactory.getLogger(ActionLog.class);

    private final Date requestDate; //请求的时间

    private String requestId; //请求的标识

    private String action; //请求对象

    private Status status;

    private String httpMethod;

    private String statusCode;

    private String errorMsg; //错误信息

    private String traceLogPath; //对应的traceLog日志地址 ${year}/${month}/${day}/${ApiName}/${hour}-${requestId}-${identifier}.log

    private long elapsedTime;

    private final Map<String, String> additionalContext = new HashMap<>(); //额外需要记录的日志

    ActionLog(Date requestDate) {
        this.requestDate = requestDate;
        this.requestId = UUID.randomUUID().toString();
        this.status = Status.SUCCESS;
    }

    public enum Status {
        SUCCESS, ERROR,
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTraceLogPath() {
        return traceLogPath;
    }

    public void setTraceLogPath(String traceLogPath) {
        this.traceLogPath = traceLogPath;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    Map<String, String> getAdditionalContext() {
        return additionalContext;
    }

    private static final String RESULT_CODE = "StatusCode";
    private static final String ELAPSED_TIME = "ElapsedTime";
    private static final String ERROR_MSG = "Msg";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(); //
        builder.append(DateTimeFormatter.ofPattern(LogConstants.DEFAULT_LOG_DATE_FORMAT).format(LocalDateTime.ofInstant(this.getRequestDate().toInstant(), ZoneId.systemDefault()))) //
                .append(LogConstants.SPLITTER) //
                .append(ObjectUtils.toString(this.getStatus())).append(LogConstants.SPLITTER) //
                .append(ObjectUtils.toString(this.getRequestId())).append(LogConstants.SPLITTER) //
                .append(Optional.ofNullable(this.getAction()).orElse(LogConstants.EMPTY_STR)) //
                .append(LogConstants.SPLITTER) //
                .append(LogValuePair.of(RESULT_CODE, this.getStatusCode())) //
                .append(LogConstants.SPLITTER) //
                .append(LogValuePair.of(ELAPSED_TIME, this.getElapsedTime())) //
                .append(LogConstants.SPLITTER) //
                .append(LogValuePair.of(ERROR_MSG, this.getErrorMsg(), LogConstants.MAX_LOG_FIELD_VALUE_LENGTH)) //
                .append(LogConstants.SPLITTER) //
                .append(Optional.ofNullable(this.getTraceLogPath()).orElse(LogConstants.EMPTY_STR));
        for (Map.Entry<String, String> logKeyValue : this.getAdditionalContext().entrySet()) {
            builder.append(LogConstants.SPLITTER).append(LogValuePair.of(logKeyValue.getKey(), logKeyValue.getValue()));
        }
        return builder.toString();
    }
}
