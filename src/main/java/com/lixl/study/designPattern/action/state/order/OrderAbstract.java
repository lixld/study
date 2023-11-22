package com.lixl.study.designPattern.action.state.order;

public abstract class OrderAbstract implements IOrder{
    @Override
    public abstract OrderState getName();

    @Override
    public void openOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void cancelOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void draftOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void paidOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void onHoldOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void unfullfilledOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void partiaFullfilledOrder(OrderStateMachine orderStateMachine) {

    }

    @Override
    public void fullfilledOrder(OrderStateMachine orderStateMachine) {

    }
}
