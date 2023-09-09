package com.situ.stmall.manager;


import com.situ.stmall.common.mapper.DrawMapper;
import com.situ.stmall.common.service.DrawService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DrawTest {
    @Autowired
    private DrawMapper drawMapper;
    @Autowired
    private DrawService drawService;
    @Test
    public void Test1(){

        drawMapper.getGoodsCount().stream().forEach(System.out::println);
    }

    @Test
    public void Test2(){

        drawService.getGoodsDrawInfo().stream().forEach(System.out::println);
    }
}
