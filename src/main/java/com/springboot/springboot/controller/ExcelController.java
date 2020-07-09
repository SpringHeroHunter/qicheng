package com.springboot.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.springboot.springboot.entity.DemoData;
import com.springboot.springboot.entity.FileData;
import com.springboot.springboot.excelListener.DemoReadListener;
import com.springboot.springboot.excelListener.WebDataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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


    /**
     * excel模板填充
     */
    @PostMapping("template")
    public void template(){
        // 加载模板
        InputStream templateFile = this.getClass().getClassLoader().getResourceAsStream("templates/fill_data_template1.xlsx");
        // 写入文件
        String targetFileName = "单组数据填充.xlsx";
        // 准备对象数据填充 对象
//        FileData data = new FileData();
//        data.setName("mazhao");
//        data.setAge(18);

        // 生成工作簿对象
        ExcelWriterBuilder workBookWriter= EasyExcel.write(targetFileName).withTemplate(templateFile);
        // 获取工作表并填充 map
        HashMap<String, String> mapFileData = new HashMap<>();
        mapFileData.put("name", "mazhao");
        mapFileData.put("age", "18");
        // 获取第一个工作表填充并自动关闭流
        workBookWriter.sheet().doFill(mapFileData);
    }


    /**
     * excel多组数据模板填充
     */
    @PostMapping("numtemplate")
    public void numtemplate(){
        // 加载模板
        InputStream templateFile = this.getClass().getClassLoader().getResourceAsStream("templates/fill_data_template1.xlsx");
        // 写入文件
        String targetFileName = "多组数据填充.xlsx";
        // 生成工作簿对象
        ExcelWriterBuilder workBookWriter = EasyExcel.write(targetFileName).withTemplate(templateFile);
        // 获取第一个工作表填充并自动关闭流
        workBookWriter.sheet().doFill(TemplateData());
    }




    /**
     * 多组数据填充准备
     * @return
     */
    public static List<FileData> TemplateData() {
        ArrayList<FileData> fillDatas = new ArrayList<FileData>();
        for (int i = 0; i < 10; i++) {
            FileData fillData = new FileData();
            fillData.setName("杭州黑马0" + i);
            fillData.setAge(10 + i);
            fillDatas.add(fillData);
        }
        return fillDatas;
    }

    /**
     * 读取数据准备
     * @return
     */
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
