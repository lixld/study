package com.lixl.study.designPattern.action.state.mario;

import com.lixl.study.designPattern.action.state.mario.impl.SmallMario;
import lombok.Data;

@Data
public class MarioStateMachine {
    private int socre;
    private IMario currentState;

    public MarioStateMachine() {
        this.socre = 0;
        this.currentState = SmallMario.getInstance();
    }


    public void obtianMashRoom() {
        currentState.obtianMashRoom(this);
    }

    public void obtianCape() {
        currentState.obtianCape(this);
    }

    public void obtianFireFlower() {
        currentState.obtianFireFlower(this);
    }

    public void metMonster() {
        currentState.metMonster(this);
    }

    public static void main(String[] args) {
        MarioStateMachine marioStateMachine = new MarioStateMachine();
        marioStateMachine.obtianMashRoom();

        int socre = marioStateMachine.getSocre();
        IMario currentState = marioStateMachine.getCurrentState();
        State name = currentState.getName();

        System.out.println("mario吃蘑菇后：状态：" + name + "积分：" + socre);
    }

}
