package com.examination.service;

import com.examination.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(Integer studentId);

    int deleteById(Integer studentId);

    int update(Student student);

    int updatePwd(Integer studentId,String pwd);

    int add(Student student);

}
