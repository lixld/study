package com.lixl.study.designPattern.action.state.mario.impl;

import com.lixl.study.designPattern.action.state.mario.IMario;
import com.lixl.study.designPattern.action.state.mario.MarioStateMachine;
import com.lixl.study.designPattern.action.state.mario.State;

public class CapeMario implements IMario {
    @Override
    public State getName() {
        return State.CAPE;
    }

    private CapeMario() {
    }

    public static CapeMario capeMario = new CapeMario();

    public static CapeMario getInstance() {
        return capeMario;
    }

    @Override
    public void obtianMashRoom(MarioStateMachine marioStateMachine) {
//        marioStateMachine.setCurrentState(State.SUPER);
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 100);
    }

    @Override
    public void obtianCape(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(capeMario);
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 200);
    }

    @Override
    public void obtianFireFlower(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(FireMario.getInstance());
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 300);
    }

    @Override
    public void metMonster(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(SmallMario.getInstance());
        marioStateMachine.setSocre(marioStateMachine.getSocre() - 300);
    }
}
