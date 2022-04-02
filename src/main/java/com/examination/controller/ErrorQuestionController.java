package com.examination.controller;

import com.examination.component.LoginAuth;
import com.examination.entity.Result;
import com.examination.service.question.ErrorQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/error-question")
@Api(tags = "错题管理")
public class ErrorQuestionController {

    ErrorQuestionService errorQuestionService;

    @Autowired
    public ErrorQuestionController(ErrorQuestionService errorQuestionService) {
        this.errorQuestionService = errorQuestionService;
    }

    @LoginAuth
    @ApiOperation("更新掌握状态")
    @ApiImplicitParam(name = "errorId",value = "错题编号")
    @PutMapping("/id/{errorId}")
    public Result changeStatus(@PathVariable Integer errorId){
        return Result.success(errorQuestionService.setStatus(errorId));
    }


    @LoginAuth
    @ApiOperation("删除某个错题记录")
    @ApiImplicitParam(name = "errorId",value = "错题编号")
    @DeleteMapping("/id/{errorId}")
    public Result delete(@PathVariable Integer errorId){
        return Result.success(errorQuestionService.delete(errorId));
    }


    @LoginAuth
    @ApiOperation("根据学生编号查找做错过的题目")
    @ApiImplicitParam(name = "studentId",value = "学生编号")
    @GetMapping("/student/{studentId}")
    public Result findByStudentId(@PathVariable Integer studentId){

        return Result.success(errorQuestionService.findByStudentId(studentId));
    }
}
