package com.lixl.study.designPattern.create.factory.DI.MyDI;

public class RedisCounter {
        private String ipAddress;
        private int port;

        public RedisCounter(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
        }
    }
