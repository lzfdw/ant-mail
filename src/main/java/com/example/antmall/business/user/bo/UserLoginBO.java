package com.example.antmall.business.user.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginBO {
    /**
     * 用户名，用于唯一标识登录用户
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码，用于用户登录认证
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
