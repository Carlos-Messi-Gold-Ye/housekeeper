package com.housekeeper.core.web;

/**
 * @author yezy
 * @since  2019/1/24
 */
public final class ResponseConstants {

    private ResponseConstants() {
    }

    public static final int SUCCESS = 200; //成功
    public static final int API_ERROR_MESSAGE = 5001; //api错误，Data为null
    public static final int API_WARN_MESSAGE = 6001; //api二次确认告警，Data为null
    public static final int API_WARN_MESSAGE_LIST = 6002; //api二次确认告警，Data为List<?>
    public static final int VIOLATION_ERROR = 4001; //参数校验错误
    public static final int NO_FOUND_ERROR = 4004; //url路径错误
    public static final int ARGUMENT_TYPE_ERROR = 4002; //参数类型错误
    public static final int ARGUMENT_MISS_ERROR = 4005; //参数缺失错误
    public static final int METHOD_NOT_SUPPORTED_ERROR = 4000; //请求方法错误
}
