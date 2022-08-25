package com.watchman.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminTeacher;
import com.watchman.smartcampus.pojo.LoginForm;

/**
 * @author hongfengw
 * @create 2022-08-07 12:11
 * @Description:
 * @Version 1.0
 */
public interface AdminTeacherService extends IService<AdminTeacher> {

    AdminTeacher login(LoginForm loginForm);

    AdminTeacher getAdminAdminTeacherById(Long userId);

    IPage<AdminTeacher> getAdminByOpr(Page<AdminTeacher> page, AdminTeacher adminTeacher);
}
