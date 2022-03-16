package com.examination.service.impl;

import com.examination.entity.JudgeQuestion;
import com.examination.mapper.JudgeQuestionMapper;
import com.examination.service.question.JudgeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeQuestionServiceImpl implements JudgeQuestionService {


    private final JudgeQuestionMapper judgeQuestionMapper;
    @Autowired
    public JudgeQuestionServiceImpl(JudgeQuestionMapper judgeQuestionMapper) {
        this.judgeQuestionMapper = judgeQuestionMapper;
    }


    @Override
    public List<JudgeQuestion> findByPaperId(Integer paperId) {
        return judgeQuestionMapper.findByPaperId(paperId);
    }

    @Override
    public List<JudgeQuestion> findAll() {
        return judgeQuestionMapper.findAll();
    }

    @Override
    public JudgeQuestion findByQuestionId(Integer questionId) {
        return judgeQuestionMapper.findByQuestionId(questionId);
    }

    @Override
    public int add(JudgeQuestion judgeQuestion) {
        return judgeQuestionMapper.add(judgeQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject) {
        return judgeQuestionMapper.findBySubject(subject);
    }
}
