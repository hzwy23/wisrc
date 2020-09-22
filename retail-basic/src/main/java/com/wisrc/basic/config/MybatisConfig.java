package com.wisrc.basic.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration(value = "retailBasicMybatisConfig")
@MapperScan(basePackages = "com.wisrc.basic.dao", sqlSessionTemplateRef = "retailBasicSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailBasicDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.basic")
    public DataSource retailBasicDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailBasicSqlSessionFactory")
    public SqlSessionFactory retailSysDataSourceSqlSessionFactory(@Qualifier("retailBasicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailBasicTransactionManager")
    public DataSourceTransactionManager retailBasicTransactionManager(@Qualifier("retailBasicDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailBasicSqlSessionTemplate")
    public SqlSessionTemplate retailBasicSqlSessionTemplate(@Qualifier("retailBasicSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
