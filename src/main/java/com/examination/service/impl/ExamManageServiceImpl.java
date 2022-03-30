package com.examination.service.impl;

import com.examination.entity.ExamManage;
import com.examination.mapper.ExamManageMapper;
import com.examination.service.ExamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamManageServiceImpl implements ExamManageService {
    ExamManageMapper examManageMapper;

    @Autowired
    public ExamManageServiceImpl(ExamManageMapper examManageMapper) {
        this.examManageMapper = examManageMapper;
    }

    @Override
    public List<ExamManage> findAll() {
        return examManageMapper.findAll();
    }

    @Override
    public ExamManage findByExamCode(Integer examCode) {
        return examManageMapper.findByExamCode(examCode);
    }

    @Override
    public int delete(Integer examCode) {
        return examManageMapper.delete(examCode);
    }

    @Override
    public int update(ExamManage exammanage) {
        return examManageMapper.update(exammanage);
    }

    @Override
    public int add(ExamManage exammanage) {
        return examManageMapper.add(exammanage);
    }

    @Override
    public List<ExamManage> findByPaperId(Integer paperId) {
        return examManageMapper.findByPaperId(paperId);
    }

    @Override
    public List<ExamManage> findByStudentId(Integer studentId) {
        return examManageMapper.findByStudentId(studentId);
    }
}
