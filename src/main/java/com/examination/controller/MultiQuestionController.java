package com.examination.controller;

import com.examination.controller.common.BaseController;
import com.examination.entity.MultiQuestion;
import com.examination.entity.Result;
import com.examination.service.impl.MultiQuestionServiceImpl;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "选择题接口")
@RestController
public class MultiQuestionController {
    private final MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    public MultiQuestionController(MultiQuestionServiceImpl multiQuestionService) {
        this.multiQuestionService = multiQuestionService;
    }


    @ApiOperation(value = "获取所有的的选择题")
    @GetMapping("/multiQuestions")
    public Result findAll(){
        return Result.success(multiQuestionService.findAll());
    }

    @ApiOperation(value = "获取该试卷编号下的所有选择题")
    @ApiImplicitParam(name = "paperId",value = "试卷编号")
    @GetMapping("/multiQuestion")
    public Result findById(@RequestParam Integer paperId) {
        List<MultiQuestion> res = multiQuestionService.findById(paperId);
        return Result.success(res);
    }

    @ApiOperation("向题库中添加选择题")
    @ApiImplicitParam(name = "multiQuestion",value = "选择题实体")
    @PostMapping("multiQuestion")
    public Result add(@RequestBody MultiQuestion multiQuestion){
        int res = multiQuestionService.add(multiQuestion);
        return res == 0 ? Result.fail("添加失败", BaseController.INSERT_FAIL) : Result.success();
    }
}
