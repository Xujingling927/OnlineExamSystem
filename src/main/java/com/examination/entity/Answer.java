package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "Answer",description = "学生答题")
public class Answer {

    @ApiModelProperty(name = "questionId",value = "题目编号")
    private Integer questionId;

    @ApiModelProperty(name = "questionType",value = "题目类型")
    private Integer questionType;

    @ApiModelProperty(name = "studentAnswer",value = "学生回答")
    private String studentAnswer;

}
