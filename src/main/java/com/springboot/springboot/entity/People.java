package com.springboot.springboot.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.qicheng")
@PropertySource(value = "classpath:my.properties")
public class People {
    private String name;
    private int age;
    private int number;
    private String uuid;
}
