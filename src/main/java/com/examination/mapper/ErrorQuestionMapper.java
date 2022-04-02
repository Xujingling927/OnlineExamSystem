package com.examination.mapper;

import com.examination.entity.ErrorQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ErrorQuestionMapper {
    boolean add(Integer studentId, Integer questionId, Integer questionType,String studentAnswer);

    int setStatus(Integer errorId);

    List<ErrorQuestion> findByStudentId(Integer studentId);

    boolean delete(Integer errorId);
}
