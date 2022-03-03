package com.onlineexamsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.onlineexamsystem.mapper")
@SpringBootApplication
public class OnlineExamSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineExamSystemApplication.class, args);
    }

}
