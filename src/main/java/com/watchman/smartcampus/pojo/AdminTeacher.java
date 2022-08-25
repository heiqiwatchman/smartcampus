package com.watchman.smartcampus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hongfengw
 * @create 2022-08-07 11:58
 * @Description: 教师信息
 * @Version 1.0
 */
@Data
@TableName("tb_teacher")
public class AdminTeacher {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String tno;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String clazzName;
    private String portraitPath;//存储头像的项目路径

//    @TableLogic
//    private Integer isDeleted;
}
