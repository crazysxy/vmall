package com.situ.stmall.front.controller;

import com.situ.stmall.common.bean.Cart;
import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.service.CartService;
import com.situ.stmall.common.service.CategoryService;
import com.situ.stmall.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    
    @RequestMapping({"/index","/"})
    public String index(Model model , HttpSession session){
        List<Category> categoryList = categoryService.selectAllParentForRecom();
        model.addAttribute("categoryList",categoryList);
        Integer interestedCategoryId=null;


        User user = (User) session.getAttribute("user");
        if(user!=null){
            //获取购物车
            List<Cart> list = cartService.selectByUserId(user.getId());
            session.setAttribute("cartCount", list.size());
            //获取用户中感兴趣的类id
            interestedCategoryId=user.getInterested();
        }
        //如果用户中没有感兴趣的id 在session中获取
        if(interestedCategoryId==null){
            interestedCategoryId = (Integer) session.getAttribute("interestedCategory");
        }
        //如果都没有 则为第一次访问 默认 显示mate系列
        if(interestedCategoryId==null){
            interestedCategoryId = 7;
        }
        //根据二级分类查找感兴趣的分类的商品并返回
        if(interestedCategoryId!=null){
            List<Goods> goodsList = categoryService.findByInterestedCategoryId(interestedCategoryId);
            model.addAttribute("interestedGoodsList",goodsList);
        }



        return "index";
    }
}
