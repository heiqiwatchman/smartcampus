package com.watchman.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.watchman.smartcampus.pojo.AdminUser;
import com.watchman.smartcampus.pojo.LoginForm;

/**
 * @author hongfengw
 * @create 2022-08-07 12:11
 * @Description:
 * @Version 1.0
 */
public interface AdminUserService extends IService<AdminUser> {

    AdminUser login(LoginForm loginForm);

    AdminUser getAdminUserById(Long userId);

    IPage<AdminUser> getAdminByOpr(Page<AdminUser> page, String adminName);
}
