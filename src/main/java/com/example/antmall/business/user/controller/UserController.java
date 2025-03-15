package com.example.antmall.business.user.controller;

import com.example.antmall.business.user.bo.UserEditBO;
import com.example.antmall.business.user.bo.UserLoginBO;
import com.example.antmall.business.user.bo.UserRegisterBO;
import com.example.antmall.business.user.service.UserService;
import com.example.antmall.business.user.vo.UserLoginResponseVO;
import com.example.antmall.common.entity.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "用户管理")
@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){this.userService = userService;}

    /**
     * 用户注册接口
     *
     * @param userRegisterBO 用户注册请求参数（已通过 @Valid 进行校验）
     * @return 注册成功或错误信息
     */
    @ApiOperation(value = "用户注册接口", notes = "用户注册，输入用户名、密码、确认密码、邮箱、电话等信息，完成注册")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterBO userRegisterBO) {
        // 校验密码和确认密码是否一致
        if (!userRegisterBO.getPassword().equals(userRegisterBO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("密码和确认密码不一致");
        }

        // 调用 Service 层处理注册逻辑（包括用户名是否存在、密码加密、保存用户信息等）
        userService.register(userRegisterBO);

        // 返回注册成功的信息
        return ResponseEntity.ok("用户注册成功");
    }

    @ApiOperation(value = "用户登录接口", notes = "用户登录，输入用户名、密码")
    @PostMapping("/login")
    public ResultWrapper<UserLoginResponseVO> login(@Valid @RequestBody UserLoginBO login) {
        // 调用 Service 层处理登录逻辑，返回封装好的登录结果（例如：token、username 等）
        UserLoginResponseVO loginResponse = userService.login(login);

        // 如果登录失败，可以选择抛出异常或者返回错误结果（这里假设 service 层已处理登录失败逻辑）
        return ResultWrapper.success(loginResponse)
                .message("登录成功");
    }


    @ApiOperation(value = "管理员注册接口", notes = "用户注册，输入用户名、密码、确认密码、邮箱、电话等信息，完成注册")
    @PostMapping("/adminregister")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody UserRegisterBO userRegisterBO) {
        // 校验密码和确认密码是否一致
        if (!userRegisterBO.getPassword().equals(userRegisterBO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("密码和确认密码不一致");
        }

        // 调用 Service 层处理注册逻辑（包括用户名是否存在、密码加密、保存用户信息等）
        userService.adminregister(userRegisterBO);

        // 返回注册成功的信息
        return ResponseEntity.ok("用户注册成功");
    }

    @ApiOperation(value = "用户删除接口", notes = "删除用户")
    @PostMapping("/delete")
    public void deleteUser(@NotEmpty @RequestBody List<Long> idList){
        userService.delete(idList);
    }

}
