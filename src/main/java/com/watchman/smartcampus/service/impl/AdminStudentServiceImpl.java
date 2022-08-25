package com.watchman.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.watchman.smartcampus.mapper.AdminStudentMapper;
import com.watchman.smartcampus.pojo.AdminGrade;
import com.watchman.smartcampus.pojo.AdminStudent;
import com.watchman.smartcampus.pojo.LoginForm;
import com.watchman.smartcampus.service.AdminStudentService;
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
@Service("adminStudentServiceImpl")
@Transactional
public class AdminStudentServiceImpl extends ServiceImpl<AdminStudentMapper, AdminStudent> implements AdminStudentService {

    @Override
    public AdminStudent login(LoginForm loginForm) {

        QueryWrapper<AdminStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        AdminStudent adminStudent = this.baseMapper.selectOne(queryWrapper);
        return adminStudent;
    }

    @Override
    public AdminStudent getAdminStudentById(Long userId) {
        AdminStudent adminStudent = this.baseMapper.selectById(userId);
        return adminStudent;
    }

    @Override
    public IPage<AdminStudent> getStudentByOpr(Page<AdminStudent> page, AdminStudent adminStudent) {
        QueryWrapper<AdminStudent> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(adminStudent.getName())){
            queryWrapper.like("name",adminStudent.getName());
        }
        if(!StringUtils.isEmpty(adminStudent.getClazzName())){
            queryWrapper.like("clazz_name",adminStudent.getClazzName());
        }
        queryWrapper.orderByDesc("id");
        Page<AdminStudent> pages = this.baseMapper.selectPage(page, queryWrapper);
        return pages;
    }
}
