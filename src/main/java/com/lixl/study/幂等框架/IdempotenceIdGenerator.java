package com.lixl.study.幂等框架;

import java.util.UUID;

public class IdempotenceIdGenerator {
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}