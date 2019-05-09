package com.housekeeper.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.housekeeper.core.exception.ErrorHandler;


/**
 * @author yezy
 * @since 2019/1/21
 */
public class ExceptionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ErrorHandler errorHandler;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        errorHandler.handle(ex);
    }
}
