package com.examination.controller;

import com.examination.component.AdminAuth;
import com.examination.component.LoginAuth;
import com.examination.service.StudentService;
import com.examination.controller.common.BaseController;
import com.examination.entity.Result;
import com.examination.entity.Student;
import com.examination.service.impl.StudentServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "学生控制")
public class StudentController {

    StudentService studentService;
    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @AdminAuth
    @ApiOperation(value = "查询所有学生信息",tags = "管理员权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize",value = "最大页面",dataTypeClass = Integer.class)})
    @GetMapping("/students")
    public Result findAll(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return Result.success(PageInfo.of(studentService.findAll()));
    }


    @LoginAuth
    @ApiOperation("根据学生编号查询")
    @ApiImplicitParam(value = "学生编号",name = "studentId")
    @ApiResponse(code = 200,message = "查找成功")
    @GetMapping("/student/{studentId}")
    public Result findById(@PathVariable("studentId") Integer studentId){
        Student student = studentService.findById(studentId);
        if (student != null){
            return Result.success(student);
        }else {
            return Result.fail("不存在该学生", BaseController.STUDENT_DO_NOT_EXIST);
        }
    }

//    @AdminAuth
//    @ApiOperation(value = "根据学生编号删除",tags = "管理员权限")
//    @ApiImplicitParam(value = "学生编号",name = "studentId")
//    @DeleteMapping("/student")
//    public Result delete(@RequestParam("studentId") Integer studentId){
//        int res = studentService.deleteById(studentId);
//        if (res == 1) {
//            return Result.success();
//        }else {
//            return Result.fail("删除失败",BaseController.DELETE_FAIL);
//        }
//    }

    @AdminAuth
    @ApiOperation(value ="批量删除学生",tags = "管理员权限")
    @ApiImplicitParams({@ApiImplicitParam (value = "学生编号列表",name = "list",dataType = "Integer")})
    @DeleteMapping("/student")
    public Result deleteMultiStudent(@RequestParam(name = "list") List<Integer> list){
        //删除成功数
        int count = 0;
        for (Integer student:list){
            log.info("studentId is {}",student);
            count += studentService.deleteById(student);
            log.info("成功删除{}个学生",count);
        }
        if (count == 0){
            return Result.fail("删除失败",BaseController.DELETE_FAIL);
        }
        else return Result.successMs("已成功删除"+count+"个学生");
    }

    @LoginAuth
    @ApiOperation("更新学生所有信息")
    @ApiImplicitParam(value = "学生实体类",name = "student",dataType ="Student")
    @PutMapping("/student")
    public Result update(@RequestBody Student student) {
        if (studentService.update(student) != 0) return Result.success();
        return Result.fail("更新失败",BaseController.UPDATE_FAIL);
    }

    @AdminAuth
    @ApiOperation(value = "添加学生",tags = "管理员权限")
    @PostMapping("/student")
    @ApiImplicitParam(value = "学生实体类",name = "student",dataType ="Student")
    public Result add(@RequestBody Student student) {
        String cardId = student.getCardId();
        String initPwd = "123456";
        if (cardId.length() > 6){
            initPwd = cardId.substring(cardId.length() - 6);
        }
        student.setPwd(initPwd);
        int res = studentService.add(student);
        if (res == 1) {
            return Result.success();
        }else {
            return Result.fail("添加失败",BaseController.INSERT_FAIL);
        }
    }

    @LoginAuth
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
