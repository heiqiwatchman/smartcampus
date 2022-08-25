package com.watchman.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminStudent;
import com.watchman.smartcampus.service.AdminStudentService;
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
@RequestMapping("/sms/studentController")
public class AdminStudentController {

    @Autowired
    AdminStudentService adminStudentService;

    @ApiOperation("删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("要删除的Student id json集合") @RequestBody List<Integer> ids){
        //调用服务层方法
        adminStudentService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("新增或者修改，有id 修改，无id新增")
    @PostMapping("/saveOrUpdateStudent")
    public Result saveOrUpdateStudent(@ApiParam("json的Student对象") @RequestBody AdminStudent adminStudent){
        //调用服务层的方法完成新增或者修改
        Integer id = adminStudent.getId();
        if(null == id || 0 == id){
            adminStudent.setPassword(MD5.encrypt(adminStudent.getPassword()));
        }
        adminStudentService.saveOrUpdate(adminStudent);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件") AdminStudent adminStudent){

        Page<AdminStudent> page = new Page<>(pageNo,pageSize);
        IPage<AdminStudent> iPage = adminStudentService.getStudentByOpr(page,adminStudent);

        return Result.ok(iPage);
    }

}
