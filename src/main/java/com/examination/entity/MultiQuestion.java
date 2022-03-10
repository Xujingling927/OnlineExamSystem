package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 选择题实体
@Data
@ApiModel("选择题实体类")
public class MultiQuestion {

    @ApiModelProperty(name = "questionId",value = "试题编号",position = 1)
    private Integer questionId;

    @ApiModelProperty(name = "subject",value = "考试科目",position = 2)
    private String subject;

    @ApiModelProperty(name = "section",value = "所属章节",position = 3)
    private String section;

    @ApiModelProperty(name = "answerA",value = "选项A",position = 4)
    private String answerA;

    @ApiModelProperty(name = "answerB",value = "选项B",position = 5)
    private String answerB;

    @ApiModelProperty(name = "answerC",value = "选项C",position = 6)
    private String answerC;

    @ApiModelProperty(name = "answerD",value = "选项D",position = 7)
    private String answerD;

    @ApiModelProperty(name = "question",value = "试题内容",position = 8)
    private String question;

    @ApiModelProperty(name = "level",value = "难度等级",position = 9)
    private String level;

    @ApiModelProperty(name = "rightAnswer",value = "正确选项",position = 10)
    private String rightAnswer;

    @ApiModelProperty(name = "analysis",value = "试题解析",position = 11)
    private String analysis; //题目解析

    @ApiModelProperty(name = "score",value = "试题分数",position = 12)
    private Integer score;

}