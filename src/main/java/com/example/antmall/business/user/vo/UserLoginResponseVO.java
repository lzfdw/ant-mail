package com.example.antmall.business.user.vo;

import lombok.Data;

@Data
public class UserLoginResponseVO {
    /**
     * 登录成功后返回的 JWT Token
     */
    private String token;

    /**
     * 用户名或其他用户信息（可根据需要扩展）
     */
    private String username;
}
