package com.examination.service.impl;

import com.examination.entity.Student;
import com.examination.mapper.StudentMapper;
import com.examination.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> findAll() {
        return studentMapper.findAll();
    }

    @Override
    public Student findById(Integer studentId) {
        return studentMapper.findById(studentId);
    }

    @Override
    public int deleteById(Integer studentId) {
        return studentMapper.deleteById(studentId);
    }

    @Override
    public int update(Student student) {
        return studentMapper.update(student);
    }

    @Override
    public int updatePwd(Integer studentId, String pwd) {
        return studentMapper.updatePwd(studentId,pwd);
    }


    @Override
    public int add(Student student) {
        return studentMapper.add(student);
    }
}
