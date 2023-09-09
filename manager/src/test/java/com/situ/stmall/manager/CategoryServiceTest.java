package com.situ.stmall.manager;


import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    //@Test
    //public void test1()  {
    //    Category category = new Category(null, "小米手机", "小米手机", "aaa.jpg", 0, 1, 1, null,null);
    //    try {
    //        System.out.println(categoryService.insert(category));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        System.out.println(e.getMessage());
    //    }
    //}

    @Test
    public void test2() throws Exception {
        categoryService.delete(66);
    }

    @Test
    public void test3() throws Exception {
        Category category = new Category();
        category.setId(36);
        //category.setName("华为电脑");
        //category.setDscp("鸿蒙4");
        category.setParentId(2);
        System.out.println(categoryService.update(category));
    }
    @Test
    public void test4() throws Exception {
        Category category = new Category();
        category.setId(35);
        category.setName("华为电脑2");
        category.setDscp("鸿蒙4");
        category.setParentId(2);
        System.out.println(categoryService.update(category));
    }

    @Test
    public void test5() {
        Category category = new Category();
        //category.setName("电脑");
        PageInfo<Category> categoryPageInfo = categoryService.selectByCondition(1, 10, category);
        System.out.println(categoryPageInfo);
    }
}
