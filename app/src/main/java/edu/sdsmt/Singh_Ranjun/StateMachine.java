package edu.sdsmt.Singh_Ranjun;

/**
 * Core functionality of the state machine.
 */
public class StateMachine {
    public enum StateEnum {NoBow, NoArrow, Arrow, Win, Lose}

    private StateEnum state = StateEnum.NoBow;
    private State[] stateArray = null;
    private GameView gameView;
    private MainActivity mainActivity;

    /**
     * core part of the state machine
     *
     * @param state
     */
    public void setState( StateEnum state ) {
        /*
         * Exit activities
         */
        stateArray[this.state.ordinal()].exitActivity( );

        /*
         * New state
         */
        this.state = state;

        //forward on the state to anything nested state that needs it
        //I could have make a second state machine entirely for the diagram

        /*
         * Entry activites
         */
        stateArray[this.state.ordinal()].entryActivity( );
    }


    public StateMachine( Game model, GameView gameView, MainActivity mainActivity ) {

        stateArray = new State[]{
                //need to give the states gameView so they can edit the arrow buttons.
                new StateNoBow(model, this, gameView),
                new StateNoArrow(model, this, gameView),
                new StateArrow(model, this, gameView),
                new StateWin(model, this, gameView),
                new StateLose(model, this, gameView)
        };
        this.gameView = gameView;
        this.mainActivity = mainActivity;
    }


    /**
     * A button press cause a state change
     */
//    void buttonPressed() {
//        //forward it on for the state to decide
//        stateArray[state.ordinal()].buttonPressed( );
//        doMaintenanceTask(0);
//    }


    /**
     * \brief refresh if the model has been updated
     */
    public void doMaintenanceTask( ) {
        stateArray[state.ordinal()].doTask();
    }

    public void enteredArea(){
        stateArray[state.ordinal()].enteredArea();
    }

    public StateEnum GetState() {
        return state;
    }

    public void mapToEnum(int ordinal){
        this.state = StateEnum.values()[ordinal];
//        setState(StateEnum.values()[ordinal]);
    }

    public MainActivity getMainActivity(){
        return mainActivity;
    }




//--------------------------------------------------------------------------------------------------
    //this animation code could be in another class. In this case, the state machine start all
    // the maintenance updates so it make sense to make it a nested class

    //this code should, in theory, work in both Java 11 and Android
    //Android preferred version is TimeAnimator or Android's Handler  (NOT Java's Handler)

/*
    private boolean ANIMATE_FAST_AS_POSSIBLE = false; //as fast as possible, or standard 30fps
    private Timer loop;
    private long lastFrame = 0;
    private AnimationTimer updateTimer = new AnimationTimer();


    public void StopAnimation() {
        loop.cancel();
        lastFrame = 0;
    }

    public void StartAnimation() {
        loop = new Timer();
        updateTimer = new AnimationTimer();
        if(ANIMATE_FAST_AS_POSSIBLE)
            loop.schedule( updateTimer , 0 ); // as fast as possible
        else
            loop.scheduleAtFixedRate( updateTimer , 0, 30 ); //~30 frames per second
    }

    public class AnimationTimer extends TimerTask {

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            if( lastFrame == 0 ) {
                lastFrame = now;
            } else {
                double delta = ( now - lastFrame ) / 1000.0;
                lastFrame = now;
                doMaintenanceTask( delta ); //update state maintenance
            }

            if(ANIMATE_FAST_AS_POSSIBLE) {
                loop = new Timer();
                updateTimer = new AnimationTimer();
                loop.schedule( updateTimer, 0 ); // as fast as possible
            }
        }
    }*/
//--------------------------------------------------------------------------------------------------



        //setup a timer to update the state
//        loop = new Timer();
//        StartAnimation();





    /**
     * A button press cause a state change
     */
//    void buttonPressed() {
//        //forward it on for the state to decide
//        stateArray[state.ordinal()].buttonPressed( );
//        doMaintenanceTask(0);
//    }


    /**
     * \brief refresh if the model has been updated
     */
//    public void doMaintenanceTask( double delta ) {
//        stateArray[state.ordinal()].doTask( delta );
//    }



}
