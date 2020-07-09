package com.springboot.springboot.service;

import com.springboot.springboot.entity.DemoData;

import java.util.List;

public interface IDemoService {
    public void readExcel(List<DemoData> demoData);
}
