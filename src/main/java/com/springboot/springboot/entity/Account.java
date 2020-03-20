package com.springboot.springboot.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity // 表明是一个映射实体类
public class Account {
    @Id // 表明id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // 自动生成主键
    private int id;
    private String name;
    private double money;
}
