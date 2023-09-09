package com.situ.stmall.manager;

import com.situ.stmall.common.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddrTest {
    @Autowired
    private OrderMapper orderMapper;
    @Test
    public void test1(){
        System.out.println(orderMapper.selectByAddrId(2).size());
    }
}

