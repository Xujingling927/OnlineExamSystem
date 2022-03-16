package com.examination.service.impl;

import com.examination.entity.FillQuestion;
import com.examination.mapper.FillQuestionMapper;
import com.examination.service.question.FillQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillQuestionServiceImpl implements FillQuestionService {
    private final FillQuestionMapper fillQuestionMapper;

    @Autowired
    public FillQuestionServiceImpl(FillQuestionMapper fillQuestionMapper) {
        this.fillQuestionMapper = fillQuestionMapper;
    }

    @Override
    public List<FillQuestion> findByPaperId(Integer paperId) {
        return fillQuestionMapper.findByPaperId(paperId);
    }

    @Override
    public List<FillQuestion> findAll() {
        return fillQuestionMapper.findAll();
    }

    @Override
    public FillQuestion findByQuestionId(Integer questionId) {
        return fillQuestionMapper.findByQuestionId(questionId);
    }


    @Override
    public int add(FillQuestion fillQuestion) {
        return fillQuestionMapper.add(fillQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject) {
        return fillQuestionMapper.findBySubject(subject);
    }
}
