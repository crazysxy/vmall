package com.situ.stmall.manager;


import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void test1(){
        userMapper.selectAll().stream().forEach(System.out::println);

    }

    @Test
    public void test2(){

        User user = new User();
        user.setId(2);
        user.setStatus(0);
        System.out.println(userMapper.update(user));
    }

}
