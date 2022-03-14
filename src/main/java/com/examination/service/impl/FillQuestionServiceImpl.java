package com.examination.service.impl;

import com.examination.entity.FillQuestion;
import com.examination.service.question.FillQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillQuestionServiceImpl implements FillQuestionService {
    @Override
    public List<FillQuestion> findById(Integer paperId) {
        return null;
    }

    @Override
    public List<FillQuestion> findAll() {
        return null;
    }

    @Override
    public FillQuestion findOnlyQuestionId() {
        return null;
    }

    @Override
    public int add(FillQuestion fillQuestion) {
        return 0;
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return null;
    }
}
