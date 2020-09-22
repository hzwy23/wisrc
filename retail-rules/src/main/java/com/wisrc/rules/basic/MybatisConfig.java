package com.wisrc.rules.basic;

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

@Configuration(value = "retailRulesMybatisConfig")
@MapperScan(basePackages = "com.wisrc.rules.webapp.dao", sqlSessionTemplateRef = "retailRulesSqlSessionTemplate")
public class MybatisConfig {


    @Bean(name = "retailRulesDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.rules")
    public DataSource retailRulesDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailRulesSqlSessionFactory")
    public SqlSessionFactory retailRulesDataSourceSqlSessionFactory(@Qualifier("retailRulesDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailRulesTransactionManager")
    public DataSourceTransactionManager retailRulesTransactionManager(@Qualifier("retailRulesDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailRulesSqlSessionTemplate")
    public SqlSessionTemplate retailRulesSqlSessionTemplate(@Qualifier("retailRulesSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
