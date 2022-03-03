package com.onlineexamsystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Api
public class IndexController {

    @GetMapping({"/login","/"})
    public String showLoginPage(){
        return "/login";
    }

    @GetMapping("/index")
    public String showIndexPage(){
        return "/index";
    }


    //TODO 用户登陆校验模块
    @ApiOperation(value = "首页",httpMethod = "POST")
    @PostMapping("/login")
    public String userLogin(String userName,String password) {
        if (userName.equals(password)){
            return "redirect:/index";
        }
        return "/login";
    }
}
