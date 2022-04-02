package com.examination.entity;

import lombok.Data;

@Data
public class ErrorQuestion {
    private Integer errorId;

    private Integer studentId;

    private Integer questionId;

    private Integer questionType;

    private String studentAnswer;

    private Boolean status;
}
