package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("成绩管理类")
public class Score {

    @ApiModelProperty(name = "examCode",value = "考试码")
    private Integer examCode;

    @ApiModelProperty(name = "studentId",value = "学生编号")
    private Integer studentId;

    @ApiModelProperty(name = "subject",value = "考试科目")
    private String subject;

    @ApiModelProperty(name = "score",value = "总成绩")
    private Integer score;

    @ApiModelProperty(name = "scoreId",value = "分数编号")
    private Integer scoreId;

    @ApiModelProperty(name = "answerDate",value = "答题时间")
    private String answerDate;
}