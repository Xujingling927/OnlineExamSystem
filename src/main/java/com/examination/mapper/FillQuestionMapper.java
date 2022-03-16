package com.examination.mapper;

import com.examination.entity.FillQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FillQuestionMapper {
    List<FillQuestion> findByPaperId(Integer paperId);

    List<FillQuestion> findAll();

    FillQuestion findByQuestionId(Integer questionId);

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject);
}
