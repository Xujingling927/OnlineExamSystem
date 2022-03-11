package com.examination.controller;

import com.examination.entity.Admin;
import com.examination.entity.ApiResult;
import com.examination.entity.Result;
import com.examination.serviceImpl.AdminServiceImpl;
import com.examination.util.ApiResultHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "管理员控制")
public class AdminController {
    AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @ApiOperation("根据管理员编号查询")
    @ApiImplicitParam(value = "管理员编号",name = "adminId")
    @GetMapping("/admin")
    public ApiResult findById(@RequestParam("adminId") Integer adminId){
        Admin admin = adminService.findById(adminId);
        if (admin != null){
            return ApiResultHandler.success(admin);
        }else {
            return ApiResultHandler.buildApiResult(404," 不存在该管理员",null);
        }
    }

    @ApiOperation("根据管理员编号删除")
    @ApiImplicitParam(value = "管理员编号",name = "adminId")
    @DeleteMapping("/admin")
    public ApiResult delete(@RequestParam("adminId") Integer adminId){
        int res = adminService.deleteById(adminId);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"删除成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"删除失败",null);
        }
    }

    @ApiOperation("更新管理员信息")
    @ApiImplicitParam(value = "管理员实体类",name = "admin",dataType = "Admin")
    @PutMapping("/admin")
    public Result update(@RequestBody Admin admin) {
        if(adminService.update(admin) != 0) return Result.success();
        return Result.fail("未更新",1002);
    }

    @ApiOperation("添加管理员")
    @PostMapping("/admin")
    @ApiImplicitParam(value = "管理员实体类",name = "admin",dataType = "Admin")
    public ApiResult add(@RequestBody Admin admin) {
        int res = adminService.add(admin);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"添加成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"添加失败",null);
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
