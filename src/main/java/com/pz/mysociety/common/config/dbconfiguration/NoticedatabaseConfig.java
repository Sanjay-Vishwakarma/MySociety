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
@EnableJpaRepositories(entityManagerFactoryRef = "noticeEntityManagerFactory", transactionManagerRef = "noticeTransactionManager", basePackages = {
        "com.pz.mysociety.repository.noticeRepository" })
public class NoticedatabaseConfig {


    @Bean(name = "noticeDataSource")
    @ConfigurationProperties(prefix = "spring.notice.datasource")
    public DataSource noticeDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "noticeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean noticeEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                               @Qualifier("noticeDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.pz.mysociety.entity.noticeEntity").persistenceUnit("Notice").build();
    }

    @Bean(name = "noticeTransactionManager")
    public PlatformTransactionManager noticeTransactionManager(
            @Qualifier("noticeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}