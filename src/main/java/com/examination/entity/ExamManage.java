package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ExamManage",description = "考试管理类")
public class ExamManage {

    @ApiModelProperty(name = "examCode",value = "考试码",position = 1)
    private Integer examCode;

    @ApiModelProperty(name = "description",value = "考试描述",position = 2)
    private String description;

    @ApiModelProperty(name = "source",value = "试题来源",position = 3)
    private String source;

    @ApiModelProperty(name = "paperId",value = "试卷编号",position = 4)
    private Integer paperId;

    @ApiModelProperty(name = "examDate",value = "考试时间",position = 5)
    private String examDate;

    @ApiModelProperty(name = "totalTime",value = "考试时长",position = 6)
    private Integer totalTime;

    @ApiModelProperty(name = "grade",value = "年级",position = 7)
    private String grade;

    @ApiModelProperty(name = "term",value = "学期",position = 8)
    private String term;

    @ApiModelProperty(name = "major",value = "主修专业",position = 9)
    private String major;

    @ApiModelProperty(name = "institute",value = "学院",position = 10)
    private String institute;

    @ApiModelProperty(name = "totalScore",value = "总分",position = 11)
    private Integer totalScore;

    @ApiModelProperty(name = "type",value = "考试类型",position = 12)
    private String type;

    @ApiModelProperty(name = "tips",value = "备注",position = 13)
    private String tips;
}