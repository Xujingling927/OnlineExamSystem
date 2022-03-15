package com.examination.interceptor;

import com.examination.util.AuthToken;
import com.examination.entity.Result;
import com.examination.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    //存放鉴权信息的Header名称，默认是Authorization
    private String httpHeaderName = "Authorization";

    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    //存放登录用户模型Key的Request Key
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {

            String token = request.getParameter(httpHeaderName);
            log.info("Get token from request is {} ", token);
            String userId = "";
            int role = -1;
//            Jedis jedis = new Jedis("192.168.1.106", 6379);
            if (token != null && token.length() != 0) {
                userId = RedisUtil.StringOps.get(token);
                String [] split = userId.split(",");
                role = Integer.parseInt(split[0]);
                if (role == 0){
                    Integer adminId = Integer.parseInt(split[1]);
                }
                if (role == 1){
                    Integer teacherId = Integer.parseInt(split[1]);
                }
                if (role == 2){
                    Integer studentId = Integer.parseInt(split[1]);
                }

                log.info("Get userId from Redis is {} the role is {}",userId,role);
            }
            if (!userId.trim().equals("")) {
                request.setAttribute("role",role);
                request.setAttribute(REQUEST_CURRENT_KEY, userId);
                return true;
            } else {
                Result.fail(unauthorizedErrorMessage,401);
            }

        }

        request.setAttribute(REQUEST_CURRENT_KEY, null);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

