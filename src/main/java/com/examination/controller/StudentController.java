package com.examination.controller;

import com.examination.service.StudentService;
import com.examination.util.AuthToken;
import com.examination.controller.common.BaseController;
import com.examination.entity.Result;
import com.examination.entity.Student;
import com.examination.service.impl.StudentServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "学生控制")
public class StudentController {

    StudentService studentService;
    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("查询所有学生信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page",value = "当前页数"),
//            @ApiImplicitParam(name = "pageId",value = "最大页面")})
    @GetMapping("/students")
    public Result findAll(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return Result.success(PageInfo.of(studentService.findAll()));
    }

    @ApiOperation("根据学生编号查询")
    @ApiImplicitParam(value = "学生编号",name = "studentId")
    @ApiResponse(code = 200,message = "查找成功")
    @GetMapping("/student")
    public Result findById(@RequestParam("studentId") Integer studentId){
        Student student = studentService.findById(studentId);
        if (student != null){
            return Result.success(student);
        }else {
            return Result.fail("不存在该学生", BaseController.STUDENT_DO_NOT_EXIST);
        }
    }

    @ApiOperation("根据学生编号删除")
    @ApiImplicitParam(value = "学生编号",name = "studentId")
    @DeleteMapping("/student")
    public Result delete(@RequestParam("studentId") Integer studentId){
        int res = studentService.deleteById(studentId);
        if (res == 1) {
            return Result.success();
        }else {
            return Result.fail("删除失败",BaseController.DELETE_FAIL);
        }
    }

    @ApiOperation("更新学生所有信息")
    @ApiImplicitParam(value = "学生实体类",name = "student",dataTypeClass = Student.class)
    @PutMapping("/student")
    public Result update(@RequestBody Student student) {
        if (studentService.update(student) != 0) return Result.success();
        return Result.fail("更新失败",BaseController.UPDATE_FAIL);
    }

    @ApiOperation("添加学生")
    @PostMapping("/student")
    @ApiImplicitParam(value = "学生实体类",name = "student",dataTypeClass = Student.class)
    public Result add(@RequestBody Student student) {
        int res = studentService.add(student);
        if (res == 1) {
            return Result.success();
        }else {
            return Result.fail("添加失败",BaseController.INSERT_FAIL);
        }
    }

    @ApiOperation("更改学生密码")
    @PutMapping("/student/modify")
    public Result updatePwd(@RequestParam("studentId")Integer studentId,@RequestParam("pwd") String pwd){
        int res = studentService.updatePwd(studentId,pwd);
        if (res == 1) {
            return Result.success();
        }else {
            return Result.fail("更改失败",BaseController.UPDATE_FAIL);
        }
    }
}
