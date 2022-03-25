package com.examination.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.examination.component.LoginAuth;
import com.examination.entity.*;
import com.examination.service.impl.LoginServiceImpl;
import com.examination.util.TokenGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.examination.util.TokenGenerator.SECRET;

@Slf4j
@Api(tags = "登陆接口")
@RestController
public class LoginController {

    private final LoginServiceImpl loginService;

    @Autowired
    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "登陆功能",notes = "login必须选择登陆角色userId可以是studentId，adminId或teacherId")
    @ApiImplicitParam(name = "login",value = "登陆信息",dataType = "Login")
    @PostMapping("/login")
    public Result login(@RequestBody Login login){
        Integer userId = login.getUserId();
        String password = login.getPassword();
        Integer role = login.getRole();
        TokenGenerator tokenGenerator = new TokenGenerator();
        String token;
        switch (role){
            //TODO 返回值不要pwd 加密
            case 0:
                Admin adminRes = loginService.adminLogin(userId,password);
                if (adminRes!=null){
                    token = tokenGenerator.getToken(adminRes);
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
                    token = tokenGenerator.getToken(teacherRes);
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
                    token = tokenGenerator.getToken(studentRes);
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

    @LoginAuth
    @ApiImplicitParam(name = "login",value = "验证信息",dataType = "Login")
    @PostMapping("/login-check")
    public Result loginCheck(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody Login login) throws IOException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        Integer userId = login.getUserId();
        Integer role = login.getRole();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        boolean status = false;
        try {
            log.info("userId = {},role = {}",userId,role);
            Integer role_auth = jwtVerifier.verify(token).getClaim("role").asInt();
            Integer userId_auth = jwtVerifier.verify(token).getClaim("userId").asInt();
            log.info("userId_auth = {},role_auth = {}",userId_auth,role_auth);
            if (Objects.equals(role_auth, role) && Objects.equals(userId_auth, userId)){
                log.info("校验成功");
                status = true;
            }
        } catch (JWTVerificationException e) {
            log.info("Verify Token: invalid token");
        }
        if (status) return Result.successMs("");
        else httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }


}
