package com.jourgeois.backend.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

public class Config {

//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
//                                                                              @Qualifier ("dataSource") DataSource primaryDataSource,
//                                                                              @Qualifier("jpaProperties") JpaProperties jpaProperties) {
//        return builder
//                .dataSource(primaryDataSource)
//                .properties(jpaProperties.getProperties())
//                .packages("me.jiniworld.demo.models.entities")
//                .persistenceUnit("default")
//                .build();
//    }
}
