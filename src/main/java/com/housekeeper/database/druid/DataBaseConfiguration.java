/**
 * @(#)DBConfig.java 2017/6/19
 * <p>
 * Copyright 2000-2017 by ChinanetCenter Corporation.
 * <p>
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 */
package com.housekeeper.database.druid;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.housekeeper.database.access.JDBCAccess;
import com.housekeeper.database.access.JPAAccess;
import com.housekeeper.database.access.MybatisAccess;


/**
 * @author yezy
 * @since 2017/6/19
 */
@Configuration
@AutoConfigureAfter(DruidAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class DataBaseConfiguration {

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(dataSource);
        managerFactoryBean.setPackagesToScan(druidProperties.getScanPackages());
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(MySQL5Dialect.class.getName());
        Optional.ofNullable(druidProperties.getAutoGenerateDdl()).ifPresent(jpaVendorAdapter::setGenerateDdl);
        Optional.ofNullable(druidProperties.getShowSql()).ifPresent(jpaVendorAdapter::setShowSql);
        managerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        managerFactoryBean.setPersistenceUnitName("entityManager");
        return managerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager jpaTransactionManager(@Qualifier("entityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setPersistenceUnitName("entityManager");
        return transactionManager;
    }

    @Bean
    public JPAAccess jpaAccess() {
        return new JPAAccess() {
            @Override
            @PersistenceContext(unitName = "entityManager")
            public void setEntityManager(EntityManager entityManager) {
                super.setEntityManager(entityManager);
            }
        };
    }

    @Bean
    public JDBCAccess jdbcAccess(@Qualifier("dataSource") DataSource dataSource) {
        JDBCAccess jdbcAccess = new JDBCAccess();
        jdbcAccess.setDataSource(dataSource);
        return jdbcAccess;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTransactionFactory(new JdbcTransactionFactory());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public MybatisAccess mybatisAccess(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new MybatisAccess(sqlSessionFactory);
    }
}
