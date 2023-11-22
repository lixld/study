package com.lixl.study.springcloudGateWay.business;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserForm userForm) {

        return userService.login(userForm);
    }

    @GetMapping("/detail")
    public JSONObject detail(@RequestParam("id") Long id) {

        return userService.detail(id);
    }
}