package edu.sdsmt.Singh_Ranjun;

/**
 * Parent class for the states.
 */
public abstract class State{
    protected Game model;
    protected StateMachine machine;
    protected GameView gameView;

    public State( Game model, StateMachine machine, GameView gameView){
        this.model = model;
        this.machine = machine;
        this.gameView = gameView;
    }

    public abstract void exitActivity();
    public abstract void entryActivity();
    public abstract void doTask();
    public abstract void enteredArea();

}
