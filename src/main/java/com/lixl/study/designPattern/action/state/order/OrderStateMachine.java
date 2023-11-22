package com.lixl.study.designPattern.action.state.order;

import com.lixl.study.designPattern.action.state.order.impl.OpenOrder;

public class OrderStateMachine {
    private int socre;
    private IOrder currentState;

    public OrderStateMachine() {
        this.socre = 0;
        this.currentState = OpenOrder.getInstance();
    }

    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }

    public IOrder getCurrentState() {
        return currentState;
    }

    public void setCurrentState(IOrder currentState) {
        this.currentState = currentState;
    }

    public void openOrder(){
        this.currentState.openOrder(this);
    }
    public void cancelOrder(){
        this.currentState.cancelOrder(this);
    }
    public void draftOrder(){
        this.currentState.draftOrder(this);
    }
    public void paidOrder(){
        this.currentState.paidOrder(this);
    }
    public void onHoldOrder(){
        this.currentState.onHoldOrder(this);
    }
    public void unfullfilledOrder(){
        this.currentState.unfullfilledOrder(this);
    }
    public void partiaFullfilledOrder(){
        this.currentState.partiaFullfilledOrder(this);
    }
    public void fullfilledOrder(){
        this.currentState.fullfilledOrder(this);}

    public static void main(String[] args) {
        OrderStateMachine orderStateMachine = new OrderStateMachine();
    }
}
