package com.examination.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel()
public class Message {
    private Integer id;
    private Integer temp_id;//解决id为null创建的一个临时id

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date time;

    List<Replay> replays;   //一对多关系，评论信息
}