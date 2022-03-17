package com.examination.controller.question;

import com.examination.component.AdminAuth;
import com.examination.component.StudentAuth;
import com.examination.controller.common.BaseController;
import com.examination.entity.FillQuestion;
import com.examination.entity.JudgeQuestion;
import com.examination.entity.Result;
import com.examination.service.question.JudgeQuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "判断题接口")
@RestController
public class JudgeQuestionController {
    JudgeQuestionService judgeQuestionService;

    @Autowired
    public JudgeQuestionController(JudgeQuestionService judgeQuestionService) {
        this.judgeQuestionService = judgeQuestionService;
    }

    @ApiOperation(value = "获取所有的的判断题")
    @GetMapping("/judgeQuestions")
    public Result findAll(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(page,pageSize);
        return Result.success(PageInfo.of(judgeQuestionService.findAll()));
    }


    @ApiOperation(value = "获取该试卷编号下的所有判断题")
    @ApiImplicitParam(name = "paperId",value = "试卷编号")
    @GetMapping("/judgeQuestion")
    public Result findById(@RequestParam("paperId") Integer paperId) {
        List<JudgeQuestion> res = judgeQuestionService.findByPaperId(paperId);
        if (res.size()>0) return Result.success(res);
        else return Result.fail("查找失败",404);
    }

    @GetMapping("/judgeQuestion/")
    @ApiOperation("通过题目编号查找判断题")
    @ApiImplicitParam(name = "questionId",value = "题目编号")
    public Result findByQuestionId(@RequestParam("questionId") Integer questionId){
        JudgeQuestion judgeQuestion = judgeQuestionService.findByQuestionId(questionId);
        if (judgeQuestion != null) return Result.success(judgeQuestion);
        else return Result.fail("查找题目失败",404);
    }

    @AdminAuth
    @ApiOperation(value = "向题库中添加判断题",tags = "管理员权限")
    @ApiImplicitParam(name = "judgeQuestion",value = "判断题实体",dataType="JudgeQuestion")
    @PostMapping("judgeQuestion")
    public Result add(@RequestBody JudgeQuestion judgeQuestion){
        int res = judgeQuestionService.add(judgeQuestion);
        return res == 0 ? Result.fail("添加失败", BaseController.INSERT_FAIL) : Result.success();
    }
}
