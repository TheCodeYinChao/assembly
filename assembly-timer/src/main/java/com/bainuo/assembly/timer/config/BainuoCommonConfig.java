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
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = "com.bainuo.assembly.timer.dao.common", sqlSessionTemplateRef  = "commonSqlSessionTemplate")
public class BainuoCommonConfig {
    @Bean(name = "commonDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.common")
    public DataSource commonDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "commonSqlSessionFactory")
    @Primary
    public SqlSessionFactory commonSqlSessionFactory(@Qualifier("commonDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybaties/common/*.xml"));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybaties/mybaties-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "commonTransactionManager")
    @Primary
    public DataSourceTransactionManager commonTransactionManager(@Qualifier("commonDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "commonSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate commonSqlSessionTemplate(@Qualifier("commonSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
