package com.jobs.im.file.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.jobs.im.file.common.Cst;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @program: im
 * @ClassName: DataSourceConfig
 * @description:
 * @author: Author
 * @create: 2024-02-22 17:04
 * @Version 1.0
 **/
@Configuration
@MapperScan(basePackages = Cst.MYBATIS_MAPPER_SCAN_BASE_PACKAGES, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
    @Value("${db.url}")
    private String jdbcUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver-class-name}")
    private String driverClassName;

    @Bean(name = {"dataSource"})
    public HikariDataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new ImMetaObjectHandler();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        // 数据源
        sessionFactory.setDataSource(dataSource);
        // 数据库字段自动填充
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(metaObjectHandler());
        sessionFactory.setGlobalConfig(globalConfig);
        // MybatisPlus分页插件
        MybatisConfiguration configuration = new MybatisConfiguration();
        PaginationInterceptor interceptor = new PaginationInterceptor();
        interceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        configuration.addInterceptor(interceptor);
        sessionFactory.setConfiguration(configuration);
        // Mybatis的Mapper文件位置
        sessionFactory
            .setMapperLocations(new PathMatchingResourcePatternResolver().getResources(Cst.MYBATIS_MAPPER_LOCATIONS));
        // Mybatis的模型别名
        sessionFactory.setTypeAliasesPackage(com.jobs.im.core.common.Cst.MYBATIS_MAPPER_YPE_ALIASES);
        return sessionFactory.getObject();
    }
}
