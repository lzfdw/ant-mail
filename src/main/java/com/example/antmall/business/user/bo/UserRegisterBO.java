package com.example.antmall.business.user.bo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegisterBO {
    /**
     * 用户名：不能为空，长度在3到50之间
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度应在3到50之间")
    private String username;

    /**
     * 密码：不能为空，长度在6到255之间
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 255, message = "密码长度应在6到255之间")
    private String password;

    /**
     * 确认密码：用于校验用户两次输入的密码是否一致，不能为空
     */
    @NotBlank(message = "确认密码不能为空")
    @Size(min = 6, max = 255, message = "确认密码长度应在6到255之间")
    private String confirmPassword;

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
