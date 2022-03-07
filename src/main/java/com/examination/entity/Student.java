package com.examination.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Student{
    @ApiModelProperty(name =  "学生编号")
    private Integer studentId;

    private String studentName;

    private String grade;

    private String major;

    private String clazz;

    private String institute;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String sex;

    private String role;
}