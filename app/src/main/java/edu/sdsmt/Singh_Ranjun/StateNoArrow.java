package edu.sdsmt.Singh_Ranjun;

/**
 * Player has a bow but no arrows state.
 */
public class StateNoArrow extends State{

    public StateNoArrow( Game model, StateMachine machine, GameView gameView ) {
        super( model, machine, gameView );
    }

    @Override
    public void exitActivity() {

    }

    @Override
    public void entryActivity() {
        //disable arrow buttons
        System.out.println("No Arrow State");

    }

    @Override
    public void doTask() {

    }

    @Override
    public void enteredArea() {
        //model checks the area in the controller and sets flags.
        if(model.getGameOver()){ //wumpus or pit
            machine.setState(StateMachine.StateEnum.Lose);
        }
        else if(model.arrowPresent()){
            machine.setState(StateMachine.StateEnum.Arrow);
            model.arrowPicked(); //picked up the arrow.
        }
        else if(model.getBatPresent()){
            machine.setState(StateMachine.StateEnum.NoBow);
        }
        else if(!model.getWumpus().isAlive() && model.getExitReached()){
            machine.setState(StateMachine.StateEnum.Win);
        }
    }
}
