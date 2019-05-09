package com.housekeeper.core.exception;


import com.housekeeper.core.web.ResponseConstants;

/**
 * @author yezy
 * @since  2019/1/24
 * 自定义内部异常
 */
public class ApiErrorException extends RuntimeException {

    private int code;
    private String message;
    private Object data;

    /**
     * 直接message错误提示，没有其他data数据展示
     * @param message 错误信息
     */
    public ApiErrorException(String message) {
        super(message);
        this.code = ResponseConstants.API_ERROR_MESSAGE;
        this.message = message;
    }

    /**
     * message错误提示+data数据组装
     * @param code 根据指定code搭配指定data
     * @param message 错误信息
     * @param data 搭配指定data
     * @see ResponseConstants
     */
    public ApiErrorException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
