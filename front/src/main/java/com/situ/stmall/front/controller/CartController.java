package com.situ.stmall.front.controller;

import com.situ.stmall.common.bean.Cart;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String toCart(){
        return "user/cart";
    }

    @GetMapping("/add")
    public String add(Integer goodsId, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");

        Cart cart = new Cart();
        cart.setGoodsId(goodsId);
        cart.setUserId(user.getId());

        try {
            cart.setCount(1);
            cartService.insert(cart);
            return "user/cart";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/error";
        }

    }

    @ResponseBody
    @GetMapping("/update")
    public RespBean update (Cart cart, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        try {
            cartService.update(cart, user.getId());
            return RespBean.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(e.getMessage());
        }

    }


    @ResponseBody
    @GetMapping("/del")
    public RespBean del(@NotNull(message = "删除的id不能为空") Integer id , HttpSession session){
        User user = (User) session.getAttribute("user");
        try {

            cartService.delete(id, user.getId());

            return RespBean.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(e.getMessage());
        }

    }

    @ResponseBody
    @GetMapping("/allCart")
    public RespBean selectAllCart(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Cart> list = cartService.selectByUserId(user.getId());
        session.setAttribute("cartCount", list.size());
        return  RespBean.ok(list);

    }




}
