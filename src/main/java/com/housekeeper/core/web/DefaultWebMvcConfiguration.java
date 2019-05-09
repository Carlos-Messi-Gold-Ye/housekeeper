package com.housekeeper.core.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.housekeeper.core.exception.ErrorHandler;
import com.housekeeper.core.web.filter.PlatformFilter;
import com.housekeeper.core.web.interceptor.ExceptionInterceptor;
import com.housekeeper.core.web.interceptor.RequestInterceptor;


/**
 * @author yezy
 * @since 2019/1/18
 */
public abstract class DefaultWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setOrder(1);
        bean.setFilter(new PlatformFilter());
        bean.addUrlPatterns("/*");
        bean.setName("platformFilter");
        return bean;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ErrorHandler();
    }

    @Bean
    public ExceptionInterceptor exceptionInterceptor() {
        return new ExceptionInterceptor();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exceptionInterceptor()); //异常处理仅处理后置, 功能放在逆向流程的末尾
        registryInterceptors(registry);
        registry.addInterceptor(requestInterceptor()); //只处理前置的请求数据采集放在流程末尾
    }

    protected abstract void registryInterceptors(InterceptorRegistry registry);

}
