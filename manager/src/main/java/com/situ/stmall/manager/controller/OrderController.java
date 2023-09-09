package com.situ.stmall.manager.controller;


import com.situ.stmall.common.bean.Order;
import com.situ.stmall.common.bean.OrderDetail;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.service.OrderService;
import com.situ.stmall.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/OrderController")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    //@PostMapping
    public RespBean add(@RequestBody Order order) throws Exception {
        //orderService.add(order);
        return RespBean.ok("添加成功");
    }

    //删除
    @DeleteMapping("/{id}")
    public RespBean del(@PathVariable("id") String id) throws Exception {
        orderService.updStatus(id);
        return  RespBean.ok("删除成功");
    }

    //修改
    @PutMapping
    public RespBean upd(@RequestBody Order order, HttpSession session) throws Exception {
        Order order1 = orderService.selectById(order.getId());
        User user = userService.selectById(order1.getUserId());

        orderService.update(order,user);

        return  RespBean.ok("修改成功");
    }

    //查询
    @GetMapping("/selectAll")
    public RespBean selectAll(Integer pageNum, Integer pageSize){
        Object result;
        if(pageNum!=null){
            result = orderService.selectAll(pageNum, pageSize);
        }else {
            result = orderService.selectAll();
        }
        return  RespBean.ok("查询成功", result);
    }

    //根据id查询
    @GetMapping("/one/{id}")
    public RespBean findByid(@PathVariable("id")String id) throws Exception {
        Order order = orderService.selectById(id);
        return  RespBean.ok("查询成功",order);
    }
    @GetMapping ("/findOrderInfo/{oid}")
    public RespBean findOrderInfo(@PathVariable String oid){
        List<OrderDetail> byOid = orderService.findByOid( oid);
        return RespBean.ok("查询成功", byOid);
    }

    @GetMapping ("/findOrderDetail/{id}")
    public RespBean findOrderDetail(@PathVariable Integer id) throws Exception {
        OrderDetail orderDetail = orderService.findById(id);
        return RespBean.ok("查询成功", orderDetail);
    }


}
