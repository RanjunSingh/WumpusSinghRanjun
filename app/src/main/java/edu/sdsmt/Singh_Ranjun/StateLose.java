package edu.sdsmt.Singh_Ranjun;

/**
 * Player has lost, state.
 */
public class StateLose extends State{

    public StateLose( Game model, StateMachine machine, GameView gameView ) {
        super( model, machine, gameView );
    }

    @Override
    public void exitActivity() {

    }

    @Override
    public void entryActivity() {

        System.out.println("Lose State");
        MainActivity activity = this.machine.getMainActivity();

        //GRADING: DIALOG
        activity.loseDialog(); //trigger the lose dialog.
    }

    @Override
    public void doTask() {

    }

    @Override
    public void enteredArea() {

    }


}
