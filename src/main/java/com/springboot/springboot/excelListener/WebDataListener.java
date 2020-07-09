package com.springboot.springboot.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.springboot.springboot.entity.DemoData;
import com.springboot.springboot.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Scope("prototype")
public class WebDataListener extends AnalysisEventListener<DemoData> {

    @Autowired
    DemoService demoDataService;

    ArrayList<DemoData> demoDatas = new ArrayList<DemoData>();

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        demoDatas.add(demoData);
        if (demoDatas.size() % 10 == 0) {
            demoDataService.readExcel(demoDatas);
            demoDatas.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
