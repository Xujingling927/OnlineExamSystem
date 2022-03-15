package com.examination.controller;

import com.examination.entity.*;
import com.examination.service.impl.LoginServiceImpl;
import com.examination.util.Md5TokenGenerator;
import com.examination.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@Api(tags = "登陆接口")
@RestController
public class LoginController {

    private final LoginServiceImpl loginService;

    Md5TokenGenerator md5TokenGenerator;

    @Autowired
    public LoginController(LoginServiceImpl loginService,Md5TokenGenerator md5TokenGenerator) {
        this.loginService = loginService;
        this.md5TokenGenerator = md5TokenGenerator;
    }


    @ApiOperation(value = "登陆功能",notes = "login必须选择登陆角色userId可以是studentId，adminId或teacherId")
    @ApiImplicitParam(name = "login",value = "登陆信息",dataTypeClass = Login.class)
    @PostMapping("/login")
    public Result login(@RequestBody Login login){
        Integer userId = login.getUserId();
        String password = login.getPassword();
        Integer role = login.getRole();
        String token = md5TokenGenerator.generate(userId.toString(),password);
        switch (role){
            //TODO 返回值不要pwd 加密
            case 0:
                Admin adminRes = loginService.adminLogin(userId,password);
                if (adminRes!=null){
                    RedisUtil.StringOps.set(token,"0,"+userId.toString());
                    RedisUtil.StringOps.setEx(token,userId.toString(),1, TimeUnit.HOURS);
                    Result result = new  Result();
                    result.setStatus(true);
                    result.setMessage("登陆成功");
                    result.addAttribute("token",token);
                    result.addAttribute("res",adminRes);
                    return result;
                }
                break;
            case 1:
                Teacher teacherRes = loginService.teacherLogin(userId,password);
                if (teacherRes!=null){
                    RedisUtil.StringOps.set(token,"1,"+userId.toString());
                    RedisUtil.StringOps.setEx(token,userId.toString(),1, TimeUnit.HOURS);
                    Result result = new  Result();
                    result.setStatus(true);
                    result.setMessage("登陆成功");
                    result.addAttribute("token",token);
                    result.addAttribute("res",teacherRes);
                    return  result;
                }
                break;
            case 2:
                Student studentRes = loginService.studentLogin(userId,password);
                if (studentRes!=null){
                    RedisUtil.StringOps.set(token,userId.toString());
                    RedisUtil.StringOps.setEx(token,"2,"+userId.toString(),1, TimeUnit.HOURS);
                    Result result = new  Result();
                    result.setStatus(true);
                    result.setMessage("登陆成功");
                    result.addAttribute("token",token);
                    result.addAttribute("res",studentRes);
                    return result;
                }
                break;
        }
        return Result.fail("登陆失败", 1002);
    }




}
