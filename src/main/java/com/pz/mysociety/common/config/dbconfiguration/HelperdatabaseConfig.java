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
@EnableJpaRepositories(entityManagerFactoryRef = "helperEntityManagerFactory", transactionManagerRef = "helperTransactionManager", basePackages = {
        "com.pz.mysociety.repository.helperRepository" })
public class HelperdatabaseConfig {

    @Bean(name = "helperDataSource")
    @ConfigurationProperties(prefix = "spring.helper.datasource")
    public DataSource helperDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "helperEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean helperEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("helperDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.pz.mysociety.entity.helperEntity").persistenceUnit("Helper").build();
    }

    @Bean(name = "helperTransactionManager")
    public PlatformTransactionManager helperTransactionManager(
            @Qualifier("helperEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
