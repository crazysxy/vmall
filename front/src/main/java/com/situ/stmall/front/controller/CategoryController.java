package com.situ.stmall.front.controller;

import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/selectAllParent")
    public RespBean selectAllParent(){
        List<Category> categories = categoryService.selectAllParentForRecom();
        return RespBean.ok(categories);
    }






}
