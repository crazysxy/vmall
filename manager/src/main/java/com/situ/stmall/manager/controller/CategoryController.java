package com.situ.stmall.manager.controller;


import com.situ.stmall.common.bean.Category;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.groups.UpdateGroup;
import com.situ.stmall.common.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/CategoryController")
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Value("${uploadDir}")
    private String uploadDir;

    @PostMapping
    public RespBean insert(@Validated @RequestBody Category category) throws Exception {
        categoryService.insert(category);
        return RespBean.ok("添加成功");
    }

    @DeleteMapping("/{id}")
    public RespBean delete(@NotNull(message = "删除时id不为空") @PathVariable("id")Integer id) throws Exception {
        categoryService.delete(id);
        return RespBean.ok("删除成功");
    }

    @PutMapping
    public RespBean update(@Validated({UpdateGroup.class, Default.class})@RequestBody Category category) throws Exception {
        categoryService.update(category);
        return RespBean.ok("修改成功");
    }

    @GetMapping("/selectByCondition")
    public RespBean selectByCondition(Integer pageNum,Integer pageSize,  Category category){
        Object result;
        if(pageNum!=null){
            result = categoryService.selectByCondition(pageNum, pageSize, category);
        }else {
            result= categoryService.selectByCondition(category);
        }

        return RespBean.ok("查询成功",result);
    }
    @GetMapping("/one/{id}")
    public RespBean findByid(@PathVariable("id")Integer id){
        Category category = categoryService.findById(id);
        return  RespBean.ok("查询成功",category);

    }

    @PostMapping("/upload")
    public RespBean upload( MultipartFile upload) throws IOException {
        String fileName =upload.getOriginalFilename();
        fileName= UUID.randomUUID().toString().replace("-", "")+"_"+fileName;
        upload.transferTo(new File(uploadDir + fileName));
        return RespBean.ok("上传成功",fileName);
    }

}
