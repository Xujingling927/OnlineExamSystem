package com.examination.controller.common;

public enum ResultEnum {

    OK("成功",200);


    private final String msg;
    private final Integer code;

    ResultEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
