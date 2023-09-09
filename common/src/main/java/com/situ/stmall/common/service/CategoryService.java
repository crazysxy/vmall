package com.situ.stmall.common.service;

import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.Goods;

import java.util.List;

public interface CategoryService {
    boolean insert(Category category) throws Exception;
    boolean delete(Integer id) throws Exception;
    boolean update(Category category) throws Exception;
    PageInfo<Category> selectByCondition(Integer pageNum,Integer pageSize, Category category);
    List<Category> selectByCondition( Category category);
    Category findById(Integer id);
    List<Category>selectAllParent();
    List<Category> selectByParentId( Integer id);

    List<Category> selectAllParentForRecom();
    List<Goods> findByInterestedCategoryId( Integer id);
}
