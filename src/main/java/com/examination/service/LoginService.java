package com.examination.service;

import com.examination.entity.Admin;
import com.examination.entity.Student;
import com.examination.entity.Teacher;

public interface LoginService {

    Admin adminLogin(Integer adminId, String password);

    Student studentLogin(Integer studentId, String password);

    Teacher teacherLogin(Integer teacherId, String password);
}
