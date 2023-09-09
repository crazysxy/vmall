package com.situ.stmall.manager.controller;


import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.service.DrawService;
import com.situ.stmall.common.service.GoodsService;
import com.situ.stmall.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DrawController")
public class DrawController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DrawService drawService;

    @GetMapping("/goodsInfo")
    public RespBean findGoodsInfo(){
        List< Integer> goodsDrawInfo = drawService.getGoodsDrawInfo();
        return  RespBean.ok("查询成功", goodsDrawInfo);

    }


    @GetMapping("/orderInfo")
    public RespBean findOrderInfo(){
       List<Integer> orderDrawInfo = orderService.getOrderDrawInfo();
        return  RespBean.ok("查询成功", orderDrawInfo);

    }

    @GetMapping("/goodsCountInfo")
    public RespBean goodsCountInfo(){
        List<Map<String, Object>> goodsCount = drawService.getGoodsCount();
        return RespBean.ok(goodsCount);
    }
    @GetMapping("/getOrderGoodsCount")
    public RespBean getOrderGoodsCount(){
        List<Map<String, Object>> orderCount = drawService.getOrderCount();
        return  RespBean.ok(orderCount);
    }





}
