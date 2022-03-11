package com.examination.service.impl;

import com.examination.entity.Admin;
import com.examination.entity.Student;
import com.examination.entity.Teacher;
import com.examination.mapper.LoginMapper;
import com.examination.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final LoginMapper loginMapper;

    @Autowired
    public LoginServiceImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public Admin adminLogin(Integer adminId, String password) {
        return loginMapper.adminLogin(adminId,password);
    }

    @Override
    public Student studentLogin(Integer studentId, String password) {
        return loginMapper.studentLogin(studentId,password);
    }

    @Override
    public Teacher teacherLogin(Integer teacherId, String password) {
        return loginMapper.teacherLogin(teacherId,password);
    }
}
