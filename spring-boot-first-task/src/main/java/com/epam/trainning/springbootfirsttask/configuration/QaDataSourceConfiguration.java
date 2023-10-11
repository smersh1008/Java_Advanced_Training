package com.epam.trainning.springbootfirsttask.configuration;

import com.epam.trainning.springbootfirsttask.model.Employee;
import com.epam.trainning.springbootfirsttask.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DBConfiguration.class)
class QaDataSourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(QaDataSourceConfiguration.class);

    private final DBConfiguration dbConfiguration;

    public QaDataSourceConfiguration(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Bean
    @ConditionalOnProperty(
            name = "main.data.source.enabled",
            havingValue = "true")
    @Profile("qa")
    public DataSource qaDataSource() {
        LOGGER.info("Initializing main QA DataSource...");
        return DataSourceBuilder.create()
                .url(dbConfiguration.getUrl())
                .driverClassName(dbConfiguration.getDriverClassName())
                .username(dbConfiguration.getUserName())
                .password(dbConfiguration.getPassword())
                .build();
    }

    @Bean
    @ConditionalOnBean(name = "qaDataSource")
    protected CommandLineRunner initQaDatabase(EmployeeRepository repository) {
        return args -> {
            LOGGER.info("Preloading " + repository.save(new Employee("Main QA burglar")));
            LOGGER.info("Preloading " + repository.save(new Employee("Main QA thief")));
        };
    }
}