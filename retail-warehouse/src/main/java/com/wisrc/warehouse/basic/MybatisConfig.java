package com.wisrc.warehouse.basic;

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

@Configuration(value = "retailWarehouseMybatisConfig")
@MapperScan(basePackages = "com.wisrc.warehouse.webapp.dao", sqlSessionTemplateRef = "retailWarehouseSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailWarehouseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.warehouse")
    public DataSource retailWarehouseDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailWarehouseSqlSessionFactory")
    public SqlSessionFactory retailWarehouseDataSourceSqlSessionFactory(@Qualifier("retailWarehouseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailWarehouseTransactionManager")
    public DataSourceTransactionManager retailWarehouseTransactionManager(@Qualifier("retailWarehouseDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailWarehouseSqlSessionTemplate")
    public SqlSessionTemplate retailWarehouseSqlSessionTemplate(@Qualifier("retailWarehouseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
