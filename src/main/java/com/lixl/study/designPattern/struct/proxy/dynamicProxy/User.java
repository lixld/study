package com.lixl.study.designPattern.struct.proxy.dynamicProxy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
    private String password;
}
