package com.lixl.study.designPattern.struct.proxy.dynamicProxy;

public class MetricsCollector {
    public void recordRequest(RequestInfo requestInfo) {
        System.out.println(requestInfo.toString());
    }
}
