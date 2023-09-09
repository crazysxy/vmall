package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.Category;

import java.util.List;

public interface CategoryMapper {
    //添加
    int insert(Category category);
    //删除
    int delete(Integer id);
    //修改
    int update(Category category);
    //模糊查询所有

    List<Category> selectByCondition(Category category);
    //根据id查询
    Category findById(Integer id);
    Category findByName(String name);
    String findName(Integer id);
    List<Category> selectAllParent();
    List<Category> selectAllParentForRecom();
    Category selectByParentId();
    List<Category> selectByParentId(Integer id);
    List<Category> selectByParentIdRecom(Integer id);


    Category findByInterestedCategoryId(Integer id);


}
