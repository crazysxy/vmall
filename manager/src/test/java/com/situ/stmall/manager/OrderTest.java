package com.situ.stmall.manager;


import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Order;
import com.situ.stmall.common.bean.OrderDetail;
import com.situ.stmall.common.mapper.OrderDetailMapper;
import com.situ.stmall.common.mapper.OrderMapper;
import com.situ.stmall.common.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderService orderService;
    @Test
    public void test1(){
        orderMapper.selectAll().stream().forEach(System.out::println);
    }

    @Test
    public void test2(){
        orderDetailMapper.selectByOid("aaa111").stream().forEach(System.out::println);
    }

    @Test
    public void test3(){
        PageInfo<Order> orderPageInfo = orderService.selectAll(1, 10);
        System.out.println(orderPageInfo);

    }
    @Test
    public void test4(){
        List<OrderDetail> byOid = orderService.findByOid("aaa111");
        System.out.println(byOid);

    }

    @Test
    public void test5() throws Exception {
        OrderDetail byId = orderService.findById(2);
        System.out.println(byId);

    }

    @Test
    public void test6(){
        List<Order> orders = orderMapper.selectAll();
        long count = orders.stream().filter(item -> item.getStatus() == 1).count();
        System.out.println(count);
    }

    //@Test
    //public void test7() {
    //        Map<String, Integer> orderDrawInfo = orderService.getOrderDrawInfo();
    //        System.out.println(orderDrawInfo);
    //     }




    }
