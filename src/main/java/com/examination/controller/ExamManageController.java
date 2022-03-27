package com.examination.controller;

import com.examination.component.AdminAuth;
import com.examination.component.LoginAuth;
import com.examination.controller.common.BaseController;
import com.examination.entity.ExamManage;
import com.examination.entity.PaperManage;
import com.examination.entity.Result;
import com.examination.service.ExamManageService;
import com.examination.service.PaperService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "考试管理接口")
@RestController
public class ExamManageController {
    ExamManageService examManageService;
    PaperService paperService;


    @Autowired
    public ExamManageController(ExamManageService examManageService, PaperService paperService) {
        this.examManageService = examManageService;
        this.paperService = paperService;
    }



//    public Result findAll(){
//        List<ExamManage> res = examManageService.findAll();
//        if (res.isEmpty()) return Result.fail("目前还没有考试",404);
//        return Result.success(res);
//    }
    @LoginAuth
    @ApiOperation(value = "查询所有考试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",defaultValue = "10")})
    @ApiResponses({
            @ApiResponse(code = 404, message = "目前还没有考试"),
            @ApiResponse(code = 200, message = "成功")
    })
    @GetMapping("/exams")
    public Result findAll(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        List<ExamManage> res = examManageService.findAll();
        PageHelper.startPage(page,pageSize);
        if (res.isEmpty()) return Result.fail("目前还没有考试",404);
        return Result.success(PageInfo.of(res));
    }


    @LoginAuth
    @ApiOperation(value = "通过考试码查找")
    @ApiImplicitParam(name = "examCode",value = "考试码")
    @ApiResponses({
            @ApiResponse(code = 404,message = "没有找到该考试"),
            @ApiResponse(code = 200,message = "成功")
    })
    @GetMapping("/exam/code/{examCode}")
    public Result findByExamCode(@PathVariable("examCode") Integer examCode){
        ExamManage examManage = examManageService.findByExamCode(examCode);
        if (examManage == null) return Result.fail("没有找到该考试",404);
        return Result.success(examManage);
    }


    @LoginAuth
    @ApiOperation(value = "通过试卷编号查询有哪些考试使用了该套试卷")
    @ApiImplicitParam(name = "paperId",value = "试卷编号",dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 404,message = "没有结果"),
            @ApiResponse(code = 200,message = "成功",responseContainer = "List")
    })
    @GetMapping("/exam/paper/{paperId}")
    public Result findByPaperId(@PathVariable("paperId") Integer paperId){
        List<ExamManage> examManages = examManageService.findByPaperId(paperId);
        if (examManages.isEmpty()) return Result.fail("没有结果",404);
        return Result.success(examManages);
    }


    @AdminAuth
    @ApiOperation(value = "添加考试")
    @ApiImplicitParam(value = "考试管理实体",dataType = "ExamManage",name = "examManage")
    @ApiResponses({
            @ApiResponse(code = BaseController.INSERT_FAIL,message = "添加失败"),
            @ApiResponse(code = 200,message = "成功")
    })
    @PostMapping("/exam")
    public Result add(@RequestBody ExamManage examManage){
        if (examManageService.add(examManage)==0){
            return Result.fail("添加失败",BaseController.INSERT_FAIL);
        }
        return Result.successMs("添加成功");
    }

    @AdminAuth
    @ApiOperation(value = "通过试卷编号删除",notes = "可以批量删除")
    @ApiImplicitParam(name = "list" ,value = "试卷编号",dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = BaseController.DELETE_FAIL,message = "删除失败"),
            @ApiResponse(code = 200,message = "已成功删除XX个考试")
    })
    @DeleteMapping("/exam")
    public Result delete(@RequestParam("list") List<Integer> list){
        int count = 0;
        for (Integer exam:list){
            count += examManageService.delete(exam);
        }
        if (count == 0){
            return Result.fail("删除失败",BaseController.DELETE_FAIL);
        }
        else return Result.successMs("已成功删除"+count+"个考试");
    }

    //用 questionId 获取哪些考试使用到了这个题目
    @LoginAuth
    @ApiOperation("获取哪些考试使用到了这个题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId",value = "题目编号",dataType = "Integer"),
            @ApiImplicitParam(name = "type",value = "类型",dataType = "Integer")
    })
    @GetMapping("/exam/question/{questionId}/type/{type}")
    public Result findByQuestionId(@PathVariable("questionId") Integer questionId,@PathVariable("type") Integer type){
        List<Integer> paperIdList = paperService.findByQuestionId(questionId,type);
        List<ExamManage> res =  new ArrayList<>();
        for (Integer paperId:paperIdList){
            res.addAll(examManageService.findByPaperId(paperId));
        }
        if (res.isEmpty()) return Result.fail("没有找到任何结果",404);
        return Result.success(res);
    }
}
