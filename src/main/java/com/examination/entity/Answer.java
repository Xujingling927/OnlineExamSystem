package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "Answer",description = "学生答题")
public class Answer {

    @ApiModelProperty
    private Integer questionId;

    private Integer type;

    private String studentAnswer;

}
