package com.situ.stmall.manager.interceptor;


import com.situ.stmall.common.bean.Admin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

public class AutoInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("aotoInterceptor");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String value = null;
            //遍历全部的Cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auto")) {
                    value = cookie.getValue();
                }
            }
            if (value != null) {
                value = URLDecoder.decode(value, "utf-8");

                //获取Cookie中的username password
                String[] split = value.split("=");

                String username = split[0];
                String password = split[1];
                //
                //System.out.println(username);
                //System.out.println(password);
                //
                //User user = userService.findUserByUsernameAndPassword(username, password);
                //System.out.println(user.toString());
                //System.out.println("user:"+user);
                if (value != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("admin", new Admin());
                    response.sendRedirect("/admin/index.html");
                }
            } else {
                return true;
            }

        } else {
            return true;

        }
        return true;
    }

}
