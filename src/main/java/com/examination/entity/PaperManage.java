package com.examination.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "PaperManage",description = "试卷管理类")
@NoArgsConstructor
@AllArgsConstructor
public class PaperManage {

    @ApiModelProperty(name = "paperId",value = "试卷编号",position = 1,example = "123")
    private Integer paperId;

    @ApiModelProperty(name = "questionType",value = "题目类型",position = 2,example = "1")
    private Integer questionType;

    @ApiModelProperty(name = "questionId",value = "试题编号",position = 3,example = "123")
    private Integer questionId;

}