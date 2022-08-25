package com.watchman.smartcampus.pojo;

import lombok.Data;

/**
 * @author hongfengw
 * @create 2022-08-07 11:48
 * @Description: 用户登录表单信息
 * @Version 1.0
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;


}
