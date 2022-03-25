package com.examination.service.impl;

import com.examination.entity.FillQuestion;
import com.examination.entity.JudgeQuestion;
import com.examination.entity.MultiQuestion;
import com.examination.service.question.FillQuestionService;
import com.examination.service.question.JudgeQuestionService;
import com.examination.service.question.MultiQuestionService;
import com.examination.service.question.QuestionImportService;
import com.examination.util.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionImportServiceImpl implements QuestionImportService {

    MultiQuestionService multiQuestionService;
    FillQuestionService fillQuestionService;
    JudgeQuestionService judgeQuestionService;

    @Autowired
    public QuestionImportServiceImpl(MultiQuestionService multiQuestionService, FillQuestionService fillQuestionService, JudgeQuestionService judgeQuestionService) {
        this.multiQuestionService = multiQuestionService;
        this.fillQuestionService = fillQuestionService;
        this.judgeQuestionService = judgeQuestionService;
    }

    @Override
    public int add(String file) {
        String[] data = CSVReader.readLine(file);
        if (data.length == 0) return 0;
        int fillCount = 0,multiCount = 0,judgeCount = 0;
        List<FillQuestion> fillQuestions = new ArrayList<>();
        List<MultiQuestion> multiQuestions = new ArrayList<>();
        List<JudgeQuestion> judgeQuestions = new ArrayList<>();
        for (String line:data){
            String[] words = line.split(",");
            String opt = words[0];
            System.out.println(opt);
            if (opt.equals("选择")) {
                MultiQuestion multiQuestion = new MultiQuestion();
                multiQuestion.setQuestion(words[1]);
                multiQuestion.setRightAnswer(words[2]);
                multiQuestion.setAnalysis(words[3]);
                multiQuestion.setScore(Integer.parseInt(words[4]));
                multiQuestion.setSubject(words[5]);
                multiQuestion.setLevel(words[6]);
                multiQuestion.setSection(words[7]);
                multiQuestion.setAnswerA(words[8]);
                multiQuestion.setAnswerB(words[9]);
                multiQuestion.setAnswerC(words[10]);
                multiQuestion.setAnswerD(words[11]);
                multiQuestions.add(multiQuestion);
                System.out.println(multiQuestion);
            }
            if (opt.equals("判断")) {
                JudgeQuestion judgeQuestion = new JudgeQuestion();
                judgeQuestion.setQuestion(words[1]);
                judgeQuestion.setAnswer(words[2]);
                judgeQuestion.setAnalysis(words[3]);
                judgeQuestion.setScore(Integer.parseInt(words[4]));
                judgeQuestion.setSubject(words[5]);
                judgeQuestion.setLevel(words[6]);
                judgeQuestion.setSection(words[7]);
                judgeQuestions.add(judgeQuestion);
                System.out.println(judgeQuestion);
            }
            if (opt.equals("填空")) {
                FillQuestion fillQuestion = new FillQuestion();
                fillQuestion.setQuestion(words[1]);
                fillQuestion.setAnswer(words[2]);
                fillQuestion.setAnalysis(words[3]);
                fillQuestion.setScore(Integer.parseInt(words[4]));
                fillQuestion.setSubject(words[5]);
                fillQuestion.setLevel(words[6]);
                fillQuestion.setSection(words[7]);
                fillQuestions.add(fillQuestion);
            }
        }
        for (MultiQuestion question :multiQuestions){
            multiCount += multiQuestionService.add(question);
        }
        for (FillQuestion question :fillQuestions){
            fillCount += fillQuestionService.add(question);
        }
        for (JudgeQuestion question :judgeQuestions){
            judgeCount += judgeQuestionService.add(question);
        }
        int countSum = multiCount + judgeCount + fillCount;
        log.info("导入{}题目，其中有选择{},填空{},判断{},",countSum,multiCount,fillCount,judgeCount);
        return countSum;
    }
}
