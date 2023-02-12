package com.pz.mysociety.common.config.dbconfiguration;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "societyEntityManagerFactory", transactionManagerRef = "societyTransactionManager", basePackages = {
        "com.pz.mysociety.repository.societyRepository" })
public class SocietyDatabaseConfig {

    @Primary
    @Bean(name = "societyDataSource")
    @ConfigurationProperties(prefix = "spring.society.datasource")
    public DataSource societyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "societyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean societyEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("societyDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.pz.mysociety.entity.societyEntity").persistenceUnit("Society").build();
    }

    @Primary
    @Bean(name = "societyTransactionManager")
    public PlatformTransactionManager societyTransactionManager(
            @Qualifier("societyEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
