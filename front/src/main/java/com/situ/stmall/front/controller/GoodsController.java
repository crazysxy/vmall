package com.situ.stmall.front.controller;

import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.service.CategoryService;
import com.situ.stmall.common.service.GoodsService;
import com.situ.stmall.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
public class GoodsController {
    @Value("${uploadDir}")
    private String uploadDir;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ModelAndView search(ModelAndView mv, String name, Integer categoryId){
        Goods goods = new Goods();
        goods.setCategoryId(categoryId);
        goods.setName(name);
        goods.setStatus(0);
        List<Goods> goodsList = goodsService.selectByCondition(goods);
        mv.addObject("goodsList",goodsList);
        mv.setViewName("search");
        return mv;
    }


    @GetMapping("/getGoodsInfo/{id}")
    public ModelAndView getGoodsInfo(ModelAndView mv, @PathVariable("id")Integer id, HttpSession session){
        Goods goods = goodsService.findById(id);
        //将商品的分类id存到session中 便于在首页显示
        session.setAttribute("interestedCategory", goods.getCategoryId());

        User user = (User) session.getAttribute("user");
        if(user!=null){
            //将感兴趣的分类id放入user中
            user.setInterested(goods.getCategoryId());
            try {
                userService.updateInfo(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Goods> browsingList = (ArrayList<Goods>) session.getAttribute("browsingList");

        if (browsingList==null){
            browsingList = new ArrayList<>();
        }

        ArrayList<Goods> browsing=new ArrayList<>();
        Collections.reverse(browsingList);
        browsingList.add(goods);
        Collections.reverse(browsingList);

        browsingList.stream().limit(6).forEach(item->browsing.add(item));
        session.setAttribute("browsingList",browsing);


        mv.addObject("goods",goods);
        mv.setViewName("goods");
        return mv;
    }

    //根据1级分类id查找商品
    @GetMapping("/searchByCategory")
    public ModelAndView  searchByCategory(ModelAndView mv, Integer categoryId,HttpSession session){
        List<Category> categoryList = categoryService.selectByParentId(categoryId);
        Category category = categoryService.findById(categoryId);
        mv.addObject("categoryName", category.getName());
        mv.addObject("categoryList",categoryList);
        session.setAttribute("categoryNameForSearch",category);
        session.setAttribute("categoryListForSearch",categoryList);
        mv.setViewName("search");
        return mv;
    }


    @PostMapping("/upload")
    public RespBean upload(MultipartFile upload) throws IOException {
        String fileName =upload.getOriginalFilename();
        fileName= UUID.randomUUID().toString().replace("-", "")+"_"+fileName;
        upload.transferTo(new File(uploadDir + fileName));
        return RespBean.ok("上传成功",fileName);
    }



}
