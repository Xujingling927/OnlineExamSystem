package com.examination.controller;

import com.examination.entity.ApiResult;
import com.examination.entity.Student;
import com.examination.service.StudentService;
import com.examination.serviceImpl.StudentServiceImpl;
import com.examination.util.ApiResultHandler;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api
public class StudentController {
    StudentService studentService;
    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("查询所有学生信息")
    @GetMapping("/students")
    public ApiResult findAll() {
        System.out.println(studentService.getClass().getName());
        return ApiResultHandler.success(studentService.findAll());
    }

    @ApiOperation("根据学生编号查询")
    @ApiImplicitParam(value = "学生编号",name = "studentId")
    @ApiResponse(code = 200,message = "查找成功")
    @GetMapping("/student")
    public ApiResult findById(@RequestParam("studentId") Integer studentId){
        Student student = studentService.findById(studentId);
        if (student != null){
            return ApiResultHandler.success(student);
        }else {
            return ApiResultHandler.buildApiResult(404," 不存在该学生",null);
        }
    }

    @ApiOperation("根据学生编号删除")
    @ApiImplicitParam(value = "学生编号",name = "studentId")
    @DeleteMapping("/student")
    public ApiResult delete(@RequestParam("studentId") Integer studentId){
        int res = studentService.deleteById(studentId);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"删除成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"删除失败",null);
        }
    }

    @ApiOperation("更新学生所有信息")
    @ApiImplicitParam(value = "学生实体类",name = "student")
    @PutMapping("/student")
    public int update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @ApiOperation("添加学生")
    @PostMapping("/student")
    @ApiImplicitParam(value = "学生实体类",name = "student")
    public ApiResult add(@RequestBody Student student) {
        int res = studentService.add(student);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"添加成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"添加失败",null);
        }
    }

    @ApiOperation("更改学生密码")
    @PutMapping("/student/modify")
    public ApiResult updatePwd(@RequestParam("studentId")Integer studentId,@RequestParam("pwd") String pwd){
        int res = studentService.updatePwd(studentId,pwd);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"更改成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"更改失败",null);
        }
    }
}
