package com.situ.stmall.common.mapper;

import java.util.List;
import java.util.Map;

public interface DrawMapper {
    List<Map<String, Object>> getGoodsCount();
    List<Map<String, Object>> getGoodsStatus();
    List<Map<String, Object>> getOrderCount();


}
