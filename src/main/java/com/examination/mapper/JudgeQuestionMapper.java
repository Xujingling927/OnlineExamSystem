package com.examination.mapper;

import com.examination.entity.JudgeQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JudgeQuestionMapper {
    List<JudgeQuestion> findByPaperId(Integer paperId);

    List<JudgeQuestion> findAll();

    JudgeQuestion findByQuestionId(Integer questionId);

    int add(JudgeQuestion judgeQuestion);

    List<Integer> findBySubject(String subject);
}