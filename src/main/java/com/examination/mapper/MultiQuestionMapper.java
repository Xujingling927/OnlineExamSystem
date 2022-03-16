package com.examination.mapper;

import com.examination.entity.MultiQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MultiQuestionMapper {
    List<MultiQuestion> findByPaperId(Integer paperId);

    List<MultiQuestion> findAll();

    MultiQuestion findByQuestionId(Integer questionId);

    int add(MultiQuestion multiQuestion);

    List<Integer> findBySubject(String subject);
}
