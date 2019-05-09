package com.housekeeper.core.web.interceptor;

import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.housekeeper.core.log.action.ActionLogHolder;
import com.housekeeper.core.web.RequestConstants;


/**
 * @author yezy
 * @since 2019/1/23
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (!initialized()) {
            Optional.ofNullable(getAction((HandlerMethod) handler)).ifPresent(ActionLogHolder.get().currentActionLog()::setAction);
            findCncRequestHeader(request).ifPresent(ActionLogHolder.get().currentActionLog()::setRequestId);
        }
        return true;
    }

    private Optional<String> findCncRequestHeader(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .filter(RequestConstants.HEADER_CNC_REQUEST_ID::equalsIgnoreCase).map(request::getHeader)
                .findFirst();
    }

    private boolean initialized() {
        return StringUtils.isNotEmpty(ActionLogHolder.get().currentActionLog().getAction());
    }

    private String getAction(HandlerMethod handler) {
        return String.format("%s-%s", getSimpleClassName(handler.getBeanType()), handler.getMethod().getName());
    }

    private String getSimpleClassName(Class<?> beanType) {
        if (isProxy(beanType)) {
            return beanType.getSuperclass().getSimpleName();
        }
        return beanType.getSimpleName();
    }

    private boolean isProxy(Class<?> beanType) {
        String name = beanType.getName();
        return name.contains("$$EnhancerByCGLIB") || name.contains("$$EnhancerBySpringCGLIB");
    }
}
