package com.springboot.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.springboot.springboot.entity.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {

    /**
     * excel文件下载
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("模板").doWrite(data());
    }

    /**
     * excel文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).sheet().doRead();
        return "success";
    }


    /**
     * 写入excel
     * 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
     * 参数1：读取excel文件路径
     * 参数2：读取sheet第一行，将参数封装到DemoData
     * 参数3：读取每一行都会执行DemoDataListener监听器
     */
    @GetMapping("/write")
    public void write(){
        Long startTs = System.currentTimeMillis();
        String fileName = "C:\\Users\\pc\\Desktop\\demo.xlsx";
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        Long endTs = System.currentTimeMillis();
        System.out.println("总计耗时："+(endTs-startTs)/1000+"秒");
    }


    /**
     * 读取excel
     * 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
     * 参数一：写入excel文件路径
     * 参数二：写入的数据类型是DemoData
     * data()方法是写入的数据，结果是List<DemoData>集合
     */
    @GetMapping("/read")
    public void read(){
        Long startTs = System.currentTimeMillis();
        String fileName = "C:\\Users\\pc\\Desktop\\demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        Long endTs = System.currentTimeMillis();
        System.out.println("总计耗时："+(endTs-startTs)/1000+"秒");
    }

    /**
     * 读取多sheet
     */
    @GetMapping("/readSheet")
    public void readSheet(){
        // 读取全部sheet
        // DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        Long startTs = System.currentTimeMillis();
        String fileName = "C:\\Users\\pc\\Desktop\\demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).doReadAll();
        Long endTs = System.currentTimeMillis();
        System.out.println("总计耗时："+(endTs-startTs)/1000+"秒");
    }









    // excel监听
    public static class DemoDataListener extends AnalysisEventListener<DemoData> {
        List<DemoData> list = new ArrayList<DemoData>();

        // 每一条数据解析都会来调用
        @Override
        public void invoke(DemoData data, AnalysisContext analysisContext) {
            System.out.println("解析到一条数据："+JSON.toJSONString(data));
            list.add(data);
        }

        // 所有数据解析完成了 都会来调用
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                System.out.println(JSON.toJSONString(list));
        }
    }




    public static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 1000; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setContent("how are you"+i);
            data.setSex("女"+i);
            list.add(data);
        }
        return list;
    }
}
