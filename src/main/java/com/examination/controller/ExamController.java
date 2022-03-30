package com.examination.controller;

import com.examination.component.LoginAuth;
import com.examination.entity.*;
import com.examination.service.ExamManageService;
import com.examination.service.ScoreService;
import com.examination.service.question.FillQuestionService;
import com.examination.service.question.JudgeQuestionService;
import com.examination.service.question.MultiQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "提交答题卡")
public class ExamController {

    MultiQuestionService multiQuestionService;
    JudgeQuestionService judgeQuestionService;
    FillQuestionService fillQuestionService;
    ExamManageService examManageService;
    ScoreService scoreService;

    @Autowired
    public ExamController(
            MultiQuestionService multiQuestionService,
            JudgeQuestionService judgeQuestionService,
            FillQuestionService fillQuestionService,
            ExamManageService examManageService,
            ScoreService scoreService) {
        this.multiQuestionService = multiQuestionService;
        this.judgeQuestionService = judgeQuestionService;
        this.fillQuestionService = fillQuestionService;
        this.examManageService = examManageService;
        this.scoreService = scoreService;
    }



    @LoginAuth
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId",value = "学生编号"),
            @ApiImplicitParam(name = "examCode",value = "考试码"),
            @ApiImplicitParam(name = "answers",value = "答卷实体类",dataType = "Answer",allowMultiple = true)
    })
    @ApiOperation(value = "学生提交试卷")
    @PostMapping("/exam/{examCode}/student/{studentId}")
    public Result judge(@PathVariable("studentId") Integer studentId,
                        @PathVariable("examCode") Integer examCode,
                        @RequestBody List<Answer> answers){
        int scoreSum;//成绩总分
        int multiScore = 0;//选择题得分
        int fillScore = 0;//填空题得分
        int judgeScore = 0;//判断题得分
        // 1-选择 2-填空 3-判断
        for (Answer answer : answers) {
            switch (answer.getQuestionType()) {
                case 1:
                    multiScore += multiQuestionJudgement(answer.getQuestionId(), answer.getStudentAnswer());
                    break;
                case 2:
                    fillScore += fillQuestionJudgement(answer.getQuestionId(), answer.getStudentAnswer());
                    break;
                case 3:
                    judgeScore += judgeQuestionJudgement(answer.getQuestionId(), answer.getStudentAnswer());
                    break;
            }
        }
        scoreSum = multiScore + fillScore + judgeScore;
        Score score = new Score();
        score.setScore(scoreSum);
        score.setStudentId(studentId);
        score.setExamCode(examCode);
        score.setAnswerDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        score.setSubject(examManageService.findByExamCode(examCode).getMajor());
        int res = scoreService.add(score);
        if (res != 0) return new Result(true,score);
        return Result.fail("提交失败",400);
    }

    /**
     * 选择题得分统计
     * @param questionId 题目编号
     * @param answer 回答
     * @return 得分
     */
    private int multiQuestionJudgement(Integer questionId, String answer){
        MultiQuestion origin =  multiQuestionService.findByQuestionId(questionId);
        if (origin ==null) return 0;
        if (origin.getRightAnswer().equals(answer)){
            return origin.getScore();
        }
        return 0;
    }


    /**
     * 填空题得分统计
     * @param questionId 题目编号
     * @param answer 回答
     * @return 得分
     */
    private int fillQuestionJudgement(Integer questionId, String answer){
        FillQuestion origin = fillQuestionService.findByQuestionId(questionId);
        if (origin ==null) return 0;
        if (origin.getAnswer().trim().equals(answer.trim())){
            return origin.getScore();
        }
        return 0;
    }

    /**
     * 填空题得分统计
     * @param questionId 题目编号
     * @param answer 回答
     * @return 得分
     */
    private int judgeQuestionJudgement(Integer questionId, String answer){
        JudgeQuestion origin = judgeQuestionService.findByQuestionId(questionId);
        if (origin == null) return 0;
        if (origin.getAnswer().trim().equals(answer.trim())){
            return origin.getScore();
        }
        return 0;
    }
}
