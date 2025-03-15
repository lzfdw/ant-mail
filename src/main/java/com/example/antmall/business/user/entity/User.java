package com.example.antmall.business.user.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.antmall.business.user.enums.UserRole;
import com.example.antmall.common.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends CommonEntity{
    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField(value = "role", fill = FieldFill.INSERT)
    @EnumValue  // 标记枚举值的存储方式
    private UserRole role = UserRole.USER;  // 默认角色为普通用户

}