package com.housekeeper.page.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.housekeeper.core.environment.YamlPropertyLoaderFactory;
import com.housekeeper.core.exception.ApiErrorException;
import com.housekeeper.core.web.ResponseConstants;
import com.housekeeper.database.access.MybatisAccess;
import com.housekeeper.page.IPageParamHandler;
import com.housekeeper.page.IPageResultHandler;
import com.housekeeper.page.service.PageService;


/**
 * Page查询配置信息配置（含列表多条件查询）
 *
 * @author yezy
 * @since 2019/2/18
 *
 */
@Configuration
@ConditionalOnResource(resources = { "classpath:/config/page-configuration.yml" })
//Page配置文件
@PropertySource(value = { "classpath:/config/page-configuration.yml" }, factory = YamlPropertyLoaderFactory.class)
@EnableConfigurationProperties(PageProperties.class)
public class PageAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PageAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PageProperties pageProperties;

    /**
     * @return 配置的page信息map{key:pageId,value:page配置}
     */
    @Bean("pageConfigurationMap")
    public Map<String, PageConfiguration> pageConfigurationMap() {
        List<PageConfiguration> pageConfigurations = Optional.ofNullable(pageProperties.getPages()).orElse(new ArrayList<>()).stream().map(p -> {
            IPageParamHandler iPageParamHandler = getBean(p.getParamHandler(), IPageParamHandler.class);
            IPageResultHandler iPageResultHandler = getBean(p.getResultHandler(), IPageResultHandler.class);
            return new PageConfiguration(p.getPageId(), p.getSqlId(), iPageParamHandler, iPageResultHandler);
        }).collect(Collectors.toList());
        return pageConfigurations.stream().collect(Collectors.toMap(PageConfiguration::getPageId, p -> p));
    }

    @Bean
    public PageService pageService(@Qualifier("pageConfigurationMap") Map<String, PageConfiguration> pageConfigurationMap, @Qualifier("mybatisAccess") MybatisAccess mybatisAccess) {
        return new PageService(mybatisAccess, pageConfigurationMap);
    }

    private <T> T getBean(String clazz, Class<T> inf) {
        if (StringUtils.isNotBlank(clazz)) {
            Class<T> c = getClass(clazz, inf);
            return applicationContext.getAutowireCapableBeanFactory().createBean(c);
        }
        return null;
    }

    private Class getClass(String clazz, Class allow) {
        try {
            Class cc = Class.forName(clazz);
            Class<?>[] infs = cc.getInterfaces();
            if (ArrayUtils.isNotEmpty(infs)) {
                for (Class<?> inf : infs) {
                    if (inf.equals(allow)) {
                        return cc;
                    }
                }
            }
            throw new ClassNotFoundException(clazz + " does not implement the interface {}" + allow.getName()); //如果在类实现借口上没有找到要求的接口，则报错
        } catch (ClassNotFoundException e) {
            logger.error("PageAutoConfiguration配置加载，Class转化Bean失败:{}，详情：{}", e.getMessage(), e.getStackTrace());
            throw new ApiErrorException(ResponseConstants.API_ERROR_MESSAGE, e.getMessage(), e);
        }
    }
}
