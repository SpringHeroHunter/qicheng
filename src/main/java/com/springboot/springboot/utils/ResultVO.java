package com.springboot.springboot.utils;

import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
