package com.examination.mapper;

import com.examination.entity.ExamManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamManageMapper {
    List<ExamManage> findAll();

    ExamManage findByExamCode(Integer examCode);

    int delete(Integer examCode);

    int update(ExamManage exammanage);

    int add(ExamManage exammanage);

    List<ExamManage> findByPaperId(Integer paperId);
}
