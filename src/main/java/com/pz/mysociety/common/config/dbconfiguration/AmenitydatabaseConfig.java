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
@EnableJpaRepositories(entityManagerFactoryRef = "amenityEntityManagerFactory", transactionManagerRef = "amenityTransactionManager", basePackages = {
        "com.pz.mysociety.repository.amenityRepository" })
public class AmenitydatabaseConfig {


    @Bean(name = "amenityDataSource")
    @ConfigurationProperties(prefix = "spring.amenity.datasource")
    public DataSource amenityDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "amenityEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean amenityEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                               @Qualifier("amenityDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.pz.mysociety.entity.amenityEntity").persistenceUnit("Amenity").build();
    }

    @Bean(name = "amenityTransactionManager")
    public PlatformTransactionManager amenityTransactionManager(
            @Qualifier("amenityEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
