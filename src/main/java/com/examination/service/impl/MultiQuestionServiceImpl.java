package com.examination.service.impl;

import com.examination.entity.MultiQuestion;
import com.examination.mapper.MultiQuestionMapper;
import com.examination.service.question.MultiQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiQuestionServiceImpl implements MultiQuestionService {
    private final MultiQuestionMapper multiQuestionMapper;

    @Autowired
    public MultiQuestionServiceImpl(MultiQuestionMapper multiQuestionMapper) {
        this.multiQuestionMapper = multiQuestionMapper;
    }

    @Override
    public List<MultiQuestion> findByPaperId(Integer paperId) {
        return multiQuestionMapper.findByPaperId(paperId);
    }

    @Override
    public List<MultiQuestion> findAll() {
        return multiQuestionMapper.findAll();
    }

    @Override
    public MultiQuestion findByQuestionId(Integer questionId) {
        return multiQuestionMapper.findByQuestionId(questionId);
    }


    @Override
    public int add(MultiQuestion multiQuestion) {
        return multiQuestionMapper.add(multiQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject) {
        return multiQuestionMapper.findBySubject(subject);
    }
}

