package com.situ.stmall.manager;


import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManagerApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;

    //@Test
    //public void test1(){
    //    Category category = new Category(null, "华为电脑", "华为电脑", "aaa.jpg", 2, 1, 1, null,null);
    //    System.out.println(categoryMapper.insert(category));
    //
    //}
    @Test
    public void test2(){
        categoryMapper.delete(25);
    }

    @Test
    public void test3(){
        Category category = new Category();
        category.setId(26);
        category.setDscp("鸿蒙4.0");
        System.out.println(categoryMapper.update(category));
    }

    @Test
    public void test4(){
        System.out.println(categoryMapper.findById(6));
    }

    @Test
    public void test5(){
        Category category = new Category();
        category.setName("电脑");
        categoryMapper.selectByCondition(category).stream().forEach(System.out::println);
    }

    @Test
    public void test6(){

        System.out.println(categoryMapper.findName(1));
    }
}
