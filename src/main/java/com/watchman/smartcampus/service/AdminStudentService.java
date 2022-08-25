package com.watchman.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.watchman.smartcampus.pojo.AdminGrade;
import com.watchman.smartcampus.pojo.AdminStudent;
import com.watchman.smartcampus.pojo.LoginForm;

/**
 * @author hongfengw
 * @create 2022-08-07 12:11
 * @Description:
 * @Version 1.0
 */
public interface AdminStudentService extends IService<AdminStudent> {

    AdminStudent login(LoginForm loginForm);

    AdminStudent getAdminStudentById(Long userId);

    IPage<AdminStudent> getStudentByOpr(Page<AdminStudent> page, AdminStudent adminStudent);
}
