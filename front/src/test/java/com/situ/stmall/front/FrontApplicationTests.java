package com.situ.stmall.front;

import com.situ.stmall.common.bean.OrderDetail;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.mapper.CategoryMapper;
import com.situ.stmall.common.mapper.OrderDetailMapper;
import com.situ.stmall.common.mapper.UserMapper;
import com.situ.stmall.common.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
class FrontApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test1(){
        User user = userMapper.selectByUserame("tom");
        System.out.println(user.getPassword());
    }
    @Test
    public void test2(){
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail aa1 = new OrderDetail(null, 10, new BigDecimal("1222"), 1, "aa1", null, null, null);
        OrderDetail aa2 = new OrderDetail(null, 11, new BigDecimal("1222"), 1, "aa1", null, null, null);
        orderDetails.add(aa1);
        orderDetails.add(aa2);

        int insert = orderDetailMapper.insert(orderDetails);
        System.out.println(insert);
    }

    @Test
    public void Test3(){
        categoryService.selectAllParent().stream().forEach(System.out::println);
    }

    //@Test
    //public void Tes4(){
    //    System.out.println(categoryService.selectByParentId(1));
    //}
    @Test
    public void Tes5(){
       categoryMapper.selectByParentId(1).stream().forEach(System.out::println);
    }

    @Test
    public void Test5(){
        categoryMapper.selectAllParentForRecom().stream().forEach(System.out::println);
    }

    @Test
    public void test6(){
        User user = new User();
        user.setId(1);
        user.setMoney(new BigDecimal(8888));
        System.out.println(userMapper.update(user));
    }
}
