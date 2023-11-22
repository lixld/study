package com.lixl.study.designPattern.struct.proxy.dynamicProxy;

public class RequestInfo {
    String apiName;
    long responseTime;
    long startTimestamp;

    public RequestInfo(String apiName, long responseTime, long startTimestamp) {
        this.apiName = apiName;
        this.responseTime = responseTime;
        this.startTimestamp = startTimestamp;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "apiName='" + apiName + '\'' +
                ", responseTime=" + responseTime +
                ", startTimestamp=" + startTimestamp +
                '}';
    }
}
