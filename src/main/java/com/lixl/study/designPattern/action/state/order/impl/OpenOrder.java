package com.lixl.study.designPattern.action.state.order.impl;


import com.lixl.study.designPattern.action.state.order.OrderState;
import com.lixl.study.designPattern.action.state.order.OrderStateMachine;
import com.lixl.study.designPattern.action.state.order.OrderAbstract;

public class OpenOrder extends OrderAbstract {


    private OpenOrder() {

    }

    static OpenOrder openOrder = new OpenOrder();

    public static OpenOrder getInstance() {
        return openOrder;
    }

    @Override
    public OrderState getName() {
        return OrderState.OPEN;
    }

    @Override
    public void openOrder(OrderStateMachine orderStateMachine) {

    }

}
