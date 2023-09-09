package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.Goods;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsMapper {
    //添加
    int insert(Goods goods);
    //删除
    int delete(Integer id);
    //修改
    int update(Goods goods);
    //模糊查询所有
    List<Goods> selectByCondition(@Param("goods") Goods goods,@Param("minPrice") BigDecimal minPrice,@Param("maxPrice") BigDecimal maxPrice);
    //根据id查询
    Goods findById(Integer id);
    Goods findByName(String name);
    List<Goods> findByCategory(Integer categoryId);
    List<Goods> findByCategoryAndRecom(Integer categoryId);
}
