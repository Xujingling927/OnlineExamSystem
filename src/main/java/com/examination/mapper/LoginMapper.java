package com.examination.mapper;

import com.examination.entity.Admin;
import com.examination.entity.Student;
import com.examination.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface LoginMapper {

    Admin adminLogin(Integer adminId, String password);

    Student studentLogin(@RequestParam("studentId") Integer studentId, @RequestParam("password") String password);

    Teacher teacherLogin(Integer teacherId, String password);
}
