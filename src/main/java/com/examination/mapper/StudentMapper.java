package com.examination.mapper;

import com.examination.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> findAll();

    Student findById(Integer studentId);

    int deleteById(Integer studentId);

    int update(Student student);

    int updatePwd(@Param("studentId") Integer studentId,@Param("pwd") String pwd);

    int add(Student student);

}
