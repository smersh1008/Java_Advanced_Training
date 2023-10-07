package com.epam.trainning.springbootfirsttask.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
}