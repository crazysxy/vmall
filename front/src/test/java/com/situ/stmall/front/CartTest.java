package com.situ.stmall.front;

import com.situ.stmall.common.bean.Cart;
import com.situ.stmall.common.mapper.CartMapper;
import com.situ.stmall.common.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartService cartService;
    @Test
    public void test1(){
        cartMapper.selectByUserId(1).stream().forEach(System.out::println);
    }

    @Test
    public void test2(){
        System.out.println(cartMapper.selectById(1));
    }

    @Test
    public void test3() throws Exception {
        Cart cart = new Cart(null, 1, 3, 1, null);
        System.out.println(cartService.insert(cart));
    }

    @Test
    public void test4() throws Exception {
        Cart cart = new Cart(2, 10, 3, 1, null);
        cartService.update(cart, 1);

    }

    @Test
    public void test5() throws Exception {
        //Cart cart = new Cart(2, 10, 3, 1, null);
        cartService.delete(1, 1);

    }

    @Test
    public void test6(){
        cartService.selectByUserId(1).stream().forEach(System.out::println);
    }




}
