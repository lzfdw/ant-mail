package com.example.antmall.business.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.antmall.business.user.bo.UserLoginBO;
import com.example.antmall.business.user.bo.UserRegisterBO;
import com.example.antmall.business.user.entity.User;
import com.example.antmall.business.user.enums.UserRole;
import com.example.antmall.business.user.mapper.UserMapper;
import com.example.antmall.business.user.service.UserService;
import com.example.antmall.business.user.vo.UserLoginResponseVO;
import com.example.antmall.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public void register(UserRegisterBO registerBO) {
        // 检查用户名是否已存在
        if (lambdaQuery().eq(User::getUsername, registerBO.getUsername()).exists()) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建用户对象并加密密码
        User user = new User();
        user.setUsername(registerBO.getUsername());
        user.setPassword(passwordEncoder.encode(registerBO.getPassword()));
        user.setPhone(registerBO.getPhone());
        user.setEmail(registerBO.getEmail());

        // 默认角色为 USER
        user.setRole(UserRole.USER);

        // 保存到数据库
        save(user);
    }

    @Override
    public void adminregister(UserRegisterBO userRegisterBO) {
        // 检查用户名是否已存在
        if (lambdaQuery().eq(User::getUsername, userRegisterBO.getUsername()).exists()) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建用户对象并加密密码
        User user = new User();
        user.setUsername(userRegisterBO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterBO.getPassword()));
        user.setPhone(userRegisterBO.getPhone());
        user.setEmail(userRegisterBO.getEmail());

        // 默认角色为 USER
        user.setRole(UserRole.ADMIN);

        // 保存到数据库
        save(user);
    }

    @Override
    public UserLoginResponseVO login(UserLoginBO userLoginBO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userLoginBO.getUsername());
        User queryuser = userMapper.selectOne(queryWrapper);

        if(queryuser == null){
            throw new RuntimeException("用户不存在");
        }
        if(!passwordEncoder.matches(userLoginBO.getPassword(),queryuser.getPassword())){
            throw new RuntimeException("密码错误");
        }
        String token = jwtUtil.generateToken(queryuser.getUsername(), queryuser.getRole());

        UserLoginResponseVO userLoginResponseVO = new UserLoginResponseVO();
        userLoginResponseVO.setToken(token);
        userLoginResponseVO.setUsername(queryuser.getUsername());
        return userLoginResponseVO;
    }

    @Override
    public void delete(List<Long> idList) {
        removeBatchByIds(idList);
    }
}
