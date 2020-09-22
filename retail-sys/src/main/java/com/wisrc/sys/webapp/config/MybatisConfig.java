package com.wisrc.sys.webapp.config;

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

@Configuration(value = "retailSystemMybatisConfig")
@MapperScan(basePackages = "com.wisrc.sys.webapp.dao", sqlSessionTemplateRef = "retailSystemSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailSystemDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.sys")
    public DataSource retailSystemDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailSystemSqlSessionFactory")
    public SqlSessionFactory retailSystemDataSourceSqlSessionFactory(@Qualifier("retailSystemDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailSystemTransactionManager")
    public DataSourceTransactionManager retailSystemTransactionManager(@Qualifier("retailSystemDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailSystemSqlSessionTemplate")
    public SqlSessionTemplate retailSystemSqlSessionTemplate(@Qualifier("retailSystemSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
