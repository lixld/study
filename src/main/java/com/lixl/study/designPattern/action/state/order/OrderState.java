package com.lixl.study.designPattern.action.state.order;

public enum OrderState {
    OPEN(1),
    CANCEL(3),
    DRAFT(4),
    PAID(5),
    ONHold(6),
    UNFULFILLED(7),
    PARTIALFULFILLED(8),
    FULLFILLED(9);
    int value;
    private OrderState(int value){
        this.value = value;
    }
}
