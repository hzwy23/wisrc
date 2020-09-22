package com.wisrc.zuul.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.wisrc.zuul.dao"}, sqlSessionTemplateRef = "retailGatewaySqlSessionTemplate")
public class MybatisConfig {


    @Value("${pagehelper.helper-dialect}")
    private String HELPERDIALECT;

    @Value("${pagehelper.reasonable}")
    private String REASONABLE;

    @Value("${pagehelper.params}")
    private String PARAMS;

    @Value("${pagehelper.support-methods-arguments}")
    private String SUPPORT_METHODS_ARGUMENTS;

    @Value("${pagehelper.auto-runtime-dialect}")
    private String AUTO_RUNTIME_DIALECT;

    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", HELPERDIALECT);
        properties.setProperty("reasonable", REASONABLE);
        properties.setProperty("supportMethodsArguments", SUPPORT_METHODS_ARGUMENTS);
        properties.setProperty("params", PARAMS);
        properties.setProperty("autoRuntimeDialect", AUTO_RUNTIME_DIALECT);
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean(name = "retailGatewayDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.gateway")
    public DataSource retailGatewayDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "retailGatewaySqlSessionFactory")
    public SqlSessionFactory retailGatewayDataSourceSqlSessionFactory(@Qualifier("retailGatewayDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "retailGatewayTransactionManager")
    public DataSourceTransactionManager retailGatewayTransactionManager(@Qualifier("retailGatewayDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "retailGatewaySqlSessionTemplate")
    public SqlSessionTemplate retailGatewaySqlSessionTemplate(@Qualifier("retailGatewaySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
