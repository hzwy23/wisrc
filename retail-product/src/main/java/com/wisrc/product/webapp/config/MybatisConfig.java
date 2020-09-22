package com.wisrc.product.webapp.config;

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

@Configuration(value = "retailProductMybatisConfig")
@MapperScan(basePackages = "com.wisrc.product.webapp.dao", sqlSessionTemplateRef = "retailProductSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailProductDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.product")
    public DataSource retailProductDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailProductSqlSessionFactory")
    public SqlSessionFactory retailProductDataSourceSqlSessionFactory(@Qualifier("retailProductDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailProductTransactionManager")
    public DataSourceTransactionManager retailProductransactionManager(@Qualifier("retailProductDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailProductSqlSessionTemplate")
    public SqlSessionTemplate retailProductSqlSessionTemplate(@Qualifier("retailProductSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
