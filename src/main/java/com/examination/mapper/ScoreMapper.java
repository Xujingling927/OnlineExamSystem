package com.examination.mapper;

import com.examination.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    //存入成绩
    int add(Score score);

    //删除成绩
    int delete(Integer scoreId);

    //查找所有成绩
    List<Score> findAll();

    //根据学生编号查成绩
    List<Score> findByStudentId(Integer studentId);

    //根据考试码查成绩
    List<Score> findByExamCode(Integer examCode);
}
