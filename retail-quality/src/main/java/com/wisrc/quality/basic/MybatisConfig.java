package com.wisrc.quality.basic;

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

@Configuration(value = "retailQualityMybatisConfig")
@MapperScan(basePackages = "com.wisrc.quality.webapp.dao", sqlSessionTemplateRef = "retailQualitySqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailQualityDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.quality")
    public DataSource retailQualityDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailQualitySqlSessionFactory")
    public SqlSessionFactory retailQualityDataSourceSqlSessionFactory(@Qualifier("retailQualityDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailQualityTransactionManager")
    public DataSourceTransactionManager retailQualityTransactionManager(@Qualifier("retailQualityDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailQualitySqlSessionTemplate")
    public SqlSessionTemplate retailQualitySqlSessionTemplate(@Qualifier("retailQualitySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
