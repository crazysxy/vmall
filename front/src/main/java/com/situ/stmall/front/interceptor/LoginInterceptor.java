package com.situ.stmall.front.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ////获取session
        HttpSession session = request.getSession();
        ////获取登录用户信息
        User user = (User) session.getAttribute("user");
        //
        if (user == null) {
            String header = request.getHeader("X-Requested-With");
            if (header != null && header.equals("XMLHttpRequest")) {
                ObjectMapper mapper = new ObjectMapper();
                RespBean respBean = RespBean.error("请重新登录");
                mapper.writeValue(response.getOutputStream(), respBean);
            } else {
                //重定向到登录页
                response.sendRedirect(request.getContextPath() + "/user/login");
            }
            return false;
        }

        return true;
    }

}
