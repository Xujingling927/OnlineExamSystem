package com.examination.entity;

import lombok.Data;

import java.util.Date;

/**
 * 考试管理类
 */
@Data
public class ExamManage {
    //考试编号
    private Integer examCode;
    //考试描述
    private String description;
    //试题来源
    private String source;
    //试卷编号
    private Integer paperId;
    //考试时间
    private Date examDate;
    //考试时长
    private Integer totalTime;
    //考试年级
    private String grade;
    //考试学期
    private String term;
    //主修专业
    private String major;
    //发布机构名称
    private String institute;
    //考试总分
    private Integer totalScore;
    //考试类型
    private String type;
    //考试备注
    private String tips;
}