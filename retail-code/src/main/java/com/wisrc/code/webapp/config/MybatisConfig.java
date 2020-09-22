package com.wisrc.code.webapp.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration(value = "retailCodeMybatisConfig")
@MapperScan(basePackages = "com.wisrc.code.webapp.dao", sqlSessionTemplateRef = "retailCodeSqlSessionTemplate")
public class MybatisConfig {



    @Bean(name = "retailCodeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.code")
    public DataSource retailCodeDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailCodeSqlSessionFactory")
    public SqlSessionFactory retailCodeDataSourceSqlSessionFactory(@Qualifier("retailCodeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailCodeTransactionManager")
    public DataSourceTransactionManager retailCodeTransactionManager(@Qualifier("retailCodeDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailCodeSqlSessionTemplate")
    public SqlSessionTemplate retailCodeSqlSessionTemplate(@Qualifier("retailCodeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
