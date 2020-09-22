package com.wisrc.purchase.basic;

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

@Configuration(value = "retailPurchaseOrderMybatisConfig")
@MapperScan(basePackages = "com.wisrc.purchase.webapp.dao", sqlSessionTemplateRef = "retailPurchaseOrderSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailPurchaseOrderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.purchase")
    public DataSource retailPurchaseOrderDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailPurchaseOrderSqlSessionFactory")
    public SqlSessionFactory retailPurchaseOrderDataSourceSqlSessionFactory(@Qualifier("retailPurchaseOrderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailPurchaseOrderTransactionManager")
    public DataSourceTransactionManager retailPurchaseOrderTransactionManager(@Qualifier("retailPurchaseOrderDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailPurchaseOrderSqlSessionTemplate")
    public SqlSessionTemplate retailPurchaseOrderSqlSessionTemplate(@Qualifier("retailPurchaseOrderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
