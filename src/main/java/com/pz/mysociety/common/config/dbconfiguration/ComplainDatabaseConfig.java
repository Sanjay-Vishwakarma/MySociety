package com.pz.mysociety.common.config.dbconfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "complainEntityManagerFactory", transactionManagerRef = "complainTransactionManager", basePackages = {
        "com.pz.mysociety.repository.complainRepository" })
public class ComplainDatabaseConfig {


    @Bean(name = "complainDataSource")
    @ConfigurationProperties(prefix = "spring.complain.datasource")
    public DataSource complainDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "complainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean complainEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                              @Qualifier("complainDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.pz.mysociety.entity.complainEntity").persistenceUnit("Complain").build();
    }

    @Bean(name = "complainTransactionManager")
    public PlatformTransactionManager complainTransactionManager(
            @Qualifier("complainEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}