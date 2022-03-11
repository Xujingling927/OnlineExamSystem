package com.examination.entity;


import lombok.Data;

/**
 * 登陆信息实体类
 */
@Data
public class Login {
    private Integer userId;
    private String password;
    //登录角色 0-管理员 1-教师 2-学生
    private Integer role;
}
