package com.examination.service.impl;

import com.examination.entity.ExamManage;
import com.examination.service.ExamManageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamManageServiceImpl implements ExamManageService {
    @Override
    public List<ExamManage> findAll() {
        return null;
    }

    @Override
    public ExamManage findById(Integer examCode) {
        return null;
    }

    @Override
    public int delete(Integer examCode) {
        return 0;
    }

    @Override
    public int update(ExamManage exammanage) {
        return 0;
    }

    @Override
    public int add(ExamManage exammanage) {
        return 0;
    }

    @Override
    public ExamManage findOnlyPaperId() {
        return null;
    }
}
