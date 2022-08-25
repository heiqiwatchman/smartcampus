package com.watchman.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.watchman.smartcampus.mapper.AdminUserMapper;
import com.watchman.smartcampus.pojo.AdminStudent;
import com.watchman.smartcampus.pojo.AdminUser;
import com.watchman.smartcampus.pojo.LoginForm;
import com.watchman.smartcampus.service.AdminUserService;
import com.watchman.smartcampus.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author hongfengw
 * @create 2022-08-07 12:12
 * @Description:
 * @Version 1.0
 */
@Service("adminUserServiceImpl")
@Transactional
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Override
    public AdminUser login(LoginForm loginForm) {

        QueryWrapper<AdminUser> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        AdminUser adminUser = this.baseMapper.selectOne(queryWrapper);
        return adminUser;
    }

    @Override
    public AdminUser getAdminUserById(Long userId) {
        AdminUser adminUser = this.baseMapper.selectById(userId);
        return adminUser;
    }

    @Override
    public IPage<AdminUser> getAdminByOpr(Page<AdminUser> page, String adminName) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(adminName)){
            queryWrapper.like("name",adminName);
        }
        queryWrapper.orderByDesc("id");
        Page<AdminUser> pages = this.baseMapper.selectPage(page, queryWrapper);
        return pages;
    }
}
