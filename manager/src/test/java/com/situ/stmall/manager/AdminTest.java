package com.situ.stmall.manager;


import com.situ.stmall.common.mapper.AdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void test1(){
        System.out.println(adminMapper.selectById(1));
    }
    @Test
    public void test2(){
        System.out.println(adminMapper.selectByUsername("tom"));
    }
}
