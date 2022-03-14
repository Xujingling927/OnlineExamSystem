package com.examination.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员实体类
 */
@Data
@ApiModel(value = "Admin",description = "管理员实体类")
public class Admin {

    //管理员编号
    @ApiModelProperty(name = "adminId",value = "管理员编号",position = 1)
    private Integer adminId;

    //管理员名称
    @ApiModelProperty(name = "adminName",value = "管理员名称",position = 2)
    private String adminName;

    //管理员性别
    @ApiModelProperty(name = "sex",value = "管理员性别",position = 3)
    private String sex;

    //电话
    @ApiModelProperty(name = "tel",value = "电话",position = 4)
    private String tel;

    //电子邮箱
    @ApiModelProperty(name = "email",value = "电子邮箱",position = 5)
    private String email;

    //密码
    @ApiModelProperty(name = "pwd",value = "密码",position = 6)
    @JsonIgnore
    private String pwd;

    //身份证号
    @ApiModelProperty(name = "cardId",value = "身份证号",position = 7)
    private String cardId;

    //角色
    @ApiModelProperty(name = "role",value = "角色",position = 8)
    private String role;

}
