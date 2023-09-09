package com.situ.stmall.common.service;

import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Goods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoodsService {
    void add (Goods goods) throws Exception;
    //逻辑删除
    void del (Integer id) throws Exception;

    void update(Goods goods) throws Exception;

    PageInfo<Goods> selectByCondition(Integer pageNum, Integer pageSize, Goods goods, BigDecimal minPrice, BigDecimal maxPrice);
    List<Goods> selectByCondition(Goods goods);

    Goods findById(Integer id);
    Map<String,Integer> getGoodsDrawInfo();
    List<Map<String, Object>> getGoodsCountInfo();


}
