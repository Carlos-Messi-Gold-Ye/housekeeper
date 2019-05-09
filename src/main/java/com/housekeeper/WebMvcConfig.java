package com.housekeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.housekeeper.core.util.I18nUtil;
import com.housekeeper.core.web.DefaultWebMvcConfiguration;


@Configuration
public class WebMvcConfig extends DefaultWebMvcConfiguration {

    @Bean
    public static PropertyPlaceholderConfigurer propertyLoader() throws IOException {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        Resource[] radResources = new PathMatchingResourcePatternResolver().getResources("classpath*:/portal.properties"); //加载RAD包内部配置
        Resource[] appResources = new PathMatchingResourcePatternResolver().getResources("classpath:/config/application.properties"); //加载项目配置
        placeholderConfigurer.setLocations(Stream.of(radResources, appResources).flatMap(Arrays::stream).toArray(Resource[]::new));
        placeholderConfigurer.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        placeholderConfigurer.setIgnoreResourceNotFound(true);
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // add custom resource handlers
    }

    @Override
    protected void registryInterceptors(InterceptorRegistry registry) {
        // add custom interceptor
    }

//    @Bean
//    public SpringServiceManager serviceManager(@Qualifier("propertyLoader") PropertyPlaceholderConfigurer propertyLoader) {
//        return new SpringServiceManager(propertyLoader);
//    }

    @Bean
    public LocalValidatorFactoryBean validator(@Qualifier("messageSource") MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames(messageSourceBasenames());
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setCacheSeconds(60);
        I18nUtil.setMessageSource(messageSource);
        return messageSource;
    }

    private String[] messageSourceBasenames() {
        return new String[]{"classpath:valid-messages", "classpath:org/hibernate/validator/ValidationMessages", "classpath:i18n/common", "classpath:i18n/config-form"};
    }
}
