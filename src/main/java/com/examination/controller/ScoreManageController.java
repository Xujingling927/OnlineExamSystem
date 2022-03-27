package com.examination.controller;

import com.examination.component.AdminAuth;
import com.examination.component.LoginAuth;
import com.examination.controller.common.BaseController;
import com.examination.entity.Result;
import com.examination.entity.Score;
import com.examination.service.ScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "成绩管理")
@RestController
public class ScoreManageController {
    ScoreService scoreService;

    @Autowired
    public ScoreManageController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    //查询该学生的所有成绩
    @LoginAuth
    @ApiOperation("查询该学生的所有成绩")
    @ApiImplicitParam(name = "studentId",value = "学生编号",dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 404,message = "未找到任何结果"),
            @ApiResponse(code = 200,message = "成功")
    })
    @GetMapping("/score/student/{studentId}")
    public Result findByStudent(@PathVariable Integer studentId){
        List<Score> res = scoreService.findByStudentId(studentId);
        if (res.isEmpty()) return Result.fail("未找到任何结果",404);
        return Result.success(res);
    }

    //查询该场考试所有学生的成绩
    @LoginAuth
    @ApiOperation("查询该场考试所有学生的成绩")
    @ApiImplicitParam(name = "examCode",value = "考试编号",dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 404,message = "未找到任何结果"),
            @ApiResponse(code = 200,message = "成功")
    })
    @GetMapping("/score/exam/{examCode}")
    public Result findByExamCode(@PathVariable("examCode") Integer examCode){
        List<Score> res = scoreService.findByExamCode(examCode);
        if (res.isEmpty()) return Result.fail("未找到任何结果",404);
        return Result.success(res);
    }


    //查找所有成绩
    @AdminAuth
    @ApiOperation("查找所有成绩")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageSize",value = "页面大小",defaultValue = "10"),
        @ApiImplicitParam(name = "page",value = "当前页数",defaultValue = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 404,message = "未找到任何结果"),
            @ApiResponse(code = 200,message = "成功")
    })
    @GetMapping("/scores")
    public Result findAll(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<Score> res = scoreService.findAll();
        if (res.isEmpty()) return Result.fail("未找到任何结果",404);
        return Result.success(PageInfo.of(res));
    }

    //删除成绩
    @AdminAuth
    @ApiOperation(value = "删除成绩",notes = "支持批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list",value = "成绩编号",dataType = "Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 404,message = "删除失败"),
            @ApiResponse(code = 200,message = "已成功删除XX条记录")
    })
    @DeleteMapping("/score")
    public Result delete(@RequestParam(name = "list") List<Integer> list){
        //删除成功数
        int count = 0;
        for (Integer score:list){
            count += scoreService.delete(score);
        }
        if (count == 0){
            return Result.fail("删除失败", BaseController.DELETE_FAIL);
        }
        else return Result.successMs("已成功删除"+count+"个学生");
    }


}
