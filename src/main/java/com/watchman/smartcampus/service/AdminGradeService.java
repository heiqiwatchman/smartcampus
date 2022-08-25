package com.watchman.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.watchman.smartcampus.pojo.AdminClazz;
import com.watchman.smartcampus.pojo.AdminGrade;

import java.util.List;

/**
 * @author hongfengw
 * @create 2022-08-07 12:11
 * @Description:
 * @Version 1.0
 */
public interface AdminGradeService extends IService<AdminGrade> {

    IPage<AdminGrade> getGradeByOpr(Page<AdminGrade> adminGradePage, String gradeName);

    List<AdminGrade> getGrades();
}
