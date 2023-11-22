package com.lixl.study.abstractAndInterface.interfaceDemo;

// 接口实现类：鉴权过滤器
public class AuthencationFilter implements Filter {
    @Override
    public void doFilter(RpcRequest req) throws RpcException {
        //...鉴权逻辑..
    }
}
