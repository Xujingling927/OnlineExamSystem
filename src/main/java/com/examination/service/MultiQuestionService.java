package com.examination.service;

import com.examination.entity.MultiQuestion;

import java.util.List;

public interface MultiQuestionService {
    //通过试卷编号查找
    List<MultiQuestion> findById(Integer paperId);

    List<MultiQuestion> findAll();

    MultiQuestion findOnlyQuestionId();

    int add(MultiQuestion multiQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}
