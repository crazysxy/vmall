package com.situ.stmall.common.service;

import java.util.List;
import java.util.Map;

public interface DrawService {
    List<Map<String, Object>> getGoodsCount();
    List<Map<String, Object>> getOrderCount();
    List<Integer> getGoodsDrawInfo();


}
