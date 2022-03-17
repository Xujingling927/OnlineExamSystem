package com.examination.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.examination.entity.Admin;
import com.examination.entity.Student;
import com.examination.entity.Teacher;

import java.util.Date;

public class TokenGenerator {
    private static final long EXPIRE_TIME = 15 * 60 * 1000;
    private static final String  SECRET = "b29e4761c245a820965fcfaf2df75cffcfe8b64e2a26e52ae48e6aeda2eb7c5d";

    public String getToken(Admin user) {
        return JWT.create()
                .withClaim("userId", user.getAdminId())
                .withClaim("userName", user.getAdminName())
                .withClaim("role", 0)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String getToken(Student user) {
        return JWT.create()
                .withClaim("userId",user.getStudentId())
                .withClaim("userName",user.getStudentName())
                .withClaim("role",2)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String getToken(Teacher user) {
        return JWT.create()
                .withClaim("userId",user.getTeacherId())
                .withClaim("userName",user.getTeacherName())
                .withClaim("role",1)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }


}