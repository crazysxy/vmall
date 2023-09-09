package com.situ.stmall.manager.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.situ.stmall.common.bean.Admin;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public RespBean login(Admin admin, HttpSession session, String codeInput) throws Exception {

        String code = (String) session.getAttribute("code");
        if(!code.equalsIgnoreCase(codeInput)){
            throw new Exception("验证码输入错误");
        }
        admin =adminService.login(admin.getUsername(), admin.getPassword());
        session.setAttribute("admin", admin);
        return  RespBean.ok("登录成功");

    }
    @GetMapping("/admin/logout")
    public RespBean logout(HttpSession session) throws Exception {
        session.invalidate();
        return RespBean.ok("退出成功");
    }

    @GetMapping("/admin/info")
    public RespBean Info(HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        return RespBean.ok(admin);
    }

    @GetMapping("/code")
    public void code(HttpSession session, HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 38, 4, 20);
        String code = lineCaptcha.getCode();
        session.setAttribute("code", code);
        lineCaptcha.write(response.getOutputStream());

    }

    @PutMapping("/updateInfo")
    public RespBean updateInfo(Admin admin,HttpSession session){
        Admin admin1 = (Admin) session.getAttribute("admin");
        try {
            admin.setId(admin1.getId());
            int i = adminService.updateInfo(admin);
            //session.invalidate();
            Admin admin2 = adminService.selectById(admin1.getId());
            session.setAttribute("admin", admin2);
            return RespBean.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(e.getMessage());
        }
    }

    @PutMapping("/updatePassword")
    public RespBean updatePassword(String password,String newPassword,String reNewPassword,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        if(!newPassword.equals(reNewPassword)){
            return RespBean.error("两次密码输入不一致！");
        }
        try {
            adminService.updatePassword(admin.getUsername(), password, newPassword);
            return RespBean.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(e.getMessage());
        }
    }
    @GetMapping("/getInfo")
    public RespBean getInfo(HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
       return RespBean.ok(admin);
    }


}
