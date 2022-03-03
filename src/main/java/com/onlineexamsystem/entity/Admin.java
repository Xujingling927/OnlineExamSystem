package com.onlineexamsystem.entity;

import lombok.Data;

/**
 * 管理员实体类
 */
@Data
public class Admin {

    //管理员编号
    private Integer adminId;

    //管理员名称
    private String adminName;

    //管理员性别
    private String sex;

    //电话
    private String tel;

    //电子邮箱
    private String email;

    //密码
    private String pwd;

    //身份证号
    private String cardId;

    //角色
    private String role;

}
