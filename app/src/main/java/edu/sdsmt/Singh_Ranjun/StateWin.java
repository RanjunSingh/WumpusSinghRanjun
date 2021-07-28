package edu.sdsmt.Singh_Ranjun;

public class StateWin extends State{

    public StateWin( Game model, StateMachine machine, GameView gameView) {
        super( model, machine, gameView );
    }

    @Override
    public void exitActivity() {
    }

    @Override
    public void entryActivity() {
        //win dialog.
        System.out.println("Win!");

        MainActivity activity = this.machine.getMainActivity();

        //GRADING: DIALOG
        activity.winDialog(); //trigger the win dialog.
    }

    @Override
    public void doTask() {
    }

    @Override
    public void enteredArea() {

    }
}
