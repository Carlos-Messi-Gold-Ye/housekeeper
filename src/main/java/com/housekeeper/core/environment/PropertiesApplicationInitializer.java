package com.housekeeper.core.environment;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author yezy
 * @since 2019/1/21
 */
public class PropertiesApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static Logger logger = LoggerFactory.getLogger(PropertiesApplicationInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        try {
            applicationContext.getEnvironment().setIgnoreUnresolvableNestedPlaceholders(true);
            loadProperties(applicationContext);
            loadYamlProperties(applicationContext);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void loadProperties(ConfigurableApplicationContext applicationContext) throws IOException {
        PropertiesLoader loader = new PropertiesLoader();
        loader.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:*.properties"));
        applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("properties", loader.load()));
    }

    private void loadYamlProperties(ConfigurableApplicationContext applicationContext) throws IOException {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new PathMatchingResourcePatternResolver().getResources("classpath*:config/*.yml"));
        applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("yaml", yamlPropertiesFactoryBean.getObject()));
    }

    private static class PropertiesLoader extends PropertySourcesPlaceholderConfigurer {
        Properties load() throws IOException {
            return mergeProperties();
        }
    }
}
