package com.wisrc.replenishment.basic;

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

@Configuration(value = "retailReplenishmentMybatisConfig")
@MapperScan(basePackages = "com.wisrc.replenishment.webapp.dao", sqlSessionTemplateRef = "retailReplenishmentSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailReplenishmentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.replenishment")
    public DataSource retailReplenishmentDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailReplenishmentSqlSessionFactory")
    public SqlSessionFactory retailReplenishmentDataSourceSqlSessionFactory(@Qualifier("retailReplenishmentDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailReplenishmentTransactionManager")
    public DataSourceTransactionManager retailReplenishmentTransactionManager(@Qualifier("retailReplenishmentDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailReplenishmentSqlSessionTemplate")
    public SqlSessionTemplate retailReplenishmentSqlSessionTemplate(@Qualifier("retailReplenishmentSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
