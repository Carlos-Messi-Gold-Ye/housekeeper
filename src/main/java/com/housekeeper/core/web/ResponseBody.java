package com.housekeeper.core.web;

/**
 * @author yezy
 * @since  2019/1/24
 * 请求反馈体
 */
public class ResponseBody {

    private int code;
    private String message;
    private Object data;

    public static ResponseBody success() {
        return new ResponseBody().code(ResponseConstants.SUCCESS).message("操作成功！");
    }

    public static ResponseBody error(int code) {
        return new ResponseBody().code(code);
    }

    public ResponseBody code(int code) {
        this.code = code;
        return this;
    }

    public ResponseBody message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBody data(Object data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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
