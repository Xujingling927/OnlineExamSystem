package com.examination.service.impl;

import com.examination.entity.Score;
import com.examination.mapper.ScoreMapper;
import com.examination.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    ScoreMapper scoreMapper;

    @Autowired
    public ScoreServiceImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    @Override
    public int add(Score score) {
        return scoreMapper.add(score);
    }

    @Override
    public int delete(Integer scoreId) {
        return scoreMapper.delete(scoreId);
    }

    @Override
    public List<Score> findAll() {
        return scoreMapper.findAll();
    }

    @Override
    public List<Score> findByStudentId(Integer studentId) {
        return scoreMapper.findByStudentId(studentId);
    }

    @Override
    public List<Score> findByExamCode(Integer examCode) {
        return scoreMapper.findByExamCode(examCode);
    }
}
