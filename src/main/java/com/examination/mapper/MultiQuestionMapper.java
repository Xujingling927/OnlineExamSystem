package com.examination.mapper;

import com.examination.entity.MultiQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MultiQuestionMapper {
    List<MultiQuestion> findById(Integer paperId);

    List<MultiQuestion> findAll();

    MultiQuestion findOnlyQuestionId();

    int add(MultiQuestion multiQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}
