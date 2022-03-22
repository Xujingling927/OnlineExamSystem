package com.examination.controller;

import com.examination.component.AdminAuth;
import com.examination.component.LoginAuth;
import com.examination.controller.common.BaseController;
import com.examination.entity.Admin;
import com.examination.entity.Result;
import com.examination.service.impl.AdminServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@LoginAuth
@RestController
@Api(tags = "管理员控制")
public class AdminController {
    AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }


    @LoginAuth
    @ApiOperation("根据管理员编号查询")
    @ApiImplicitParam(value = "管理员编号",name = "adminId")
    @GetMapping("/admin/{adminId}")
    public Result findById(@PathVariable("adminId") Integer adminId){
        Admin admin = adminService.findById(adminId);
        if (admin != null){
            return Result.success(admin);
        }else {
            return Result.fail("管理员不存在", BaseController.ADMIN_DO_NOT_EXIST);
        }
    }

    @AdminAuth
    @ApiOperation(value = "根据管理员编号删除",tags = "管理员权限")
    @ApiImplicitParam(value = "管理员编号",name = "adminId")
    @DeleteMapping("/admin/{adminId}")
    public Result delete(@PathVariable("adminId") Integer adminId){
        int res = adminService.deleteById(adminId);
        if (res == 1) {
            return Result.success();
        }else {
            return Result.fail("删除失败",BaseController.DELETE_FAIL);
        }
    }

    @AdminAuth
    @ApiOperation(value = "更新管理员信息",tags = "管理员权限")
    @ApiImplicitParam(value = "管理员实体类",name = "admin",dataType = "Admin")
    @PutMapping("/admin")
    public Result update(@RequestBody Admin admin) {
        if(adminService.update(admin) != 0) return Result.success();
        return Result.fail("更新失败",BaseController.UPDATE_FAIL);
    }

    @AdminAuth
    @ApiOperation(value = "添加管理员",tags = "管理员权限")
    @PostMapping("/admin")
    @ApiImplicitParam(value = "管理员实体类",name = "admin",dataType = "Admin")
    public Result add(@RequestBody Admin admin) {
        int res = adminService.add(admin);
        if (res == 1) {
            return Result.success();
        }else {
            return Result.fail("添加失败", BaseController.INSERT_FAIL);
        }
    }

//    @ApiOperation("更改管理员密码")
//    @PutMapping("/admin/modify")
//    @ApiImplicitParams({
//            @ApiImplicitParam(value = "管理员编号",name = "adminId"),
//            @ApiImplicitParam(value = "新密码",name="pwd")
//    })
//    public ApiResult updatePwd(@RequestParam("adminId")Integer adminId,@RequestParam("pwd") String pwd){
//        int res = adminService.updatePwd(adminId,pwd);
//        if (res == 1) {
//            return ApiResultHandler.buildApiResult(200,"更改成功",null);
//        }else {
//            return ApiResultHandler.buildApiResult(400,"更改失败",null);
//        }
//    }
}
