package com.examination.controller.question;

import com.examination.component.AdminAuth;
import com.examination.controller.common.BaseController;
import com.examination.entity.FillQuestion;
import com.examination.entity.Result;
import com.examination.service.question.FillQuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@Api(tags = "填空题接口")
public class FillQuestionController {
    FillQuestionService fillQuestionService;

    @Autowired
    public FillQuestionController(FillQuestionService fillQuestionService) {
        this.fillQuestionService = fillQuestionService;
    }

    @GetMapping("/fillQuestions")
    @ApiOperation("查找题库中所有填空题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数"),
            @ApiImplicitParam(name = "pageSize",value = "页面大小")})
    public Result findAll(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(page,pageSize);
        return Result.success(PageInfo.of(fillQuestionService.findAll()));
    }

    @GetMapping("/fillQuestion")
    @ApiOperation("根据试卷编号查找该试卷中的所有填空题")
    @ApiImplicitParam(name = "paperId",value = "试卷编号")
    public Result findByPaperId(@RequestParam("paperId") Integer paperId){
        List<FillQuestion> list = fillQuestionService.findByPaperId(paperId);
        if (list.size()>0){
            return Result.success(list);
        }
        else return Result.fail("查找失败",404);
    }

    @GetMapping("/fillQuestion/")
    @ApiOperation("根据题目编号查找填空题")
    @ApiImplicitParam(name = "questionId",value = "题目编号",dataTypeClass = Integer.class)
    public Result findByQuestionId(@RequestParam("questionId") Integer questionId){
        FillQuestion fillQuestion = fillQuestionService.findByQuestionId(questionId);
        if (fillQuestion != null) return Result.success(fillQuestion);
        else return Result.fail("查找题目失败",404);
    }


    @AdminAuth
    @PostMapping("/fillQuestion")
    @ApiOperation(value = "向题库中添加新的填空题",tags = "管理员权限")
    @ApiImplicitParam(name = "fillQuestion",value = "填空题实体类",dataType = "FillQuestion",dataTypeClass = FillQuestion.class)
    public Result add(@RequestBody  FillQuestion fillQuestion){
        return fillQuestionService.add(fillQuestion)==0 ? Result.fail("添加失败", BaseController.INSERT_FAIL) : Result.success();
    }

}
