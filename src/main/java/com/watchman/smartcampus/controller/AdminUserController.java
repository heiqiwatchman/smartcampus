package com.watchman.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watchman.smartcampus.pojo.AdminStudent;
import com.watchman.smartcampus.pojo.AdminUser;
import com.watchman.smartcampus.service.AdminUserService;
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
@RequestMapping("/sms/adminController")
public class AdminUserController{


    @Autowired
    AdminUserService adminUserService;

    @ApiOperation("删除管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("要删除的管理员 id json集合") @RequestBody List<Integer> ids){
        //调用服务层方法
        adminUserService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("新增或者修改，有id 修改，无id新增")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("json的Student对象") @RequestBody AdminUser adminUser){
        //调用服务层的方法完成新增或者修改
        Integer id = adminUser.getId();
        if(null == id || 0 == id){
            adminUser.setPassword(MD5.encrypt(adminUser.getPassword()));
        }
        adminUserService.saveOrUpdate(adminUser);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件 管理员名称") String adminName){

        Page<AdminUser> page = new Page<>(pageNo,pageSize);
        IPage<AdminUser> iPage = adminUserService.getAdminByOpr(page,adminName);

        return Result.ok(iPage);
    }

}
