package com.examination.service.question;

import com.examination.entity.MultiQuestion;
import io.swagger.models.auth.In;

import java.util.List;

public interface MultiQuestionService {
    //通过试卷编号查找
    List<MultiQuestion> findByPaperId(Integer paperId);

    List<MultiQuestion> findAll();

    MultiQuestion findByQuestionId(Integer questionId);

    int add(MultiQuestion multiQuestion);

    List<Integer> findBySubject(String subject);
}
