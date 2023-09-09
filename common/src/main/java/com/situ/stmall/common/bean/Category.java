package com.situ.stmall.common.bean;

import com.situ.stmall.common.groups.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @NotNull(message = "id不能为空",groups = UpdateGroup.class)
    private Integer id;//主键
    @NotEmpty(message = "分类名称不能为空")
    private String name;//名称
    @NotEmpty(message = "描述不能为空")
    private String dscp;//描述
    @NotEmpty(message = "图片地址不能为空")
    private String pic;//图片
    @NotNull(message = "父分类不能为空")
    private Integer parentId;//父分类ID

    @NotNull(message = "是否推荐不能为空")
    @Min(value = 0,message = "是否推荐最小值为0")
    @Max(value = 1,message = "是否推荐最大值为1")
    private Integer recom;//是否推荐 0:不推荐 1：推荐

    @NotNull(message = "是否上架不能为空")
    @Min(value = 0,message = "是否上架最小值为0")
    @Max(value = 1,message = "是否上架最大值为1")
    private Integer status;//是否上架 0:上架 1:下架
    private Category parent;//父类
    private List<Category> children;
    private List<Goods> goodsList;

}
