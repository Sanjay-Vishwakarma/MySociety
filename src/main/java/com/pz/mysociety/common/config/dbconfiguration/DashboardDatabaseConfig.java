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
@EnableJpaRepositories(entityManagerFactoryRef = "dashboardEntityManagerFactory", transactionManagerRef = "dashboardTransactionManager", basePackages = {
        "com.pz.mysociety.repository.dashboardRepository" })
public class DashboardDatabaseConfig {

    @Bean(name = "dashboardDataSource")
    @ConfigurationProperties(prefix = "spring.dashboard.datasource")
    public DataSource dashboardDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dashboardEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dashboardEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                                @Qualifier("dashboardDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.pz.mysociety.entity.dashboardEntity").persistenceUnit("Dashboard").build();
    }

    @Bean(name = "dashboardTransactionManager")
    public PlatformTransactionManager dashboardTransactionManager(
            @Qualifier("dashboardEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
