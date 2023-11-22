package com.lixl.study.designPattern.struct.proxy.staticProxy;

public interface IUserController {
    UserVo login(String telephone, String password);

    UserVo register(String telephone, String password);
}
