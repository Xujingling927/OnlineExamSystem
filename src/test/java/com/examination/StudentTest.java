package com.examination;

import com.examination.controller.StudentController;
import com.examination.entity.Student;
import com.examination.serviceImpl.StudentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class StudentTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    protected StudentServiceImpl studentService;
    protected Student student;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentService)).build();
        student = new Student();
        student.setStudentId(123);
        student.setClazz("测试班级");
        student.setEmail("Test@email.com");
        student.setStudentName("TestStudent");
        student.setGrade("2018");
        student.setInstitute("测试机构");
        student.setMajor("测试专业");
        student.setRole("2");
        student.setCardId("213123");
        student.setPwd("123123");
        student.setTel("13588888888");
        student.setSex("男");
    }

    @Test
    void studentAddTest() throws Exception {

//        String requestJson= new Gson().toJson(student);
        mockMvc.perform(MockMvcRequestBuilders.post("/student",student).accept(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @AfterEach
    void tearDown() {
        studentService.deleteById(123);
    }
}
