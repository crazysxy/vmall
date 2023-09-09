package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.GoodsPic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsPicMapper {
    int insert(@Param("pics") List<GoodsPic> pics);

    int delete(Integer goodsId);

    List<GoodsPic> select(Integer goodsId);




}
