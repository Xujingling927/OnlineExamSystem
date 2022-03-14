package com.examination.service.question;

import com.examination.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {
    List<FillQuestion> findById(Integer paperId);

    List<FillQuestion> findAll();

    FillQuestion findOnlyQuestionId();

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}
