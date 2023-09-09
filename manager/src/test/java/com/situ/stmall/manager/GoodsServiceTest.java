package com.situ.stmall.manager;


import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.bean.GoodsPic;
import com.situ.stmall.common.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
public class GoodsServiceTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    public void test1(){
        goodsService.selectByCondition(null).stream().forEach(System.out::println);
    }

    @Test
    public void  test2(){
        PageInfo<Goods> goodsPageInfo = goodsService.selectByCondition(1, 100, null, null, null);
        System.out.println(goodsPageInfo);
    }

    //添加测试
    @Test
    public void test3() throws Exception {
        ArrayList<GoodsPic> list = new ArrayList<>();
        list.add(new GoodsPic(null, "111.jpg",null));
        list.add(new GoodsPic(null, "222.jpg",null));
        list.add(new GoodsPic(null, "333.jpg",null));
        list.add(new GoodsPic(null, "444.jpg",null));
        Goods goods = new Goods(null, "华为mate40", "5G", new BigDecimal(6999), new BigDecimal(6666), "翡冷翠", "8+256", 800, "111", 1, 52, 1, null, list);
        goodsService.add(goods);
    }

    //修改测试
    @Test
    public void test4() throws Exception {
        Goods goods = new Goods();
        goods.setId(5);
        goods.setName("华为mate40 pro");
        goods.setColor("曜石黑");

        ArrayList<GoodsPic> list = new ArrayList<>();
        list.add(new GoodsPic(null, "111.jpg",null));
        list.add(new GoodsPic(null, "222.jpg",null));
        list.add(new GoodsPic(null, "333.jpg",null));
        list.add(new GoodsPic(null, "444.jpg",null));
        goods.setPics(list);
        goodsService.update(goods);

    }
    @Test
    public void test5(){
        Map<String, Integer> goodsDrawInfo = goodsService.getGoodsDrawInfo();
        System.out.println(goodsDrawInfo);
    }


}
