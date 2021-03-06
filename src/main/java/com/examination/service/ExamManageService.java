package com.examination.service;

import com.examination.entity.ExamManage;

import java.util.List;

public interface ExamManageService {

    /**
     * 不分页查询所有考试信息
     */
    List<ExamManage> findAll();

    ExamManage findByExamCode(Integer examCode);

    int delete(Integer examCode);

    int update(ExamManage exammanage);

    int add(ExamManage exammanage);

    List<ExamManage> findByPaperId(Integer paperId);

    List<ExamManage> findByStudentId(Integer studentId);
}
