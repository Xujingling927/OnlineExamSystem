package com.examination.service.impl;

import com.examination.entity.ErrorQuestion;
import com.examination.mapper.ErrorQuestionMapper;
import com.examination.service.question.ErrorQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorQuestionServiceImpl implements ErrorQuestionService {

    ErrorQuestionMapper mapper;

    @Autowired
    public ErrorQuestionServiceImpl(ErrorQuestionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean add(Integer studentId, Integer questionId, Integer questionType, String studentAnswer) {
        return mapper.add(studentId,questionId,questionType,studentAnswer);
    }

    @Override
    public List<ErrorQuestion> findByStudentId(Integer studentId) {
        return mapper.findByStudentId(studentId);
    }

    @Override
    public int setStatus(Integer id) {
        return mapper.setStatus(id);
    }

    @Override
    public boolean delete(Integer id) {
        return mapper.delete(id);
    }
}
