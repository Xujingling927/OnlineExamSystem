package com.examination;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.examination.mapper")
@SpringBootApplication
public class ExaminationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExaminationApplication.class, args);
    }
}
