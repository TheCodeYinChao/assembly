package com.bainuo.assembly.timer.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * User: zyc
 * Date: 2018-03-14
 * Time: 19:14
 * Version ：1.0
 * Description:
 */
@Configuration
@MapperScan(basePackages = "com.bainuo.assembly.timer.dao.coupon", sqlSessionTemplateRef  = "couponSqlSessionTemplate")
public class BainuoCouponConfig {
    @Bean(name = "couponDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.coupon")
    public DataSource couponDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "couponSqlSessionFactory")
    public SqlSessionFactory couponSqlSessionFactory(@Qualifier("couponDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybaties/coupon/*.xml"));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybaties/mybaties-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "couponTransactionManager")
    public DataSourceTransactionManager couponTransactionManager(@Qualifier("couponDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "couponSqlSessionTemplate")
    public SqlSessionTemplate couponSqlSessionTemplate(@Qualifier("couponSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
