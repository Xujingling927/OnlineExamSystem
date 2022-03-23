package com.examination.service;

import com.examination.entity.PaperManage;

import java.util.List;

public interface PaperService {

    int add(PaperManage paperManage);

    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    List<Integer> findByQuestionId(Integer questionId,Integer questionType);

}
