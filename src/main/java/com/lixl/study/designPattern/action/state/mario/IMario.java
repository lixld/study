package com.lixl.study.designPattern.action.state.mario;

public interface IMario {
    public State getName();
    public void obtianMashRoom(MarioStateMachine marioStateMachine);
    public void obtianCape(MarioStateMachine marioStateMachine);
    public void obtianFireFlower(MarioStateMachine marioStateMachine);
    public void metMonster(MarioStateMachine marioStateMachine);
}
