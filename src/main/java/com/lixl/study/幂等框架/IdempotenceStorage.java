package com.lixl.study.幂等框架;

/**
 * 幂等的读写
 */
public interface IdempotenceStorage {
    boolean saveIfAbsent(String idempotenceId);

    void delete(String idempotenceId);
}