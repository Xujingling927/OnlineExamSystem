package com.examination.service;

import com.examination.entity.Admin;
import com.examination.entity.Student;
import com.examination.entity.Teacher;

public interface LoginService {

    public Admin adminLogin(Integer adminId,String password);

    public Student studentLogin(Integer studentId,String password);

    public Teacher teacherLogin(Integer teacherId,String password);
}
