package com.watchman.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.watchman.smartcampus.mapper.AdminClazzMapper;
import com.watchman.smartcampus.mapper.AdminGradeMapper;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminGrade;
import com.watchman.smartcampus.service.AdminClazzService;
import com.watchman.smartcampus.service.AdminGradeService;
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
@Service("adminGradeServiceImpl")
@Transactional
public class AdminGradeServiceImpl extends ServiceImpl<AdminGradeMapper, AdminGrade> implements AdminGradeService {

    @Override
    public IPage<AdminGrade> getGradeByOpr(Page<AdminGrade> adminGradePage, String gradeName) {
        QueryWrapper<AdminGrade> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByDesc("id");
        Page<AdminGrade> page = this.baseMapper.selectPage(adminGradePage, queryWrapper);
        return page;
    }

    @Override
    public List<AdminGrade> getGrades() {
        List<AdminGrade> list = this.list();
        return list;
    }
}
