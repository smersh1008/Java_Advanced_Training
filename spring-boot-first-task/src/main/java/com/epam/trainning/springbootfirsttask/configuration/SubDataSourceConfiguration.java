package com.epam.trainning.springbootfirsttask.configuration;

import com.epam.trainning.springbootfirsttask.model.Employee;
import com.epam.trainning.springbootfirsttask.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DBConfiguration.class)
class SubDataSourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubDataSourceConfiguration.class);

    private final DBConfiguration dbConfiguration;

    public SubDataSourceConfiguration(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource subDataSource() {
        LOGGER.info("Main DataSource is blocked, initializing second one...");
        return DataSourceBuilder.create()
                .url(dbConfiguration.getUrl())
                .driverClassName(dbConfiguration.getDriverClassName())
                .username(dbConfiguration.getUserName())
                .password(dbConfiguration.getPassword())
                .build();
    }

    @Bean
    @ConditionalOnBean(name = "subDataSource")
    CommandLineRunner initSubDatabase(EmployeeRepository repository) {
        return args -> {
            LOGGER.info("Preloading " + repository.save(new Employee("Sub burglar")));
            LOGGER.info("Preloading " + repository.save(new Employee("Sub thief")));
        };
    }
}