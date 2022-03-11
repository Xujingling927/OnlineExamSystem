package com.examination.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登陆信息实体类
 */
@ApiModel("登陆信息")
@Data
public class Login {

    @ApiModelProperty(value = "用户账号",name = "userId")
    private Integer userId;

    @ApiModelProperty(value = "密码",name = "password")
    private String password;

    //登录角色 0-管理员 1-教师 2-学生
    @ApiModelProperty(value = "登录角色",name = "role")
    private Integer role;
}
