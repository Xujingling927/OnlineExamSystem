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

    @ApiModelProperty(name = "paperId",value = "试卷编号",position = 1)
    private Integer paperId;

    // 1-选择 2-填空 3-判断
    @ApiModelProperty(name = "questionType",value = "题目类型",position = 2)
    private Integer questionType;

    @ApiModelProperty(name = "questionId",value = "试题编号",position = 3)
    private Integer questionId;

}