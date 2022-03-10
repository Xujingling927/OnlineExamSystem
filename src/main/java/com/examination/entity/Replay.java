package com.examination.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("回复实体类")
public class Replay {
    @ApiModelProperty(name = "messageId",value = "留言编号")
    private Integer messageId;

    @ApiModelProperty(name = "replayId",value = "回复编号")
    private Integer replayId;

    @ApiModelProperty(name = "replay",value = "内容")
    private String replay;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @ApiModelProperty(name = "replayTime",value = "回复时间")
    private Date replayTime;
}