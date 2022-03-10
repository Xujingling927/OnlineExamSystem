package com.examination.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Teacher {

    @ApiModelProperty(name = "teacherId",value = "教师编号",position = 1)
    private Integer teacherId;

    @ApiModelProperty(name = "teacherNAme",value = "姓名",position = 2)
    private String teacherName;

    @ApiModelProperty(name = "institute",value = "学院",position = 3)
    private String institute;

    @ApiModelProperty(name = "tel",value = "电话号码",position = 4)
    private String tel;

    @ApiModelProperty(name = "email",value = "电子邮箱",position = 5)
    private String email;

    @ApiModelProperty(name = "pwd",value = "密码",position = 6)
    private String pwd;

    @ApiModelProperty(name = "cardId",value = "身份证号",position = 7)
    private String cardId;

    @ApiModelProperty(name = "type",value = "职称",position = 8)
    private String type;

    @ApiModelProperty(name = "sex",value = "性别",position = 9)
    private String sex;

    @ApiModelProperty(name = "role",value = "角色",position = 10)
    private String role;
}