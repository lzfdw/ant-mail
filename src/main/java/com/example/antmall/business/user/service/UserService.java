package com.example.antmall.business.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.antmall.business.user.bo.UserLoginBO;
import com.example.antmall.business.user.bo.UserRegisterBO;
import com.example.antmall.business.user.entity.User;
import com.example.antmall.business.user.vo.UserLoginResponseVO;

import java.util.List;

public interface UserService extends IService<User> {
    void register(UserRegisterBO userRegisterBO);

    void adminregister(UserRegisterBO userRegisterBO);

    UserLoginResponseVO login(UserLoginBO userLoginBO);

    void delete(List<Long> idList);



}
