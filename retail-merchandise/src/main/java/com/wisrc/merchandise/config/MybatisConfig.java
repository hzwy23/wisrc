package com.wisrc.merchandise.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration(value = "retailMerchandiseMybatisConfig")
@MapperScan(basePackages = "com.wisrc.merchandise.dao", sqlSessionTemplateRef = "retailMerchandiseSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailMerchandiseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.merchandise")
    public DataSource retailMerchandiseDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailMerchandiseSqlSessionFactory")
    public SqlSessionFactory retailMerchandiseDataSourceSqlSessionFactory(@Qualifier("retailMerchandiseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailMerchandiseTransactionManager")
    public DataSourceTransactionManager retailMerchandiseTransactionManager(@Qualifier("retailMerchandiseDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailMerchandiseSqlSessionTemplate")
    public SqlSessionTemplate retailMerchandiseSqlSessionTemplate(@Qualifier("retailMerchandiseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
