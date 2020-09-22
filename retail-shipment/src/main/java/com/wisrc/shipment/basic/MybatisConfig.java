package com.wisrc.shipment.basic;

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

@Configuration(value = "retailShipmentMybatisConfig")
@MapperScan(basePackages = "com.wisrc.shipment.webapp.dao", sqlSessionTemplateRef = "retailShipmentSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailShipmentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.shipment")
    public DataSource retailShipmentDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailShipmentSqlSessionFactory")
    public SqlSessionFactory retailShipmentDataSourceSqlSessionFactory(@Qualifier("retailShipmentDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailShipmentTransactionManager")
    public DataSourceTransactionManager retailShipmentTransactionManager(@Qualifier("retailShipmentDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailShipmentSqlSessionTemplate")
    public SqlSessionTemplate retailSystemSqlSessionTemplate(@Qualifier("retailShipmentSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
