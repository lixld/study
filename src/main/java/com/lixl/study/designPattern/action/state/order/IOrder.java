package com.lixl.study.designPattern.action.state.order;



public interface IOrder {
    public abstract OrderState getName();
    public void openOrder(OrderStateMachine orderStateMachine);
    public void cancelOrder(OrderStateMachine orderStateMachine);
    public void draftOrder(OrderStateMachine orderStateMachine);
    public void paidOrder(OrderStateMachine orderStateMachine);
    public void onHoldOrder(OrderStateMachine orderStateMachine);
    public void unfullfilledOrder(OrderStateMachine orderStateMachine);
    public void partiaFullfilledOrder(OrderStateMachine orderStateMachine);
    public void fullfilledOrder(OrderStateMachine orderStateMachine);
}
