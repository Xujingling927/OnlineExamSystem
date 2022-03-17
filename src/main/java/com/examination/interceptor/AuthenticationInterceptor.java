package com.examination.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.examination.component.AdminAuth;
import com.examination.component.PassToken;
import com.examination.component.StudentAuth;
import com.examination.component.TeacherAuth;
import com.examination.entity.Admin;
import com.examination.entity.Student;
import com.examination.entity.Teacher;
import com.examination.service.AdminService;
import com.examination.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String  SECRET = "b29e4761c245a820965fcfaf2df75cffcfe8b64e2a26e52ae48e6aeda2eb7c5d";

    @Autowired
    AdminService adminService;
    StudentService studentService;

    //    TeacherService teacherService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                log.info(".........不需要验证token.......");
                return true;
            }
        }

        //检查有没有需要管理员权限的注解
        if (method.isAnnotationPresent(AdminAuth.class)) {
            log.info("验证管理员权限");
            AdminAuth userLoginToken = method.getAnnotation(AdminAuth.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    log.info("no token");
                    httpServletResponse.sendError(401,"无token，请重新登录");
                    return false;
                }
                // 获取 token 中的 user id
//                String userId;
//                try {
//                    userId = JWT.decode(token).getAudience().get(0);
//                    log.info("The token is {},get userId = {}",token,userId);
//                } catch (JWTDecodeException j) {
//                    httpServletResponse.sendError(401,"解析token失败");
//                    return false;
//                }
//                Admin admin = adminService.findById(Integer.parseInt(userId));
//                if (admin == null) {
//                    log.info("admin not found");
//                    httpServletResponse.sendError(404,"用户不存在，请重新登录");
//                    return false;
//                }
                // 验证 token
                   JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
                try {
                    if (jwtVerifier.verify(token).getClaim("role").asInt()==0){
                        log.info("校验成功");
                        return true;
                    }
                    else{
                        httpServletResponse.sendError(401);
                        return false;
                    }
                } catch (JWTVerificationException e) {
                    log.info("Verify Token: invalid token");
                    httpServletResponse.sendError(401,"token无效");
                    return false;
                }

            }
        }
        //检查有没有需要学生权限的注解
        if (method.isAnnotationPresent(StudentAuth.class)) {
            log.info("验证学生权限");
            StudentAuth userLoginToken = method.getAnnotation(StudentAuth.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    log.info("no token");
                    httpServletResponse.sendError(404,"无token，请重新登录");
                    return false;
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                    log.info("The token is {},userId = {}",token,userId);
                } catch (JWTDecodeException j) {
                    httpServletResponse.sendError(401,"解析token失败");
                    return false;
                }
                Student student = studentService.findById(Integer.parseInt(userId));
                if (student == null) {
                    httpServletResponse.sendError(404,"用户不存在，请重新登录");
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(student.getPwd())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    httpServletResponse.sendError(401,"解析token失败");
                    return false;
                }
                return true;
            }
        }
        //TODO 教师预留
        //检查有没有需要教师权限的注解
//        if (method.isAnnotationPresent(TeacherAuth.class)) {
//            TeacherAuth userLoginToken = method.getAnnotation(TeacherAuth.class);
//            if (userLoginToken.required()) {
//                // 执行认证
//                if (token == null) {
//                    throw new RuntimeException("无token，请重新登录");
//                }
//                // 获取 token 中的 user id
//                String userId;
//                try {
//                    userId = JWT.decode(token).getAudience().get(0);
//                } catch (JWTDecodeException j) {
//                    throw new RuntimeException("401");
//                }
////                Teacher teacher = teacherService.findById(Integer.parseInt(userId));
//                if (teacher == null) {
//                    throw new RuntimeException("用户不存在，请重新登录");
//                }
//                // 验证 token
//                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(teacher.getPwd())).build();
//                try {
//                    jwtVerifier.verify(token);
//                } catch (JWTVerificationException e) {
//                    throw new RuntimeException("401");
//                }
//                return true;
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}