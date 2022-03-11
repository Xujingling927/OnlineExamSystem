package com.examination.controller;

import com.examination.entity.*;
import com.examination.service.impl.LoginServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "登陆接口")
@RestController
public class LoginController {

    private final LoginServiceImpl loginService;

    @Autowired
    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }


    @ApiOperation(value = "登陆功能",notes = "login必须选择登陆角色userId可以是studentId，adminId或teacherId")
    @ApiImplicitParam(name = "login",value = "登陆信息")
    @PostMapping("/login")
    public Result login(@RequestBody Login login){
        Integer userId = login.getUserId();
        String password = login.getPassword();
        Integer role = login.getRole();
        switch (role){
            case 0:
                Admin adminRes = loginService.adminLogin(userId,password);
                if (adminRes!=null) return Result.success(adminRes);
                break;
            case 1:
                Teacher teacherRes = loginService.teacherLogin(userId,password);
                if (teacherRes!=null) return  Result.success(teacherRes);
                break;
            case 2:
                Student studentRes = loginService.studentLogin(userId,password);
                if (studentRes!=null) return  Result.success(studentRes);
                break;
        }
        return Result.fail("登陆失败", 1002);
    }



}
