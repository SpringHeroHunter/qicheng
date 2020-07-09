package com.springboot.springboot.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.springboot.springboot.entity.DemoData;

public class DemoReadListener extends AnalysisEventListener<DemoData> {

    // 没读取一次调用invoke方法一次
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("demo"+demoData);
    }

    // 全部读取完毕。会调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
