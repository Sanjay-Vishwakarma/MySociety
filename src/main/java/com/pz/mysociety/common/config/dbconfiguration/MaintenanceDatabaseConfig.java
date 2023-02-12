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
@EnableJpaRepositories(entityManagerFactoryRef = "maintenanceEntityManagerFactory", transactionManagerRef = "maintenanceTransactionManager", basePackages = {
        "com.pz.mysociety.repository.maintenanceRepository" })
public class MaintenanceDatabaseConfig {

    @Bean(name = "maintenanceDataSource")
    @ConfigurationProperties(prefix = "spring.maintenance.datasource")
    public DataSource maintenanceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "maintenanceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean maintenanceEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("maintenanceDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.pz.mysociety.entity.maintenanceEntity").persistenceUnit("Maintenance").build();
    }

    @Bean(name = "maintenanceTransactionManager")
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("maintenanceEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
