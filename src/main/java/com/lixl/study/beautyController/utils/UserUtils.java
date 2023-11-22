package com.lixl.study.beautyController.utils;

import com.lixl.study.beautyController.LoginUser;

import java.util.Optional;

public class UserUtils {

    public static LoginUser getUser() {
        try {
//            LoginUser loginUser = SecurityUtils.getLoginUser();
            LoginUser loginUser = new LoginUser();
            return loginUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserId() {
        String userId = Optional.ofNullable(getUser()).map(u -> u.getUserId()).orElse("fake_user_id");
        return userId;
    }
}
