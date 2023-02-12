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
@EnableJpaRepositories(entityManagerFactoryRef = "householdEntityManagerFactory", transactionManagerRef = "householdTransactionManager", basePackages = {
        "com.pz.mysociety.repository.householdRepository" })
public class HouseholdDatabaseConfig {

    @Bean(name = "householdDataSource")
    @ConfigurationProperties(prefix = "spring.household.datasource")
    public DataSource householdDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "householdEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean householdEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("householdDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.pz.mysociety.entity.householdEntity").persistenceUnit("Household").build();
    }

    @Bean(name = "householdTransactionManager")
    public PlatformTransactionManager householdTransactionManager(
            @Qualifier("householdEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
