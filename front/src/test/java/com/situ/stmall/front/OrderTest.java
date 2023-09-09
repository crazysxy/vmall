package com.situ.stmall.front;

import com.situ.stmall.common.mapper.AddrMapper;
import com.situ.stmall.common.mapper.OrderDetailMapper;
import com.situ.stmall.common.mapper.OrderMapper;
import com.situ.stmall.common.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddrMapper addrMapper;
    @Autowired
    private CartService cartService;


    @Test
    public void test1(){
        System.out.println(orderMapper.selectById("aa1"));
    }

    @Test
    public void test2(){
        System.out.println(orderMapper.selectByUserId(1));
    }
    @Test
    public void test3(){
        addrMapper.selectByUserId(1).stream().forEach(System.out::println);
    }

    @Test
    public void test4(){
        System.out.println(addrMapper.selectById(2));

    }


    @Test
    public void test5(){
        orderDetailMapper.selectByOrderId("aa1").stream().forEach(System.out::println);
    }

    @Test
    public void test6() throws Exception {
        Integer[] cartId = new Integer[]{4,5,6};
        cartService.selectByIds(cartId, 1).stream().forEach(System.out::println);
    }
    @Test
    public void test7 (){
        Integer [] status = new Integer[]{1,2};
        orderMapper.selectByUserIdAndStatus(1, status).forEach(System.out::println);
    }
}
