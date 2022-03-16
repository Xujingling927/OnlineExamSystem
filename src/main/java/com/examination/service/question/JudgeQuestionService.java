package com.examination.service.question;

import com.examination.entity.JudgeQuestion;

import java.util.List;

public interface JudgeQuestionService {
    List<JudgeQuestion> findByPaperId(Integer paperId);

    List<JudgeQuestion> findAll();

    JudgeQuestion findByQuestionId(Integer questionId);

    int add(JudgeQuestion judgeQuestion);

    List<Integer> findBySubject(String subject);
}
