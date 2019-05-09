package com.housekeeper.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.housekeeper.core.log.action.ActionLog;
import com.housekeeper.core.log.action.ActionLogHolder;


/**
 * @author yezy
 * @since 2019/1/18
 */
public class PlatformFilter implements Filter {

    private static final String UTF8_CHARACTER_ENCODING = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(PlatformFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            filter((HttpServletRequest) request, (HttpServletResponse) response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(UTF8_CHARACTER_ENCODING);
        try {
            doInitializer();
            logger.debug("=== begin request processing ===");
            logRequest(request);
            chain.doFilter(request, response);
        } finally {
            logResponse(response);
            clear();
            logger.debug("=== finish request processing ===");
        }
    }

    private void doInitializer() {
        ActionLogHolder.get().initialize();
    }

    private void clear() {
        ActionLogHolder.get().clear();
    }

    private void logRequest(HttpServletRequest request) {
        logger.debug("Uri:{}", request.getRequestURI());
        logger.debug("Method:{}", request.getMethod());
        logger.debug("ContextPath:{}", request.getContextPath());
        ActionLog actionLog = ActionLogHolder.get().currentActionLog();
        actionLog.setHttpMethod(request.getMethod());
    }

    private void logResponse(HttpServletResponse response) {
        logger.debug("Start Log Http Response:");
        logger.debug("Content-Type:{}", response.getContentType());
        logger.debug("Status:{}", response.getStatus());
        ActionLogHolder.get().currentActionLog().setStatusCode(String.valueOf(response.getStatus()));
    }


    @Override
    public void destroy() {
        //do nothing
    }
}
