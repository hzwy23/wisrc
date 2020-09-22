package com.wisrc.order.basic;

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

@Configuration(value = "retailOrderMybatisConfig")
@MapperScan(basePackages = "com.wisrc.order.webapp.dao", sqlSessionTemplateRef = "retailOrderSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailOrderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.order")
    public DataSource retailOrderDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailOrderSqlSessionFactory")
    public SqlSessionFactory retailOrderDataSourceSqlSessionFactory(@Qualifier("retailOrderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailOrderTransactionManager")
    public DataSourceTransactionManager retailOrderTransactionManager(@Qualifier("retailOrderDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailOrderSqlSessionTemplate")
    public SqlSessionTemplate retailOrderSqlSessionTemplate(@Qualifier("retailOrderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
