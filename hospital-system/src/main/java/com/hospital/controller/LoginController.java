package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.dto.LoginRequest;
import com.hospital.entity.User;
import com.hospital.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;   // ✅ 正确的包名
/**
 * 1）@RestController 拆解（合并两个注解）
 * 等价组合：@Controller + @ResponseBody
 * @Controller：把当前类交给 Spring 容器管理，标记为接口控制器；
 * @ResponseBody：方法返回值不再跳转页面，直接变成 JSON 字符串发给前端。
 * 大白话：写了这个注解，前端 AJAX、Postman 发请求，
 * 拿到的就是 JSON 数据，不用返回 html 页面，前后端分离必备注解。
 * 2）@RequestMapping("/departments")
 * 类上统一接口前缀当前这个类里所有接口地址，
 * 都会自动拼接前缀：/departments例：后面@GetMapping完整地址 = http://ip:端口/departments
 */
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request.getUsername(), request.getPassword());
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    @GetMapping("/current-user")
    public Result getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("未登录");
        }
    }

    @PostMapping("/logout")
    public Result logout(HttpSession session) {
        session.invalidate();
        return Result.success("退出成功");
    }

    // 注册接口
    @PostMapping("/register")
    public Result register(@RequestBody LoginRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        // role 在 Mapper 中默认设为 'USER'，所以这里不用设置

        boolean success = userService.register(user);
        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error("用户名已存在");
        }
    }
}