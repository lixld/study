package com.lixl.study.designPattern.action.state.mario;

public enum State {
    SMALL(0),
    SUPER(1),
    CAPE(2),
    FIRE(3);
    int value;
    private State(int value){
        this.value = value;
    }
}
