package com.examination.service.question;

import com.examination.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {
    List<FillQuestion> findByPaperId(Integer paperId);

    List<FillQuestion> findAll();

    FillQuestion findByQuestionId(Integer questionId);

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject);
}
