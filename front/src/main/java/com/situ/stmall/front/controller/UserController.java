package com.situ.stmall.front.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/reg")
    public String reg(){
        return "user/reg";
    }

    @GetMapping("/userInfo")
    public String userInfo(){
        return "user/userInfo";
    }
    @GetMapping("/updatePassword")
    public String updatePassword(){
        return "user/updatePassword";
    }

    @GetMapping("/updatePayPassword")
    public String updatePayPassword(){
        return "user/updatePayPassword";
    }



    @GetMapping("/updateInfo")
    public String updateInfo(){
        return "user/update_userInfo";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index";
    }




    @ResponseBody
    @GetMapping("/getUserInfo")
    public RespBean getUserInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        return RespBean.ok(user);

    }

    @PostMapping("/login")
    public String login(String codeInput,String username, String password, HttpSession session, Model model){
        String code = (String) session.getAttribute("code");
        if(!codeInput.equalsIgnoreCase(code)){
            model.addAttribute("errMsg", "验证码输入错误");
            return "user/login";
      }
        try {
            User user = userService.login(username,password);

            session.setAttribute("user", user);
            return "redirect:/index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/login";
        }
    }
    @PostMapping("/reg")
    public String reg(String codeInput,HttpSession session, String username, String password, String repassword, Model model){
        String code = (String) session.getAttribute("code");
        if(!codeInput.equalsIgnoreCase(code)){
            model.addAttribute("errMsg", "验证码输入错误");
            return "user/login";
        }
        try {
            userService.insert(username, password,repassword);
            return "redirect:user/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/reg";
        }
    }

    //修改密码
    @PostMapping("/updatePassword")
    public String updatePassword(HttpSession session ,  String password,  String newPassword,  String reNewPassword, Model model){
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();

        if(!newPassword.equals(reNewPassword)){
            model.addAttribute("updErrMsg", "两次新密码输入不同！");
            //未修改成功则返回修改页面
            return "user/updatePassword";
        }

        try {
            userService.updatePassword(username, password, newPassword);
            //修改后重定向到登录页
            session.invalidate();
            return "user/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("updErrMsg", e.getMessage());
            //未修改成功则返回修改页面
            return "user/updatePassword";
        }
    }


    //修改支付密码
    @PostMapping("/updatePayPassword")
    public String updatePayPassword(HttpSession session ,  String payPassword,  String newPayPassword,  String reNewPayPassword, Model model){
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();

        if(!newPayPassword.equals(reNewPayPassword)){
            model.addAttribute("updErrMsg", "两次新密码输入不同！");
            //未修改成功则返回修改页面
            return "user/updatePayPassword";
        }

        try {
            userService.updatePayPassword(username, payPassword, newPayPassword);
            //修改后重定向到登录页
            User user1 = userService.selectById(user.getId());
            session.setAttribute("user",user1);
            return "user/userInfo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("updErrMsg", e.getMessage());
            //未修改成功则返回修改页面
            return "user/updatePayPassword";
        }
    }




    @ResponseBody
    @PostMapping("/updateUserInfo")
    public RespBean updateUserInfo( User user,Model model,HttpSession session){

        try {
            userService.updateInfo(user);
            user=userService.selectById(user.getId());
            session.setAttribute("user", user);

            return RespBean.ok();
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("updInfoMsg", e.getMessage());

            return RespBean.error(e.getMessage());
        }


    }


    @GetMapping("/code")
    public void code(HttpSession session, HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 38, 4, 20);
        String code = lineCaptcha.getCode();
        session.setAttribute("code", code);
        lineCaptcha.write(response.getOutputStream());

    }






}
