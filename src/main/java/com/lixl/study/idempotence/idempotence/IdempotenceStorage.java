package com.lixl.study.idempotence.idempotence;

public interface IdempotenceStorage {
    boolean saveIfAbsent(String idempotenceId);

    void delete(String idempotenceId);
}
