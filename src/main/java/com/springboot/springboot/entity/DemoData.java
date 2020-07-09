package com.springboot.springboot.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;
@Data
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    // 时间格式换
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    @ExcelProperty("内容")
    private String content;
    @ExcelProperty("性别")
    private String sex;
}
