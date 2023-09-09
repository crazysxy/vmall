package com.situ.stmall.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.mapper.CategoryMapper;
import com.situ.stmall.common.mapper.GoodsMapper;
import com.situ.stmall.common.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Caching(
            put = @CachePut(value = "category", key = "#category.id"),
            evict = @CacheEvict(value = "categoryParentList", key = "'categoryParentList'")
    )
    @Override
    public boolean insert(Category category) throws Exception {

        List<Category> categoryList = categoryMapper.selectByCondition(null);

        boolean b = categoryList.stream()
                .filter(item -> item.getParentId() == category.getParentId())
                .anyMatch(item -> item.getName().equals(category.getName()));
        if (b){
            throw  new Exception("已存在该类别，请勿重复添加");
        }

        return categoryMapper.insert(category)==1?true:false;
    }
    @Caching(
            evict = @CacheEvict(value = "categoryParentList", key = "'categoryParentList'")
    )
    @Override
    public boolean delete(Integer id) throws Exception {
        Category category = categoryMapper.findById(id);
        if(category==null){
            throw new Exception("不存在该类别！！");
        }
        Category category0 = new Category();
        category0.setParentId(id);
        long count = categoryMapper.selectByCondition(category0).stream().count();
        if(count>0){
            throw  new Exception("该类别内有其他类！无法删除");
        }
        Goods goods = new Goods();
        goods.setCategoryId(id);
        if (goodsMapper.selectByCondition(goods, null, null).size()>0) {
            throw  new Exception("该分类下有商品，无法删除");
        }

        return categoryMapper.delete(id)==1?true:false;
    }
    @Caching(
            put = @CachePut(value = "category", key = "#category.id"),
            evict = @CacheEvict(value = "categoryParentList", key = "'categoryParentList'")
    )
    @Override
    public boolean update(Category category) throws Exception {

        //获取父类id
        Integer parentId;
        String newName;
        Category category1 = categoryMapper.findById(category.getId());
        //判断是否传入父类id
        if(category.getParentId()!=null){

            parentId=category.getParentId();

        }else{
            parentId = category1.getParentId();
        }
        if(parentId==category1.getId()){
            throw new Exception("不能选择自己作为父分类");

        }
        if(category.getParentId()==0&&goodsMapper.findByCategory(category.getId()).size()>0){
            throw new Exception("该分类下有商品，无法修改为父分类");
        }

        //判断是否传入新名字
        if(category.getName()!=null){
            newName=category.getName();
        }else {
            newName=category1.getName();
        }
        //如果该id是父分类，并且子分类不为空
        if(category1.getParentId()==0&&category.getParentId()!=0){
            Category category2 = new Category();
            category2.setParentId(category.getId());
            if (categoryMapper.selectByCondition(category2).size()>0) {

                throw  new Exception("该分类存在子分类，修改失败~");
            }

        }

        //获取所有类别
        List<Category> categoryList = categoryMapper.selectByCondition(null);
        boolean b = categoryList.stream()
                .filter(item -> item.getParentId() == parentId)//获取所有类别中和修改类的同类
                .anyMatch(item -> item.getName().equals(newName)&&item.getId()!=category.getId());//判断同类中是否重复并且id不同
        if (b){
            throw  new Exception("类别冲突，修改失败~");
        }
        return categoryMapper.update(category)==1?true:false;
    }


    @Override
    public PageInfo<Category> selectByCondition(Integer pageNum,Integer pageSize, Category category) {
        PageHelper.startPage(pageNum,pageSize);
        List<Category> categories = categoryMapper.selectByCondition(category);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categories);
        return categoryPageInfo;
    }

    @Override
    public List<Category> selectByCondition(Category category) {
        return categoryMapper.selectByCondition(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }


    @Override
    public List<Category> selectAllParent() {
        List<Category> categoryList = categoryMapper.selectAllParent();
        return categoryList;
    }

    @Override
    public List<Category> selectByParentId(Integer id) {
        return categoryMapper.selectByParentId(id);
    }

    @Cacheable(value = "categoryParentList",key="'categoryParentList'")
    @Override
    public List<Category> selectAllParentForRecom() {
        List<Category> categoryList = categoryMapper.selectAllParentForRecom();
        //遍历父分类
        for(Category category:categoryList){
            List<Goods> goodsList = category.getGoodsList();
            //  遍历子分类
            for(Category category1:category.getChildren()){
                //遍历子分类中的商品
                for(Goods goods:category1.getGoodsList()){
                    goodsList.add(goods);
                }
            }
            List<Goods> goodsList1=new ArrayList<>();
            goodsList.stream().limit(8).forEach(item->goodsList1.add(item));

            category.setGoodsList(goodsList1);
        }

        return categoryList;
    }

    @Override
    public List<Goods> findByInterestedCategoryId( Integer id) {
        Category byInterestedCategoryId = categoryMapper.findByInterestedCategoryId(id);
        List<Goods> goodsList = byInterestedCategoryId.getGoodsList();
        ArrayList<Goods> goodsArrayList = new ArrayList<>();
        Collections.shuffle(goodsList);
        goodsList.stream().limit(4).forEach(item->goodsArrayList.add(item));

        return goodsArrayList;
    }
}
