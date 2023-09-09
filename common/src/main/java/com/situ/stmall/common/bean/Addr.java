package com.situ.stmall.common.bean;

import com.situ.stmall.common.groups.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Addr {
    @NotNull(message = "id不能为空",groups = UpdateGroup.class)
    private Integer id;//主键
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\s]{1,20}$", message = "联系人姓名")
    private String contact;//联系人
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789][0-9]{9}$", message = "非法电话")
    private String phone;//电话
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\s]{1,20}$", message = "非法省")
    private String province;//省
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\s]{1,20}$", message = "非法市")
    private String city;//市
    private String county;//县
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\s]{1,20}$", message = "非法街道")
    private String town;//镇/街道
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\s]{1,20}$", message = "非法详细地址")
    private String detail;//详细地址
    private Integer userId;//用户Id
    private Integer status;// 0：普通 1：默认
}
