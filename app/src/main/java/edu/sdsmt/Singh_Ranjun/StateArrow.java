package edu.sdsmt.Singh_Ranjun;

/**
 * Player has an Arrow, state.
 */
public class StateArrow extends State{

    public StateArrow( Game model, StateMachine machine, GameView gameView ) {
        super( model, machine, gameView );
    }

    @Override
    public void exitActivity() {

        //GRADING: DISABLE
        gameView.disableArrows();
    }

    @Override
    public void entryActivity() {
        //enable buttons that allow player to use arrows.
        System.out.println("Arrow State");

        //GRADING: ENABLE
        gameView.enableArrows();
    }

    @Override
    public void doTask() {

        if(model.arrowsSpent() && model.getWumpus().isAlive()) {
            machine.setState(StateMachine.StateEnum.Lose);
        }
        //GRADING: EMPTY
        else if(model.getArrowCount() == 0){
            machine.setState(StateMachine.StateEnum.NoArrow);
        }
    }

    @Override
    public void enteredArea() {
        if(model.getGameOver()){ //wumpus or pit entered.
            machine.setState(StateMachine.StateEnum.Lose);
        }
        //GRADING: BAT
        else if(model.getBatPresent()){
            machine.setState(StateMachine.StateEnum.NoBow);
        }
        else if(!model.getWumpus().isAlive() && model.getExitReached()){
            machine.setState(StateMachine.StateEnum.Win);
        }

    }
}
