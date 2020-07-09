package com.springboot.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.springboot.springboot.entity.DemoData;
import com.springboot.springboot.excelListener.DemoReadListener;
import com.springboot.springboot.excelListener.WebDataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {

    @Autowired
    WebDataListener webDataListener;

    /**
     * 读取excel
     * @return
     */
    @PostMapping("read")
    public String read(){
        ExcelReaderBuilder read = EasyExcel.read("C:\\Users\\pc\\Desktop\\demo.xlsx", DemoData.class, new DemoReadListener());
        read.sheet().doRead();
        return "success";
    }

    /**
     * 写入excel
     * @return
     */
    @PostMapping("write")
    public String write(){
        ExcelWriterBuilder write = EasyExcel.write("C:\\Users\\pc\\Desktop\\demo.xlsx", DemoData.class);
        write.sheet().doWrite(data());
        return "success";
    }


    /**
     * excel 上传
     * @param file
     * @return
     */
    @PostMapping("readExcel")
    @ResponseBody
    public String readExcel(MultipartFile file){
        try {
            // 工作簿
            ExcelReaderBuilder readWorkBook = EasyExcel.read(file.getInputStream(), DemoData.class, webDataListener);
            // 工作表
            readWorkBook.sheet().doRead();
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }


    /**
     * excel模板下载
     * @param response
     * @throws IOException
     */
    @GetMapping("download")
    @ResponseBody
    public void excelDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");
        // 获得一个工作簿对象
        ExcelWriterBuilder writeWorkBook = EasyExcel.write(response.getOutputStream(), DemoData.class);
        // 获的一个工作表对象
        ExcelWriterSheetBuilder sheet = writeWorkBook.sheet("模板");
        // 写入数据到工作表中
        sheet.doWrite(data());
    }
















    public static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 1000; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setContent("how are you"+i);
            data.setSex("男"+i);
            list.add(data);
        }
        return list;
    }

}
