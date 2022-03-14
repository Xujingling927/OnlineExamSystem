package com.examination.mapper;

import com.examination.entity.FillQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FillQuestionMapper {
    List<FillQuestion> findById(Integer paperId);

    List<FillQuestion> findAll();

    FillQuestion findOnlyQuestionId();

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}
