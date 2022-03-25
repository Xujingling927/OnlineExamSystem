package com.examination.service.impl;

import com.examination.service.question.QuestionImportService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuestionImportServiceImplTest {

    @Autowired
    QuestionImportService service;

    @Test
    void name() {
        service.add("/Users/xujingling/Developer/OnlineExamSystem/src/main/resources/import/questionImprt.csv");
    }
}