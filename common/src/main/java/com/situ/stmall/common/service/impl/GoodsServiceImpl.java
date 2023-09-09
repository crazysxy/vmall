package com.situ.stmall.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.bean.GoodsPic;
import com.situ.stmall.common.mapper.GoodsMapper;
import com.situ.stmall.common.mapper.GoodsPicMapper;
import com.situ.stmall.common.mapper.OrderDetailMapper;
import com.situ.stmall.common.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsPicMapper goodsPicMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Transactional
    @Override
    public void add(Goods goods) throws Exception {
        Goods goods0 = new Goods();
        goods0.setCategoryId(goods.getCategoryId());
        //获取该父分类下的所有商品的信息
        List<Goods> list = goodsMapper.selectByCondition(goods0, null, null);
        //判断是否有相同商品
        boolean b = list.stream().anyMatch(item -> item.getName().equals(goods.getName()) &&
                item.getColor().equals(goods.getColor()) &&
                item.getVersion().equals(goods.getVersion())
        );
        if(b){
            throw new Exception("该商品已存在，添加失败");
        }

        List<GoodsPic> pics = goods.getPics();

        //设置图片内的goodsId
        goodsMapper.insert(goods);
        pics.stream().forEach(item->item.setGoodsId(goods.getId()));
        goodsPicMapper.insert(pics);

    }

    @Transactional
    @Override
    public void del(Integer id) throws Exception {
        Goods findById = goodsMapper.findById(id);
        if (findById==null){
            throw new Exception("不存在该商品");
        }
        //orderDetailMapper.
        if(orderDetailMapper.selectByGoodsId(id).size()>0){
        throw new Exception("订单中有该商品，无法删除");
        }


        goodsPicMapper.delete(id);

        goodsMapper.delete(id);
    }

    @Transactional
    @Override
    public void update(Goods goods) throws Exception {
        Goods allInfo = goodsMapper.findById(goods.getId());
        Integer newCategortId;
        if(goods.getCategoryId()==null){
            newCategortId = allInfo.getCategoryId();
        }else {
            newCategortId=goods.getCategoryId();
        }

        String newName;
        if(goods.getName()==null){
            newName = allInfo.getName();
        }else {
            newName=goods.getName();
        }

        String newColor;
        if(goods.getColor()==null){
            newColor = allInfo.getColor();
        }else {
            newColor=goods.getColor();
        }

        String newVersion;
        if(goods.getVersion()==null){
            newVersion = allInfo.getVersion();
        }else {
            newVersion=goods.getVersion();
        }

        Goods g = new Goods();
        g.setCategoryId(newCategortId);
        List<Goods> goodsList = goodsMapper.selectByCondition(g, null, null);
        boolean b = goodsList.stream().anyMatch(item -> item.getName().equals(newName) &&
                item.getColor().equals(newColor) &&
                item.getVersion().equals(newVersion)&&
                item.getId()!=goods.getId());
        if(b){
            throw  new Exception("分类下商品重复,修改失败");
        }


        List<GoodsPic> pics = goods.getPics();
        if(pics!=null&&pics.size()>0){
            goodsPicMapper.delete(goods.getId());
            pics.stream().forEach(item->item.setGoodsId(goods.getId()));
            goodsPicMapper.insert(pics);

        }

        goodsMapper.update(goods);
    }



    @Override
    public PageInfo<Goods> selectByCondition(Integer pageNum, Integer pageSize, Goods goods, BigDecimal minPrice, BigDecimal maxPrice) {
        PageHelper.startPage(pageNum,pageSize);
        List<Goods> goodsList = goodsMapper.selectByCondition(goods, minPrice, maxPrice);
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goodsList);
        return goodsPageInfo;
    }

    @Cacheable(value = "goodsList",key="'goodsList'")
    @Override
    public List<Goods> selectByCondition(Goods goods) {
        return goodsMapper.selectByCondition(goods, null, null);
    }

    @Cacheable(value = "goods",key="#id")
    @Override
    public Goods findById(Integer id) {
        return goodsMapper.findById(id);
    }

    @Override
    public Map<String, Integer> getGoodsDrawInfo() {
        HashMap<String, Integer> getGoodsDrawInfo = new HashMap<>();
        List<Goods> goods = goodsMapper.selectByCondition(null, null, null);
        long NotListedCount = goods.stream().filter(item -> item.getStatus() == 0).count();
        long listedCount = goods.stream().filter(item -> item.getStatus() == 1).count();
        long allCount = goods.stream().count();

        getGoodsDrawInfo.put("全部商品", (int) allCount);
        getGoodsDrawInfo.put("未上架", (int) NotListedCount);
        getGoodsDrawInfo.put("已上架", (int) listedCount);
        return getGoodsDrawInfo;
    }

    @Override
    public List<Map<String, Object>> getGoodsCountInfo() {

        return null;
    }


}
