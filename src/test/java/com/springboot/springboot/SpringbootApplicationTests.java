package com.springboot.springboot;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.springboot.springboot.controller.DemoDataListener;
import com.springboot.springboot.entity.DemoData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void test01(){
        // 获得一个工作簿对象
        ExcelReaderBuilder readWorkBook = EasyExcel.read("E:\\springboot\\src\\main\\resources\\demo.xlsx", DemoData.class, new DemoDataListener());
        // 获的一个工作表对象
        ExcelReaderSheetBuilder sheet = readWorkBook.sheet();
        // 读取工作表中的内容
        sheet.doRead();
    }


    @Test
    public void test02(){
        // 获得一个工作簿对象
        ExcelWriterBuilder writeWorkBook = EasyExcel.write("E:\\springboot\\src\\main\\resources\\demo.xlsx", DemoData.class);
        // 获的一个工作表对象
        ExcelWriterSheetBuilder sheet = writeWorkBook.sheet();
        // 准备数据
        List<DemoData> lists = data();
        // 写入数据到工作表中
        sheet.doWrite(lists);
    }




    public static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 1000; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.88888888);
            data.setContent("how are you"+i);
            data.setSex("女"+i);
            list.add(data);
        }
        return list;
    }

}
