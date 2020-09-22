package com.wisrc.wms.basic;

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

@Configuration(value = "retailWmsMybatisConfig")
@MapperScan(basePackages = "com.wisrc.wms.webapp.dao", sqlSessionTemplateRef = "retailWmsSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailWmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.wms")
    public DataSource retailSystemDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailWmsSqlSessionFactory")
    public SqlSessionFactory retailWmsDataSourceSqlSessionFactory(@Qualifier("retailWmsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailWmsTransactionManager")
    public DataSourceTransactionManager retailWmsTransactionManager(@Qualifier("retailWmsDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailWmsSqlSessionTemplate")
    public SqlSessionTemplate retailWmsSqlSessionTemplate(@Qualifier("retailWmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
