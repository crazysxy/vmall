package com.situ.stmall.common.service.impl;

import com.situ.stmall.common.bean.Cart;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.mapper.CartMapper;
import com.situ.stmall.common.mapper.GoodsMapper;
import com.situ.stmall.common.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public boolean insert( Cart cart) throws Exception {
        Goods goods = goodsMapper.findById(cart.getGoodsId());
        if(cart.getCount()<0||cart.getCount()>9999){
            throw new Exception("非法的数量");
        }
        if(goods==null){
            throw  new Exception("商品不存在，请确认后再添加~~");
        }
        if (goods.getStatus()==1){
            throw new Exception("该商品已下架");
        }

        //查询该商品是否在该用户的购物车内
        Cart c0 = cartMapper.selectByUserIdAndGoodsId(cart.getGoodsId(), cart.getUserId());
        if(c0 == null){
            //不存在购物车中 --添加
            if(goods.getCount()==0){
                throw  new Exception("商品库存不足，暂时无法添加");
            }
            return cartMapper.insert(cart)==1?true:false;
        }else {
            //购物车存在该商品
            //判断库存
            if(goods.getCount() < c0.getCount() + 1){
                throw  new Exception("商品库存不足，暂时无法添加");
            }

            c0.setCount(c0.getCount()+1);
            return cartMapper.update(c0)==1?true:false;

        }

    }

    @Override
    public boolean delete(Integer id, Integer userId) throws Exception {
        Cart cart = cartMapper.selectById(id);
        if(cart==null){
            throw new Exception("购物车不存在，无法删除~~");
        }
        if(cart.getUserId()!=userId){
            throw new Exception("非法的购物车删除");
        }

        return cartMapper.delete(id)==1?true:false;
    }

    @Override
    public boolean update(Cart cart, Integer userId) throws Exception {
        //查询到该购物车
        Cart c0 = cartMapper.selectById(cart.getId());
        if(c0==null){
            throw new Exception("该购物车不存在，无法修改~~");
        }
        if(c0.getUserId()!=userId){
            throw new Exception("非法的购物车修改");
        }
        if(c0.getGoods()==null || c0.getGoods().getStatus()==1){
            throw new Exception("商品不存在或已下架，请联系管理员");
        }

        if(c0.getGoods().getCount()<cart.getCount()){
            throw new Exception("商品库存不足，修改失败~~");
        }

        return cartMapper.update(cart)==1?true:false;
    }

    @Override
    public List<Cart> selectByUserId(Integer userId) {
        return cartMapper.selectByUserId(userId);
    }

    @Override
    public List<Cart> selectByIds(Integer[] ids, Integer userId) throws Exception {

        List<Cart> carts = cartMapper.selectByIds(ids);
        for(Cart cart:carts){
            if(cart.getUserId()!=userId){
                throw new Exception("购物车和当前用户不匹配");
            }

        }
        return carts;
    }
}
