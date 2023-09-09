package com.situ.stmall.common.service.impl;

import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.mapper.DrawMapper;
import com.situ.stmall.common.mapper.GoodsMapper;
import com.situ.stmall.common.service.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DrawServiceImpl implements DrawService {
    @Autowired
    private DrawMapper drawMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<Map<String, Object>> getGoodsCount() {
        return drawMapper.getGoodsCount();
    }

    @Override
    public List<Map<String, Object>> getOrderCount() {
        return drawMapper.getOrderCount();
    }

    @Override
    public  List<Integer> getGoodsDrawInfo() {
        ArrayList< Integer> list = new ArrayList<>();

        //HashMap<String, Integer> allCountMap = new HashMap<>();
        //HashMap<String, Integer> NotListedCountMap = new HashMap<>();
        //HashMap<String, Integer> listedCountMap = new HashMap<>();
        List<Goods> goods = goodsMapper.selectByCondition(null, null, null);
        long NotListedCount = goods.stream().filter(item -> item.getStatus() == 0).count();
        long listedCount = goods.stream().filter(item -> item.getStatus() == 1).count();
        long allCount = goods.stream().count();

        //allCountMap.put("allCount", (int) allCount);
        //NotListedCountMap.put("NotListedCount", (int) NotListedCount);
        //listedCountMap.put("listedCount", (int) listedCount);
        list.add((int) allCount);
        list.add((int) NotListedCount);
        list.add((int) listedCount);
        return list;
    }
}
