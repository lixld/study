package com.lixl.study.designPattern.struct.proxy.rpc.server;

public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
