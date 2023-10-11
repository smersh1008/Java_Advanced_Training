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
class DevDataSourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevDataSourceConfiguration.class);

    private final DBConfiguration dbConfiguration;

    public DevDataSourceConfiguration(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Bean
    @ConditionalOnProperty(
            name = "main.data.source.enabled",
            havingValue = "true")
    @Profile("dev")
    public DataSource devDataSource() {
        LOGGER.info("Initializing main DEV DataSource...");
        return DataSourceBuilder.create()
                .url(dbConfiguration.getUrl())
                .driverClassName(dbConfiguration.getDriverClassName())
                .username(dbConfiguration.getUserName())
                .password(dbConfiguration.getPassword())
                .build();
    }

    @Bean
    @ConditionalOnBean(name = "devDataSource")
    protected CommandLineRunner initDevDatabase(EmployeeRepository repository) {
        return args -> {
            LOGGER.info("Preloading " + repository.save(new Employee("Main DEV burglar")));
            LOGGER.info("Preloading " + repository.save(new Employee("Main DEV thief")));
        };
    }
}