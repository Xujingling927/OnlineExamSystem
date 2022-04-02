package com.examination.service.question;

import com.examination.entity.ErrorQuestion;

import java.util.List;

public interface ErrorQuestionService {
    /** 添加错题
     * @param studentId 学生编号
     * @param questionId 题目编号
     * @param questionType 题目类型
     * @return 添加结果
     */
    boolean add(Integer studentId, Integer questionId, Integer questionType,String studentAnswer);

    List<ErrorQuestion> findByStudentId(Integer studentId);

    int setStatus(Integer errorId);

    boolean delete(Integer id);
}
