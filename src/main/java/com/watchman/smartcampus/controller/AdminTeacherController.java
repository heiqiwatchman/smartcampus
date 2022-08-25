package com.watchman.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watchman.smartcampus.pojo.AdminTeacher;
import com.watchman.smartcampus.pojo.AdminUser;
import com.watchman.smartcampus.service.AdminTeacherService;
import com.watchman.smartcampus.util.MD5;
import com.watchman.smartcampus.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hongfengw
 * @create 2022-08-07 15:11
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/sms/teacherController")
public class AdminTeacherController {

    @Autowired
    AdminTeacherService adminTeacherService;

    @ApiOperation("删除教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@ApiParam("要删除的教师 id json集合") @RequestBody List<Integer> ids){
        //调用服务层方法
        adminTeacherService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("新增或者修改，有id 修改，无id新增")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@ApiParam("json的教师对象") @RequestBody AdminTeacher adminTeacher){
        //调用服务层的方法完成新增或者修改
        Integer id = adminTeacher.getId();
        if(null == id || 0 == id){
            adminTeacher.setPassword(MD5.encrypt(adminTeacher.getPassword()));
        }
        adminTeacherService.saveOrUpdate(adminTeacher);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询管理员信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件 管理员名称") AdminTeacher adminTeacher){

        Page<AdminTeacher> page = new Page<>(pageNo,pageSize);
        IPage<AdminTeacher> iPage = adminTeacherService.getAdminByOpr(page,adminTeacher);

        return Result.ok(iPage);
    }

}
