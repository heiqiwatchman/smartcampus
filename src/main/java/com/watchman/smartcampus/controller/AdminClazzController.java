package com.watchman.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.AbstractService;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminGrade;
import com.watchman.smartcampus.service.AdminClazzService;
import com.watchman.smartcampus.util.Result;
import io.swagger.annotations.Api;
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
@Api("班级管理控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class AdminClazzController {

    @Autowired
    AdminClazzService adminClazzService;

    @ApiOperation("获取全部班级")
    @GetMapping("/getClazzs")
    public Result getClazzs(){

        List<AdminClazz> adminClazzsList = adminClazzService.getClazzs();
        return Result.ok(adminClazzsList);
    }
    @ApiOperation("删除Clazz信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteGrade(@ApiParam("要删除的Clazz id json集合") @RequestBody List<Integer> ids){
        //调用服务层方法
        adminClazzService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("新增或者修改，有id 修改，无id新增")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("json的Grade对象") @RequestBody AdminClazz adminClazz){
        //调用服务层的方法完成新增或者修改
        adminClazzService.saveOrUpdate(adminClazz);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件") AdminClazz adminClazz){

        Page<AdminClazz> page = new Page<>(pageNo,pageSize);
        IPage<AdminClazz> iPage = adminClazzService.getClazzByOpr(page,adminClazz);

        return Result.ok(iPage);
    }

}
