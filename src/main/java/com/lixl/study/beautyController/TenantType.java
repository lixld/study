package com.lixl.study.beautyController;

public enum TenantType {
    INTERNAL(0, "内部租户"),
    CUSTOMER(1, "客户租户"),
    TRAIL(2, "试用租户");

    private final int code;
    private final String desc;

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    private TenantType(final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }
}