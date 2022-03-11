package com.examination.mapper;

import com.examination.entity.JudgeQuestion;

import java.util.List;

public interface JudgeQuestionMapper {
    List<JudgeQuestion> findById(Integer paperId);

    List<JudgeQuestion> findAll();

    JudgeQuestion findOnlyQuestionId();

    int add(JudgeQuestion judgeQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}