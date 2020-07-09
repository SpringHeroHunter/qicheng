package com.springboot.springboot.service;

import com.springboot.springboot.entity.DemoData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService implements IDemoService {

    @Override
    public void readExcel(List<DemoData> demos) {
        for (DemoData demo : demos) {
            System.out.println("demo"+demo);
        }
    }
}
