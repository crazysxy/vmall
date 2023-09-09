package com.situ.stmall.manager;


import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.bean.GoodsPic;
import com.situ.stmall.common.mapper.GoodsMapper;
import com.situ.stmall.common.mapper.GoodsPicMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GoodMapperTest {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsPicMapper goodsPicMapper;

    @Test
    public void test11(){
        ArrayList<GoodsPic> list = new ArrayList<>();
        list.add(new GoodsPic(null, "aaa.jpg", 1));
        list.add(new GoodsPic(null, "bbb.jpg", 2));
        list.add(new GoodsPic(null, "ccc.jpg", 2));

        System.out.println(goodsPicMapper.insert(list));
    }
    @Test
    public void test12(){
        System.out.println(goodsPicMapper.delete(2));
    }

    @Test
    public void test1(){
        System.out.println(goodsMapper.findById(3));
    }

    @Test
    public void test2(){
        goodsMapper.selectByCondition(null, new BigDecimal(0), new BigDecimal(1000)).stream().forEach(System.out::println);
    }

    @Test
    public void test3(){
        System.out.println(goodsMapper.findById(3));
    }

    @Test
    public void test4(){
        Goods goods = new Goods();
        goods.setName("华为");
        goods.setDscp("4");
        goodsMapper.selectByCondition(goods,null, null).stream().forEach(System.out::println);
    }


    @Test
    public void test5(){
        List<Goods> goods = goodsMapper.selectByCondition(null, null, null);
        long count = goods.stream().filter(item -> item.getStatus() == 0).count();
        System.out.println(count);
    }
}
