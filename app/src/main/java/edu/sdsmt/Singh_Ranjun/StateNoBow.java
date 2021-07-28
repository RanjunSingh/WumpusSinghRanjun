package edu.sdsmt.Singh_Ranjun;

/**
 * Player does not have a bow state.
 */
public class StateNoBow extends State{

    public StateNoBow( Game model, StateMachine machine, GameView gameView ) {
        super( model, machine, gameView );
    }

    @Override
    public void exitActivity() {
    }

    @Override
    public void entryActivity() {

    }

    @Override
    public void doTask() {

    }

    @Override
    public void enteredArea() {
        //model checks the area in the controller and sets flags.
        if(model.getGameOver()){ //wumpus or pit entered.
            machine.setState(StateMachine.StateEnum.Lose);
        }
        //GRADING: BOW
        else if(model.getBowPresent()){
            machine.setState(StateMachine.StateEnum.NoArrow);
        }
        //GRADING: EXIT
        else if(!model.getWumpus().isAlive() && model.getExitReached()){
            machine.setState(StateMachine.StateEnum.Win);
        }

    }
}
