package com.examination.controller;

import com.examination.entity.*;
import com.examination.serviceImpl.LoginServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "登陆接口")
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginServiceImpl loginService;

    @Autowired
    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public Result login(@RequestBody Login login){
        Integer userId = login.getUserId();
        String password = login.getPassword();
        Integer role = login.getRole();
        switch (role){
            case 0:
                Admin adminRes = loginService.adminLogin(login.getUserId(),login.getPassword());
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
