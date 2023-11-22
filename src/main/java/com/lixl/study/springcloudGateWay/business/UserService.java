package com.lixl.study.springcloudGateWay.business;

import com.alibaba.fastjson.JSONObject;
import com.lixl.study.springcloudGateWay.util.TokenUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private static final String USERNAME = "admin";

    private static final String PASSWORD = "123456";

    private static final Long USER_ID = 1L;


    /**
     * 模拟 登录 username = admin, password =123456,user_id 1L 登录成功 返回token
     */
    public String login(UserForm userForm) {

        String username = userForm.getUsername();
        String password = userForm.getPassword();

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            JSONObject userInfo = new JSONObject();
            userInfo.put("username", USERNAME);
            userInfo.put("password", PASSWORD);
            userInfo.put("userId", USER_ID);
            return TokenUtils.createToken(userInfo.toJSONString());
        }

        return "账号密码不正确";
    }


    public JSONObject detail(Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", "admin");
        return jsonObject;
    }
}