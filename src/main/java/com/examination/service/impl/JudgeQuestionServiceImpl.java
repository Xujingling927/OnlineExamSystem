package com.examination.service.impl;

import com.examination.entity.JudgeQuestion;
import com.examination.mapper.JudgeQuestionMapper;
import com.examination.service.question.JudgeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeQuestionServiceImpl implements JudgeQuestionService {


    @Override
    public List<JudgeQuestion> findById(Integer paperId) {
        return null;
    }

    @Override
    public List<JudgeQuestion> findAll() {
        return null;
    }

    @Override
    public JudgeQuestion findOnlyQuestionId() {
        return null;
    }

    @Override
    public int add(JudgeQuestion judgeQuestion) {
        return 0;
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return null;
    }
}
