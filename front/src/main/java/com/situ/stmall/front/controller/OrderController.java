package com.situ.stmall.front.controller;

import com.situ.stmall.common.bean.*;
import com.situ.stmall.common.service.AddrService;
import com.situ.stmall.common.service.CartService;
import com.situ.stmall.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddrService addrService;
    @Autowired
    private CartService cartService;


    @PostMapping("/confirm")
    public String confirm(Integer[] cartId, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        try {
            System.out.println("123123123");
            List<Addr> addrs = addrService.selectByUserId(user.getId());
            List<Cart> carts = cartService.selectByIds(cartId, user.getId());
            model.addAttribute("addrList",addrs);
            model.addAttribute("cartList",carts);
            return "user/orderConfirm";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/error";
        }
    }

    @GetMapping("/add")
    public String add(Integer[] cartId,Integer addressId,HttpSession session, Model model){
        User user = (User) session.getAttribute("user");

        Order order = new Order();
        order.setUserId(user.getId());
        order.setAddrId(addressId);

        try {
            orderService.insert(order, cartId);
            return "redirect:/user/order/pay?orderId=" + order.getId();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/error";
        }

    }

    @GetMapping("/pay")
    public String pay(String orderId,Model model){
        try {
            Order order = orderService.selectById(orderId);
            model.addAttribute("order", order);
            return "user/pay";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/error";
        }
    }
    @GetMapping("/cancel")
    public String cancel(String orderId,Model model){

        //orderService
        int cancel = orderService.cancel(orderId);

        return "redirect:/user/order/toList";

    }
    @GetMapping("/updateStatus")
    public String updateStatus(String orderId,Integer status,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");

        //orderService
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        try {
            orderService.update(order,user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/user/order/toList";

    }



    @PostMapping("/pay")
    public String pay( String orderId,String paypwd,HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        try {
            orderService.pay(orderId, user.getId(), paypwd);
            return "redirect:/user/order/toList";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "user/error";

        }


    }

    @GetMapping("/toList")
    public String toOrderList(){
        return "user/orderList";
    }
    @GetMapping("/orderListForStatus0")
    public String orderListForStatus0(){
        return "user/orderListForStatus0";
    }
    @GetMapping("/orderListForStatus12")
    public String orderListForStatus12(){
        return "user/orderListForStatus12";
    }

    @ResponseBody
    @GetMapping("/list")
    public RespBean list(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.selectByUserId(user.getId());
        return RespBean.ok(orders);
    }



    @ResponseBody
    @GetMapping("/arrearage")
    public RespBean arrearage(HttpSession session,@RequestParam(value = "status[]") Integer[] status){
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.selectByUserIdAndStatus(user.getId(),status);
        orders.stream().forEach(System.out::println);
        return RespBean.ok(orders);
    }


}
