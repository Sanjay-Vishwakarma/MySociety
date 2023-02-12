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
@EnableJpaRepositories(entityManagerFactoryRef = "societyUtilEntityManagerFactory", transactionManagerRef = "societyUtilTransactionManager", basePackages = {
        "com.pz.mysociety.repository.societyUtilRepository" })
public class SocietyUtilDatabaseConfig {

    @Bean(name = "societyUtilDataSource")
    @ConfigurationProperties(prefix = "spring.util.datasource")
    public DataSource societyUtilDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "societyUtilEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean societyUtilEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("societyUtilDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.pz.mysociety.entity.societyUtilEntity").persistenceUnit("Util").build();
    }

    @Bean(name = "societyUtilTransactionManager")
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("societyUtilEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
