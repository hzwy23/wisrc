package com.wisrc.supplier.webapp.config;

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

@Configuration(value = "retailSupplierMybatisConfig")
@MapperScan(basePackages = "com.wisrc.supplier.webapp.dao", sqlSessionTemplateRef = "retailSupplierSqlSessionTemplate")
public class MybatisConfig {



    @Bean(name = "retailSupplierDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.supplier")
    public DataSource retailSupplierDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailSupplierSqlSessionFactory")
    public SqlSessionFactory retailSupplierDataSourceSqlSessionFactory(@Qualifier("retailSupplierDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailSupplierTransactionManager")
    public DataSourceTransactionManager retailSupplierTransactionManager(@Qualifier("retailSupplierDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailSupplierSqlSessionTemplate")
    public SqlSessionTemplate retailSupplierSqlSessionTemplate(@Qualifier("retailSupplierSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
