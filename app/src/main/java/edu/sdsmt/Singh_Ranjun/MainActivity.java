package edu.sdsmt.Singh_Ranjun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 All tiers Complete.

 3: extensions 	30
 Extension 1: 5b: 10 points, Hints: Upon entering a room, if a wumpus, bat or pit is adjacent the
 player is given a hint in the notification area.

 Extension 2: 5g: 20 points, Scared Wumpus : There is a 50% chance that upon entering a room with a
 wumpus that it flees and the player survives.

 Known bugs:
 1. floating button is overlaid over everything except the player.
 2. Some game objects may overlap, please reset and test again.
 */

/**
 * MainActivity acts as the controller for the project.
 */
public class MainActivity extends AppCompatActivity {
    private Game game; //model
    private GameView gameView;
    private StateMachine state; //StateMachine
    private ArrayList<NormalRoom> rooms;
    private boolean creation = true;

    private FloatingActionButton circlePlayer;
    private FloatingActionButton squarePlayer;
    private FloatingActionButton burst;
    private Boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rooms = new ArrayList<>();

        game = new Game();
        insertRooms();//insert all rooms in the rooms array.
    }

    @Override
    protected void onStart() {

        super.onStart();

        //need the view to be created first before the stateMachine.
        gameView = findViewById(R.id.gameView);

        circlePlayer = findViewById(R.id.circlePlayer);
        squarePlayer = findViewById(R.id.squarePlayer);
        burst = findViewById(R.id.burst);


        state = new StateMachine(game, gameView, this);
        state.setState( StateMachine.StateEnum.NoBow); //in the beginning no bow.

        updateUI();
    }

    //basics
    public void onCheat(View view) {
        game.cheat();

        updateUI();
    }


    /**
     * Functions for the movement, trigger game movement and stateMachine updates.
     * @param view
     */
    //region Movement
    public void onRight(View view) {

        //redraw the player in the view ( dunno how to do this rn).
        game.moveRight();
        state.enteredArea(); //process the new area.

        updateUI();
    }


    public void onDown(View view) {
        game.moveDown();
        state.enteredArea();

        updateUI();
    }

    public void onLeft(View view) {
        game.moveLeft();
        state.enteredArea();

        updateUI();
    }

    public void onUp(View view) {
        game.moveUp();
        state.enteredArea();

        updateUI();
    }
    //endregion

    /**
     * Arrow Button functions, trigger model and statemachine updates.
     * @param view
     */
    public void onUArrow(View view){
        game.arrowShot("up");
        state.doMaintenanceTask();

        updateUI();
    }

    public void onDArrow(View view){
        game.arrowShot("down");
        state.doMaintenanceTask();

        updateUI();
    }

    public void onLArrow(View view){
        game.arrowShot("left");
        state.doMaintenanceTask();

        updateUI();
    }

    public void onRArrow(View view){
        game.arrowShot("right");
        state.doMaintenanceTask();

        updateUI();
    }


    /**
     * Reset the game, views and statemachine.
     * @param view
     */
    public void onReset(View view){
        game.reset();
        state.setState(StateMachine.StateEnum.NoBow);
        ExitRoom exitRoom = findViewById(R.id.exitRoom);
        exitRoom.reset();
        gameView.reset();

        resetRooms();
        updateUI();
    }

    public int getRow(int pos){
        return pos % 4;
    }

    public int getCol(int pos){
        return pos / 4;
    }

    /**
     * Updates the UI
     */
    private void updateUI(){
        String text = "";

        //update player position in the gameView.
        gameView.drawPlayer(getRow(game.getPlayerPos()), getCol(game.getPlayerPos()));

        //draw the exit room.
        ExitRoom exitRoom = findViewById(R.id.exitRoom);

        //Check if the exit is revealed or not.
        if(game.getExitRevealed())
            exitRoom.setRevealed();
        else
            exitRoom.reset();

        exitRoom.drawExit(getRow(game.getExit().getPos()), getCol(game.getExit().getPos()));


        TextView scoreText = findViewById(R.id.scoreText);
        text = "Score: " + game.getScore();
        scoreText.setText(text);

        TextView bowText = findViewById(R.id.bowText);
        text = "Bow: " + ((game.getBowPresent()) ? "yes" : "no");
        bowText.setText(text);

        TextView arrowsText = findViewById(R.id.arrowsText);
        text = "Arrows: " + game.getArrowCount();
        arrowsText.setText(text);

        TextView textArea = findViewById(R.id.textArea);
        textArea.setText(game.getText());

        colorVisited();

        if(isFABOpen)
            showFABMenu();
        else
            closeFABMenu();


        //very convoluted because enable to edit views in gameView.
        if(gameView.getArrowsEnabled())
            enableArrows();
        else
            disableArrows();
    }

    /**
     * Color the visited areas.
     */
    private void colorVisited(){

        ArrayList<Integer> visitedRooms = game.getVisitedRooms();

        for(int i = 0; i < visitedRooms.size(); i++){
            rooms.get(visitedRooms.get(i)).setVisited();
        }
    }

    /**
     * Enable and disable arrow buttons, triggered by the state machine.
     */
    private void enableArrows(){
        findViewById(R.id.uArrow).setEnabled(true);
        findViewById(R.id.dArrow).setEnabled(true);
        findViewById(R.id.lArrow).setEnabled(true);
        findViewById(R.id.rArrow).setEnabled(true);
    }

    private void disableArrows(){
        findViewById(R.id.uArrow).setEnabled(false);
        findViewById(R.id.dArrow).setEnabled(false);
        findViewById(R.id.lArrow).setEnabled(false);
        findViewById(R.id.rArrow).setEnabled(false);
    }

    //lose dialog

    /**
     * Win/Lose dialogs triggered by the state machine.
     */
    public void loseDialog(){
        LoseDialog loseDialog = new LoseDialog();

        loseDialog.show(getSupportFragmentManager(),"loseDlg");
    }

    public void winDialog(){
        WinDialog winDialog = new WinDialog();

        winDialog.show(getSupportFragmentManager(), "winDlg");
    }


    /**
     * Reset the area colors.
     */
    private void resetRooms(){

        for(int i = 1; i < 16;i++){
            rooms.get(i).setUnvisited();
        }
    }



    private void insertRooms(){
        rooms.add(findViewById(R.id.normalRoom0));
        rooms.add(findViewById(R.id.normalRoom1));
        rooms.add(findViewById(R.id.normalRoom2));
        rooms.add(findViewById(R.id.normalRoom3));
        rooms.add(findViewById(R.id.normalRoom4));
        rooms.add(findViewById(R.id.normalRoom5));
        rooms.add(findViewById(R.id.normalRoom6));
        rooms.add(findViewById(R.id.normalRoom7));
        rooms.add(findViewById(R.id.normalRoom8));
        rooms.add(findViewById(R.id.normalRoom9));
        rooms.add(findViewById(R.id.normalRoom10));
        rooms.add(findViewById(R.id.normalRoom11));
        rooms.add(findViewById(R.id.normalRoom12));
        rooms.add(findViewById(R.id.normalRoom13));
        rooms.add(findViewById(R.id.normalRoom14));
        rooms.add(findViewById(R.id.normalRoom15));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        circlePlayer.animate().translationY(0);
        squarePlayer.animate().translationY(0);
    }

    /**
     * Open/close the floating action button menus.
     */
    private void showFABMenu() {
        isFABOpen = true;
        circlePlayer.animate().translationY(DpToPixels(55));
        squarePlayer.animate().translationY(DpToPixels(105));
    }

    public void onBurst(View view) {
        if (!isFABOpen) {
            showFABMenu();
        } else {
            closeFABMenu();
        }
    }

    /**
     * Switch the player shape.
     * @param view
     */
    public void onCircle(View view){
        gameView.setCircle(true);
        updateUI();
    }

    public void onRectangle(View view){
        gameView.setCircle(false);
        updateUI();
    }


    public float DpToPixels(float dp) {
        float pxPerDp = (float) getResources().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT;
        return dp * pxPerDp;
    }

    /**
     * Save and restore instance states.
     */

    private static final String SCORE = "score"; //key for the score value.
    private static final String BOWPOS = "bowPos"; //key for the score value.
    private static final String BOWPRESENT = "bowPresent"; //key for the score value.
    private static final String ARROW1POS = "arrow1Pos"; //key for the score value.
    private static final String ARROW2POS = "arrow2Pos"; //key for the score value.
    private static final String ARROWCOUNT = "arrowCount";
    private static final String PITPOS = "pitPos";
    private static final String PLAYERPOS = "playerPos";
    private static final String WUMPUSPOS = "wumpusPos";
    private static final String BATPOS = "batPos";
    private static final String EXITPOS = "exitPos";
    private static final String TEXT = "text";
    private static final String VISITEDROOMS = "visitedRooms";
    private static final String BATPRESENT = "batPresent";
    private static final String PITPRESENT = "pitPresent";
    private static final String WWUMPUSPRESENT = "wumpusPresent";

    private static final String ARROWSENABLED = "arrowsEnabled";
    private static final String EXITREVEALED = "exitRevealed";
    private static final String ISCIRCLE = "isCirlce";


    private static final String STATEENUM = "stateEnum";

    private static final String ISFABOPEN = "isFABOpen";

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        game.setScore(savedInstanceState.getInt(SCORE));

        //set the positions of the game objects.
        game.getBow().setPos(savedInstanceState.getInt(BOWPOS));
        game.setBowPresent(savedInstanceState.getBoolean(BOWPRESENT));

        game.getArrow1().setPos(savedInstanceState.getInt(ARROW1POS));
        game.getArrow2().setPos(savedInstanceState.getInt(ARROW2POS));
        game.setArrowCount(savedInstanceState.getInt(ARROWCOUNT));

        game.getPit().setPos(savedInstanceState.getInt(PITPOS));
        game.getPlayer().setPos(savedInstanceState.getInt(PLAYERPOS));
        game.getWumpus().setPos(savedInstanceState.getInt(WUMPUSPOS));
        game.getBat().setPos(savedInstanceState.getInt(BATPOS));
        game.getExit().setPos(savedInstanceState.getInt(EXITPOS));
        game.setText(savedInstanceState.getString(TEXT));
        game.setVisitedRooms(savedInstanceState.getIntegerArrayList(VISITEDROOMS));
        game.setBatPresent(savedInstanceState.getBoolean(BATPRESENT));
        game.setExitRevealed(savedInstanceState.getBoolean(EXITREVEALED));
        game.setPitPresent(savedInstanceState.getBoolean(PITPRESENT));
        game.setWumpusPresent(savedInstanceState.getBoolean(WWUMPUSPRESENT));

        gameView.setArrowsEnabled(savedInstanceState.getBoolean(ARROWSENABLED));
        gameView.setCircle(savedInstanceState.getBoolean(ISCIRCLE));


        state.mapToEnum(savedInstanceState.getInt(STATEENUM)); //very trial version

        isFABOpen = savedInstanceState.getBoolean(ISFABOPEN);

        game.setPositions();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(SCORE, game.getScore());
        savedInstanceState.putInt(BOWPOS, game.getBow().getPos());
        savedInstanceState.putBoolean(BOWPRESENT, game.getBowPresent());
        savedInstanceState.putInt(ARROW1POS, game.getArrow1().getPos());
        savedInstanceState.putInt(ARROW2POS,game.getArrow2().getPos());
        savedInstanceState.putInt(ARROWCOUNT, game.getArrowCount());
        savedInstanceState.putInt(PITPOS, game.getPit().getPos());
        savedInstanceState.putInt(PLAYERPOS, game.getPlayerPos());
        savedInstanceState.putInt(WUMPUSPOS, game.getWumpus().getPos());
        savedInstanceState.putInt(BATPOS, game.getBat().getPos());
        savedInstanceState.putInt(EXITPOS, game.getExit().getPos());
        savedInstanceState.putString(TEXT, game.getText());
        savedInstanceState.putIntegerArrayList(VISITEDROOMS, game.getVisitedRooms());
        savedInstanceState.putBoolean(BATPRESENT, game.getBatPresent());
        savedInstanceState.putBoolean(EXITREVEALED, game.getExitRevealed());
        savedInstanceState.putBoolean(PITPRESENT, game.getPitPresent());
        savedInstanceState.putBoolean(WWUMPUSPRESENT, game.isWumpusPresent());

        savedInstanceState.putBoolean(ARROWSENABLED, gameView.getArrowsEnabled());
        savedInstanceState.putBoolean(ISCIRCLE, gameView.getCircle());

        savedInstanceState.putInt(STATEENUM, state.GetState().ordinal());

        savedInstanceState.putBoolean(ISFABOPEN, isFABOpen);

    }

    public int getScore(){
        return game.getScore();
    }

    public Game getGame(){
        return game;
    }

}