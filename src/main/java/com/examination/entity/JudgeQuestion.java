package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//判断题实体类
@Data
@ApiModel(value = "JudgeQuestion",description = "判断题实体类")
public class JudgeQuestion {

    @ApiModelProperty(name = "questionId",value = "试题编号",position = 1)
    private Integer questionId;

    @ApiModelProperty(name = "subject",value = "考试科目",position = 2)
    private String subject;

    @ApiModelProperty(name = "question",value = "试题内容",position = 3)
    private String question;

    @ApiModelProperty(name = "answer",value = "正确答案",position = 4)
    private String answer;

    @ApiModelProperty(name = "section",value = "所属章节",position = 5)
    private String section;

    @ApiModelProperty(name = "score",value = "题目分数",position = 6)
    private Integer score;

    @ApiModelProperty(name = "level",value = "难度等级",position = 7)
    private String level;

    @ApiModelProperty(name = "analysis",value = "题目解析",position = 8)
    private String analysis; //题目解析
}