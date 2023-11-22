package com.lixl.study.designPattern.action.state.mario.impl;

import com.lixl.study.designPattern.action.state.mario.IMario;
import com.lixl.study.designPattern.action.state.mario.MarioStateMachine;
import com.lixl.study.designPattern.action.state.mario.State;

public class SuperMario implements IMario {
    @Override
    public State getName() {
        return State.SUPER;
    }

    private SuperMario() {
    }

    public static SuperMario superMario = new SuperMario();

    public static SuperMario getInstance() {
        return superMario;
    }

    @Override
    public void obtianMashRoom(MarioStateMachine marioStateMachine) {
//        marioStateMachine.setCurrentState(State.SUPER);
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 100);
    }

    @Override
    public void obtianCape(MarioStateMachine marioStateMachine) {
//        marioStateMachine.setCurrentState(State.CAPE);
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 200);
    }

    @Override
    public void obtianFireFlower(MarioStateMachine marioStateMachine) {
//        marioStateMachine.setCurrentState(State.FIRE);
        marioStateMachine.setSocre(marioStateMachine.getSocre() + 300);
    }

    @Override
    public void metMonster(MarioStateMachine marioStateMachine) {
        marioStateMachine.setCurrentState(SmallMario.getInstance());
        marioStateMachine.setSocre(marioStateMachine.getSocre() - 300);
    }
}
