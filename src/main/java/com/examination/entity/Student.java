package com.examination.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Student",description = "学生实体类")
public class Student{
    @ApiModelProperty(name = "studentId",value = "学生编号",position = 1)
    private Integer studentId;

    @ApiModelProperty(name = "studentName",value = "姓名",position = 2)
    private String studentName;

    @ApiModelProperty(name = "grade",value = "年级",position = 3)
    private String grade;

    @ApiModelProperty(name = "major",value = "专业",position = 4)
    private String major;

    @ApiModelProperty(name = "clazz",value = "班级",position = 5)
    private String clazz;

    @ApiModelProperty(name = "institute",value = "学院",position = 6)
    private String institute;

    @ApiModelProperty(name = "tel",value = "电话号码",position = 7)
    private String tel;

    @ApiModelProperty(name = "email",value = "电子邮箱",position = 8)
    private String email;

    @ApiModelProperty(name = "pwd",value = "密码",position = 9)
    @JsonIgnore
    private String pwd;

    @ApiModelProperty(name = "cardId",value = "身份证号",position = 10)
    private String cardId;

    @ApiModelProperty(name = "sex",value = "性别",position = 11)
    private String sex;

    @ApiModelProperty(name = "role",value = "角色",position = 12)
    private String role;
}