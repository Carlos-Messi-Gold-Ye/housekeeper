package com.housekeeper;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.housekeeper.core.web.DefaultWebMvcConfiguration;


/**
 * @author yezy
 * @since 2018/8/3
 */
@SpringBootConfiguration
@ComponentScan(basePackageClasses = TestConfig.class)
public class TestConfig extends DefaultWebMvcConfiguration {

    @Override
    protected void registryInterceptors(InterceptorRegistry registry) {
    }
}
