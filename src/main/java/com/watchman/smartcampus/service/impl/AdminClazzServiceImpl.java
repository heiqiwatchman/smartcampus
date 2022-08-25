package com.watchman.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.watchman.smartcampus.mapper.AdminClazzMapper;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminGrade;
import com.watchman.smartcampus.service.AdminClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author hongfengw
 * @create 2022-08-07 12:12
 * @Description:
 * @Version 1.0
 */
@Service("adminClazzServiceImpl")
@Transactional
public class AdminClazzServiceImpl extends ServiceImpl<AdminClazzMapper, AdminClazz> implements AdminClazzService {

    @Override
    public IPage<AdminClazz> getClazzByOpr(Page<AdminClazz> page, AdminClazz adminClazz) {
        QueryWrapper<AdminClazz> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(adminClazz.getGradeName())){
            queryWrapper.eq("grade_name",adminClazz.getGradeName());
        }
        if(!StringUtils.isEmpty(adminClazz.getName())){
            queryWrapper.eq("name",adminClazz.getName());
        }
        queryWrapper.orderByDesc("id");
        Page<AdminClazz> pages = this.baseMapper.selectPage(page, queryWrapper);
        return pages;
    }

    @Override
    public List<AdminClazz> getClazzs() {
        List<AdminClazz> list = this.list();
        return list;
    }
}
