package com.example.antmall.business.user.bo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditBO {
    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户名：不能为空，长度在3到50之间
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度应在3到50之间")
    private String username;

    /**
     * 电子邮箱：不能为空且必须符合邮箱格式
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 电话号码：不能为空
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
