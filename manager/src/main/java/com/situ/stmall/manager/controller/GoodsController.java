package com.situ.stmall.manager.controller;


import com.situ.stmall.common.bean.Goods;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.groups.UpdateGroup;
import com.situ.stmall.common.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

//
@RestController
@RequestMapping("/GoodsController")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Value("${uploadDir}")
    private String uploadDir;

    //添加
    @PostMapping
    public RespBean add( @Validated @RequestBody Goods goods) throws Exception {
        goodsService.add(goods);
        return RespBean.ok("添加成功");

    }

    //删除
    @DeleteMapping("/{id}")
    public RespBean del(@NotNull(message = "删除时id不为空")@PathVariable("id") Integer id) throws Exception {
        goodsService.del(id);
        return  RespBean.ok("删除成功");
    }

    //修改
    @PutMapping
    public RespBean upd(@Validated({UpdateGroup.class, Default.class})@RequestBody Goods goods) throws Exception {
        goodsService.update(goods);
        return  RespBean.ok("修改成功");
    }

    //查询
    @GetMapping("/selectByCondition")
    public RespBean selectByCondition(Integer pageNum, Integer pageSize, Goods goods,
                                      BigDecimal minPrice,BigDecimal maxPrice){
        Object result;
        if(pageNum!=null){

            result = goodsService.selectByCondition(pageNum, pageSize, goods, minPrice , maxPrice);

        }else {
            result = goodsService.selectByCondition(goods);
        }
        return  RespBean.ok("查询成功", result);

    }

    //根据id查询
    @GetMapping("/one/{id}")
    public RespBean findByid(@PathVariable("id")Integer id){
        Goods goods = goodsService.findById(id);
        return  RespBean.ok("查询成功",goods);

    }

    @PostMapping("/upload")
    public RespBean upload( MultipartFile upload) throws IOException {
        String fileName =upload.getOriginalFilename();
        fileName= UUID.randomUUID().toString().replace("-", "")+"_"+fileName;
        upload.transferTo(new File(uploadDir + fileName));
        return RespBean.ok("上传成功",fileName);
    }


}
