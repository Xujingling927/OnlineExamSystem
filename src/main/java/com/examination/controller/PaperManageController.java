package com.examination.controller;

import com.examination.entity.PaperManage;
import com.examination.entity.Result;
import com.examination.service.PaperService;
import com.examination.serviceImpl.PaperServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "试卷管理")
@RestController
public class PaperManageController {
    PaperServiceImpl paperService;

    @Autowired
    public PaperManageController(PaperServiceImpl paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/papers")
    public Result findAll(){
        List<PaperManage> res = paperService.findAll();
        if (res!=null) return Result.success(res);
        return Result.fail("试卷查找失败",1003);
    }

    @ApiImplicitParam(value = "试卷编号",name = "paperId")
    @GetMapping("/paper")
    public Result findById(@RequestParam("paperId") Integer paperId){
        List<PaperManage> res = paperService.findById(paperId);
        if (res!=null) return Result.success(res);
        return Result.fail("试卷查找失败",1003);
    }

    @ApiImplicitParam(value = "试卷实体类",name = "paperManage",dataType = "PaperManage")
    @PutMapping("/paper")
    public Result add(@RequestBody PaperManage paperManage){
        if (paperService.add(paperManage)!= 0){
            return Result.success();
        }
        return Result.fail("添加失败",1004);
    }
}
