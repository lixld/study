package com.lixl.study.abstractAndInterface.interfaceDemo;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static List<Filter> filters = new ArrayList<>();

    static {
        filters.add(new AuthencationFilter());
        filters.add(new RateLimitFilter());
    }

    public void handleRpcRequest(RpcRequest req) {
        try {
            for (Filter filter : filters) {
                filter.doFilter(req);
            }
        } catch (RpcException e) {
            // ...处理过滤结果...
        }
        // ...省略其他处理逻辑...
    }
}
