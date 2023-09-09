package com.situ.stmall.common.bean;

import com.situ.stmall.common.groups.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods  implements Serializable {
    @NotNull(message = "id不能为空",groups = UpdateGroup.class)
    private Integer id;//主键 id
    @NotEmpty(message = "名称不能为空")
    private String name;//名称
    @NotEmpty(message = "描述不能为空")
    private String dscp;//描述
    @DecimalMin(value="0.00",message="价格最低为0")
    @DecimalMax(value="999999",message="价格最高为999999")
    private BigDecimal price;//价格
    @DecimalMin(value="0.00",message="价格最低为0")
    @DecimalMax(value="999999",message="价格最高为999999")
    private BigDecimal markPrice;//标价
    @NotEmpty(message = "颜色不能为空")
    private String color;//颜色
    @NotEmpty(message = "版本不能为空")
    private String version;//版本
    @Min(value = 0,message = "数量最小值为0")
    @Max(value = 10000,message = "数量最大值为10000")
    private Integer count;//数量
    @NotEmpty(message = "内容不能为空")
    private String content;//内容
    @Min(value = 0,message = "是否推荐最小值为0")
    @Max(value = 1,message = "是否推荐最大值为1")
    private Integer recom;//是否推荐 0:不推荐 1:推荐
    private Integer categoryId;//分类id
    @Min(value = 0,message = "是否上架最小值为0")
    @Max(value = 1,message = "是否上架最大值为1")
    private Integer status;//是否上架 0: 上架 1:下架
    private Category category;//类名
    private List<GoodsPic> pics;//图片

}
