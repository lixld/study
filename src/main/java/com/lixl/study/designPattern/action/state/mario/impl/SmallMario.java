package com.lixl.study.designPattern.action.state.mario.impl;

import com.lixl.study.designPattern.action.state.mario.IMario;
import com.lixl.study.designPattern.action.state.mario.MarioStateMachine;
import com.lixl.study.designPattern.action.state.mario.State;

public class SmallMario implements IMario {
    @Override
    public State getName() {
        return State.SMALL;
    }

    private SmallMario() {
    }

    public static SmallMario smallMario = new SmallMario();

    public static SmallMario getInstance() {
        return smallMario;
    }

    @Override
    public void obtianMashRoom(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(SuperMario.getInstance());
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 100);
    }

    @Override
    public void obtianCape(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(CapeMario.getInstance());
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 200);
    }

    @Override
    public void obtianFireFlower(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(FireMario.getInstance());
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 300);
    }

    @Override
    public void metMonster(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(smallMario);
        marioStateMachine.setSocre(marioStateMachine.getSocre() - 300);
    }
}
