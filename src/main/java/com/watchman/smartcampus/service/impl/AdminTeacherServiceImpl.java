package com.watchman.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.watchman.smartcampus.mapper.AdminClazzMapper;
import com.watchman.smartcampus.mapper.AdminTeacherMapper;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminTeacher;
import com.watchman.smartcampus.pojo.AdminUser;
import com.watchman.smartcampus.pojo.LoginForm;
import com.watchman.smartcampus.service.AdminClazzService;
import com.watchman.smartcampus.service.AdminTeacherService;
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
@Service("adminTeacherServiceImpl")
@Transactional
public class AdminTeacherServiceImpl extends ServiceImpl<AdminTeacherMapper, AdminTeacher> implements AdminTeacherService {

    @Override
    public AdminTeacher login(LoginForm loginForm) {

        QueryWrapper<AdminTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        AdminTeacher adminTeacher = this.baseMapper.selectOne(queryWrapper);
        return adminTeacher;
    }

    @Override
    public AdminTeacher getAdminAdminTeacherById(Long userId) {

        AdminTeacher adminTeacher = this.baseMapper.selectById(userId);
        return adminTeacher;
    }

    @Override
    public IPage<AdminTeacher> getAdminByOpr(Page<AdminTeacher> page, AdminTeacher adminTeacher) {
        QueryWrapper<AdminTeacher> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(adminTeacher.getName())){
            queryWrapper.like("name",adminTeacher.getName());
        }
        if(!StringUtils.isEmpty(adminTeacher.getClazzName())){
            queryWrapper.like("clazz_name",adminTeacher.getClazzName());
        }
        queryWrapper.orderByDesc("id");
        Page<AdminTeacher> pages = this.baseMapper.selectPage(page, queryWrapper);
        return pages;
    }
}
