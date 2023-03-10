package com.pz.mysociety.common.config.dbconfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "guardEntityManagerFactory", transactionManagerRef = "guardTransactionManager", basePackages = {
        "com.pz.mysociety.repository.guardRepository" })
public class GuardDatabaseConfig {

    @Bean(name = "guardDataSource")
    @ConfigurationProperties(prefix = "spring.guard.datasource")
    public DataSource guardDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "guardEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean guardEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("guardDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.pz.mysociety.entity.guardEntity").persistenceUnit("Guard").build();
    }

    @Bean(name = "guardTransactionManager")
    public PlatformTransactionManager guardTransactionManager(
            @Qualifier("guardEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
