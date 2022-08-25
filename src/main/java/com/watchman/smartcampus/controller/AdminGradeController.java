package com.watchman.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watchman.smartcampus.pojo.AdminGrade;
import com.watchman.smartcampus.service.AdminGradeService;
import com.watchman.smartcampus.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hongfengw
 * @create 2022-08-07 15:11
 * @Description: 年级
 * @Version 1.0
 */
@Api("年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class AdminGradeController {

    @Autowired
    AdminGradeService adminGradeService;

    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){

        List<AdminGrade> adminGradesList = adminGradeService.getGrades();
        return Result.ok(adminGradesList);
    }


    @ApiOperation("删除Grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的Grade id json集合") @RequestBody List<Integer> ids){
        //调用服务层方法
        adminGradeService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("新增或者修改，有id 修改，无id新增")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("json的Grade对象") @RequestBody AdminGrade adminGrade){
        //调用服务层的方法完成新增或者修改
        adminGradeService.saveOrUpdate(adminGrade);
        return Result.ok();
    }

    @ApiOperation("根据年级名称模糊查询，带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
                            @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
                            @ApiParam("分页查询模糊匹配的名称") String gradeName
    ) {

        //分页 带条件查询
        Page<AdminGrade> adminGradePage = new Page<>(pageNo, pageSize);
        //通过服务层
        IPage<AdminGrade> pageRs = adminGradeService.getGradeByOpr(adminGradePage,gradeName);
        //封装Result对象并返回
        return Result.ok(pageRs);
    }

}
