package com.watchman.smartcampus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.watchman.smartcampus.pojo.AdminStudent;
import com.watchman.smartcampus.pojo.AdminTeacher;
import com.watchman.smartcampus.pojo.AdminUser;
import com.watchman.smartcampus.pojo.LoginForm;
import com.watchman.smartcampus.service.AdminStudentService;
import com.watchman.smartcampus.service.AdminTeacherService;
import com.watchman.smartcampus.service.AdminUserService;
import com.watchman.smartcampus.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hongfengw
 * @create 2022-08-07 15:11
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/sms/system")
public class AdminSystemController {

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    AdminStudentService adminStudentService;
    @Autowired
    AdminTeacherService adminTeacherService;

    @ApiOperation("更新用户密码的处理器")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@ApiParam("token口令") @RequestHeader("token") String token,
                            @ApiParam("旧密码") @PathVariable("oldPwd") String oldPwd,
                            @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ){

        //判断token是否失效
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.fail().message("token失效，请重新登录");
        }

        //获取用户ID和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                QueryWrapper<AdminUser> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id",userId.intValue());
                queryWrapper1.eq("password",oldPwd);
                AdminUser adminUser = adminUserService.getOne(queryWrapper1);
                if(adminUser != null){
                    // 修改
                    adminUser.setPassword(newPwd);
                    adminUserService.saveOrUpdate(adminUser);
                }else {
                    return Result.fail().message("原密码有误！");
                }
                break;
            case 2:
                QueryWrapper<AdminStudent> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id",userId.intValue());
                queryWrapper2.eq("password",oldPwd);
                AdminStudent adminStudent = adminStudentService.getOne(queryWrapper2);
                if(adminStudent != null){
                    // 修改
                    adminStudent.setPassword(newPwd);
                    adminStudentService.saveOrUpdate(adminStudent);
                }else {
                    return Result.fail().message("原密码有误！");
                }
                break;
            case 3:
                QueryWrapper<AdminTeacher> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("id",userId.intValue());
                queryWrapper3.eq("password",oldPwd);
                AdminTeacher adminTeacher = adminTeacherService.getOne(queryWrapper3);
                if(adminTeacher != null){
                    // 修改
                    adminTeacher.setPassword(newPwd);
                    adminTeacherService.saveOrUpdate(adminTeacher);
                }else {
                    return Result.fail().message("原密码有误！");
                }
                break;

        }
        return Result.ok();
    }

    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile
            ,
            HttpServletRequest request
    ) {

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));

        // 保存文件 将文件发送到第三方/独立的图片服务器上,
        String portraitPath = "D:/workspace/smartcampus/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 响应图片的路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    @ApiOperation("通过token指令获取当前登录的用户信息")
    @GetMapping("/getInfo")
    public Result getInfoByToken(@ApiParam("token口令") @RequestHeader("token") String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析 用户id 和 用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //将数据放入到map里
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                AdminUser adminUser = adminUserService.getAdminUserById(userId);
                map.put("userType", userType);
                map.put("user", adminUser);
                break;
            case 2:
                AdminStudent adminStudent = adminStudentService.getAdminStudentById(userId);
                map.put("userType", userType);
                map.put("user", adminStudent);
                break;
            case 3:
                AdminTeacher adminTeacher = adminTeacherService.getAdminAdminTeacherById(userId);
                map.put("userType", userType);
                map.put("user", adminTeacher);
                break;
        }
        return Result.ok(map);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@ApiParam("登录提交的from表单") @RequestBody LoginForm loginForm, HttpServletRequest request) {
        //1:验证码校验（是否有效）
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
            return Result.fail().message("验证码有误，请检查后重试");
        }
        //2:从session现在域中移除现有验证码
        session.removeAttribute("verifiCode");
        //3:分用户类型进行校验
        //准备一个map用于存放响应的数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    AdminUser adminUser = adminUserService.login(loginForm);
                    if (null != adminUser) {
                        //用户的类型和用户id转换成一个密文,以toker的名称向客户端反馈
                        String token = JwtHelper.createToken(adminUser.getId().longValue(), 1);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码有误,请重新输入");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    AdminStudent adminStudent = adminStudentService.login(loginForm);
                    if (null != adminStudent) {
                        //用户的类型和用户id转换成一个密文,以toker的名称向客户端反馈
                        String token = JwtHelper.createToken(adminStudent.getId().longValue(), 2);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码有误,请重新输入");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    AdminTeacher adminTeacher = adminTeacherService.login(loginForm);
                    if (null != adminTeacher) {
                        //用户的类型和用户id转换成一个密文,以toker的名称向客户端反馈
                        String token = JwtHelper.createToken(adminTeacher.getId().longValue(), 3);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码有误,请重新输入");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }

        }
        return Result.fail().message("查无此用户");

    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     */
    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //1:获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //2:获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //3:将验证码文本放入session域，为下一步验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        //4:将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
