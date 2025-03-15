package com.example.antmall.business.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum UserRole {
    USER("USER"),  // 普通用户
    ADMIN("ADMIN"); // 管理员

    @EnumValue
    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    // 根据字符串获取枚举值
    public static UserRole fromRoleName(String roleName) {
        for (UserRole role : UserRole.values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("未知的角色: " + roleName);
    }
}
