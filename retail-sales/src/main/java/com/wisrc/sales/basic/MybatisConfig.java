package com.wisrc.sales.basic;

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

@Configuration(value = "retailSalesMybatisConfig")
@MapperScan(basePackages = "com.wisrc.sales.webapp.dao", sqlSessionTemplateRef = "retailSalesSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailSalesDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.sales")
    public DataSource retailSalesDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailSalesSqlSessionFactory")
    public SqlSessionFactory retailSalesDataSourceSqlSessionFactory(@Qualifier("retailSalesDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailSalesTransactionManager")
    public DataSourceTransactionManager retailSalesTransactionManager(@Qualifier("retailSalesDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailSalesSqlSessionTemplate")
    public SqlSessionTemplate retailSalesSqlSessionTemplate(@Qualifier("retailSalesSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
