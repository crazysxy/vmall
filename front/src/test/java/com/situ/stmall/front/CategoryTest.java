package com.situ.stmall.front;

import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.mapper.CategoryMapper;
import com.situ.stmall.common.mapper.GoodsMapper;
import com.situ.stmall.common.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class CategoryTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void test1(){
        goodsMapper.findByCategoryAndRecom(7).stream().forEach(System.out::println);

    }
    @Test
    public void test2(){
        categoryMapper.selectAllParentForRecom().stream().forEach(System.out::println);
    }



    @Test
    public void test(){
        Category byInterestedCategoryId = categoryMapper.findByInterestedCategoryId(7);
        //System.out.println(byInterestedCategoryId);
        System.out.println(byInterestedCategoryId.getGoodsList().size());
    }
    @Test
    public void test5(){
        Category byInterestedCategoryId = categoryMapper.findByInterestedCategoryId(7);
        List<Goods> goodsList = byInterestedCategoryId.getGoodsList();
        ArrayList<Goods> goods = new ArrayList<>();
        goodsList.stream().limit(4).forEach(item->goods.add(item));
        goods.stream().forEach(System.out::println);
    }
    @Test
    public void test6(){
        List<Category> categoryList = categoryMapper.selectAllParentForRecom();
        ArrayList<Goods> goodsArrayList = new ArrayList<>();

        //categoryList.stream().map(item->item.getChildren()).;
        //categoryList.stream().map(item->item.getGoodsList()).limit(2).forEach(item->goodsArrayList.add(item));
    }


    @Test
    public void test7(){
        ArrayList<Goods> goodsArrayList = new ArrayList<>();
        List<Goods> byCategory = goodsMapper.findByCategory(7);


        Collections.reverse(byCategory);
        byCategory.stream().forEach(System.out::println);
    }

    @Test
    public void test8(){
        List<Category> categoryList = categoryMapper.selectAllParentForRecom();
        List<Category> list = new ArrayList<>();
        //遍历父分类
        for(Category category:categoryList){
            List<Goods> goodsList = category.getGoodsList();
          //  遍历子分类
          for(Category category1:category.getChildren()){
              //遍历子分类中的商品
              for(Goods goods:category1.getGoodsList()){
                  goodsList.add(goods);
              }
          }

          category.setGoodsList(goodsList);
          category.setChildren(null);
        }



    }
}
