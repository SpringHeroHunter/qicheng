package com.springboot.springboot.controller;

import com.springboot.springboot.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties({People.class})
public class HelloController {
    @Autowired
    People people;

    @RequestMapping("/")
    public String index() {
        return people.getName()+" "+people.getUuid()+" "+people.getAge()+" "+people.getNumber() ;
    }
}
