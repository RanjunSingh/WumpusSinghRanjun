package edu.sdsmt.Singh_Ranjun;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Model Populates the 4x4 array with gameObjects.

/**
 * Holds the entire game, and implements some of the game logic.
 */
public class Game {

    //Areas hold the roomview along with any game objects.
    private List<Area> areaList;
    private int score;
    private Player player;
    private Arrow arrow1;
    private Arrow arrow2;
    private Bat bat;
    private Bow bow;
    private Exit exit;
    private Pit pit;
    private Wumpus wumpus;

    private String text;
    private boolean bowPresent;
    private int arrowCount;
    private boolean gameOver;
    private boolean batPresent;
    private boolean pitPresent;
    private boolean wumpusPresent;
    private boolean exitRevealed;
    private boolean arrowPresent;
    private ArrayList<Integer> visitedRooms;
    private boolean arrowsSpent;
    private boolean exitReached;
    private boolean targetHit; //only internal use.

    private Random rand = new Random();
    int randPos;
    int upperBound = 16; //16 spots in the grid.


    public Game(){

        //Create the gameObjects.
        player = new Player();
        arrow1 = new Arrow();
        arrow2 = new Arrow();
        bat = new Bat();
        bow = new Bow();
        exit = new Exit();
        pit = new Pit();
        wumpus = new Wumpus();
        score = 0;
        gameOver = false;
        arrowPresent = false;
        arrowsSpent = false;
        exitReached = false;
        targetHit = false;
        text = "";
        randPos = 0;

        visitedRooms = new ArrayList<>();
        areaList = new ArrayList<>();
        visitedRooms.add(0);

        //Create the areas, and add them to the areaList.
        for(int i = 0; i < 18; i++){ //one extra area for player objects, one more for used objects.
            areaList.add(new Area());
        }

        //Create the gameObjects and add them to the rooms randomly.
        Generate();
        setPositions();//insert gameObjects into the areas list.
    }

    /**
     * Generate positions for the game objects.
     */
    public void Generate(){

        //Add gameObjects to random spots in the areaList. (every object knows its pos).
        player.setPos(0);

        generateRandomEmpty();
        arrow1.setPos(randPos);

        generateRandomEmpty();

        while(randPos == arrow1.getPos())
            generateRandomEmpty();

        arrow2.setPos(randPos);

        generateRandomEmpty();
        while(randPos == 0 || randPos == arrow1.getPos() || randPos == arrow2.getPos())
            generateRandomEmpty();
        bat.setPos(randPos);

        generateRandomEmpty();


        if(randPos != 0)
            exit.setPos(randPos);
        else{
            generateRandomEmpty();
            exit.setPos(randPos);
        }


        generateRandomEmpty();
        while(randPos == arrow1.getPos() || randPos == arrow2.getPos() || randPos == 0 || randPos ==
        bat.getPos() || randPos == exit.getPos())
             generateRandomEmpty();

        pit.setPos(randPos);


        generateRandomEmpty();
        while(randPos == arrow1.getPos() || randPos == arrow2.getPos() || randPos == 0)
            generateRandomEmpty();

        wumpus.setPos(randPos);

        generateRandomEmpty();

        //don't place bow in wumpus or pit (un winnable). (should've put more thought into this sry).
        while(randPos == wumpus.getPos() || randPos == pit.getPos() || randPos == bat.getPos() ||
        randPos == 0 ||randPos == arrow1.getPos() || randPos == arrow2.getPos())
            randPos = rand.nextInt(upperBound);

        bow.setPos(randPos);

    }

    //insert gameObjects into the areas.
    public void setPositions(){
        areaList.get(player.getPos()).addGameObject(player);
        areaList.get(arrow1.getPos()).addGameObject(arrow1);
        areaList.get(arrow2.getPos()).addGameObject(arrow2);
        areaList.get(bat.getPos()).addGameObject(bat);
        areaList.get(bow.getPos()).addGameObject(bow);
        areaList.get(exit.getPos()).addGameObject(exit);
        areaList.get(pit.getPos()).addGameObject(pit);
        areaList.get(wumpus.getPos()).addGameObject(wumpus);
    }



    //region Movement

    /**
     * Move player and check area for events.
     */
    public void moveRight() {
        int playerPos = player.getPos();
        score++;

        playerPos = getRight(player.getPos());
        player.setPos(playerPos);

        //check the area at the new pos.
        checkArea(areaList.get(playerPos));

    }

    public void moveLeft() {
        int playerPos = player.getPos();
        score++;


        playerPos = getLeft(player.getPos());
        player.setPos(playerPos);

        //check the area at the new pos.
        checkArea(areaList.get(playerPos));
    }

    public void moveDown() {
        int playerPos = player.getPos();
        score++;

        playerPos = getDown(player.getPos());
        player.setPos(playerPos);

        //check the area at the new pos.
        checkArea(areaList.get(playerPos));
    }

    //shooting

    public void moveUp() {
        int playerPos = player.getPos();
        score++;

        playerPos = getUp(player.getPos());
        player.setPos(playerPos);

        //check the area at the new pos.
        checkArea(areaList.get(playerPos));
    }
    //endregion

    /**
     * Arrow shot functionality
     * @param direction
     */
    public void arrowShot(String direction){
        text = ""; //clear the text
        int currentPos = player.getPos();

        targetHit = false;
        Area area1;
        Area area2;

        if(direction.equals("up"))
            currentPos = getUp(currentPos);
        else if(direction.equals("left"))
            currentPos = getLeft(currentPos);
        else if(direction.equals("right"))
            currentPos = getRight(currentPos);
        else if(direction.equals("down"))
            currentPos = getDown(currentPos);

        area1 = areaList.get(currentPos); //first adjacent square.
        arrowCheck(area1);

        if(direction.equals("up"))
            currentPos = getUp(currentPos);
        else if(direction.equals("left"))
            currentPos = getLeft(currentPos);
        else if(direction.equals("right"))
            currentPos = getRight(currentPos);
        else if(direction.equals("down"))
            currentPos = getDown(currentPos);

        area2 = areaList.get(currentPos);
        if(!targetHit)
            arrowCheck(area2);

        arrowCount--; //one arrow has been spent.

        if(arrow1.getPos() == 16)
            arrow1.setPos(17);
        else if(arrow2.getPos() == 16)
            arrow2.setPos(17);

        if(arrow1.getPos() == 17 && arrow2.getPos() == 17){ //both have been used.{
            arrowsSpent = true;
//            System.out.println("arrowsSpent");
        }

        clearAreas();
        setPositions();
    }

    //function to check the area an arrow flies through for the bat and wumpus.

    /**
     * Arrow logic.
     * @param area
     */
    private void arrowCheck(Area area){

        area.checkWumpus();
        area.checkBat();

        //only kill the wumpus or the bat, wumpus take prio here.
        if(wumpusPresent && arrowCount > 0) {
            wumpus.kill(); //arrow hit the wumpus.
            text += "\nWumpus hit!";
            wumpus.setPos(17); //move to the dead area.

            wumpusPresent = false;
            targetHit = true;
        }
        else if(batPresent && arrowCount > 0) {
            bat.kill();
            text += "\nBat hit!";
            bat.setPos(17);

            batPresent = false;
            targetHit = true;
        }
        //one arrow has been spent.
    }


    //region Position Utility
    //return the position number above this one in the gameplay area.
    private int getUp(int pos){
        int upPos;

        if(pos < 4) // player is on the top edge (0,1,2,3).
            upPos = pos + 12;
        else
            upPos = pos - 4;

        return upPos;
    }

    private int getDown(int pos){
        int downPos;

        if(pos  > 11)// player is on the bottom edge (12,13,14,15).
            downPos = pos - 12;
        else
            downPos = pos + 4;

        return downPos;
    }

    private int getLeft(int pos){
        int leftPos;

        if(pos % 4 == 0) // player is on the left edge (0,4,8,12).
            leftPos = pos + 3;
        else
            leftPos = pos - 1;

        return leftPos;
    }

    private int getRight(int pos){
        int rightPos;

        //update the player pos.
        if(pos % 4 + 1 == 4)// player is on the right edge
            rightPos = pos - 3;
        else
            rightPos = pos + 1;

        return rightPos;
    }
    //endregion


    public void clearAreas(){
        for(int i = 0; i < 16; i++){
            areaList.get(i).clearGameObjects();
        }
    }

    //reset the game(generate random positions).

    /**
     * reset the model.
     */
    public void reset() {
        text = "";
        score = 0;
        bowPresent = false;
        arrowCount = 0;
        gameOver = false;
        arrowPresent = false;
        wumpusPresent = false;
        pitPresent = false;
        batPresent = false;
        wumpus.revive();

        arrowsSpent = false;
        exitReached = false;
        exitRevealed = false;

        clearAreas();
        visitedRooms.clear();

        Generate();
        setPositions();
    }

    public void cheat() {
        text = "";
        text += wumpus.toString() + "\n";
        text += pit.toString() + "\n";
        text += arrow1.toString() + "\n";
        text += arrow2.toString() + "\n";
        text += bat.toString() + "\n";
        text += bow.toString() + "\n";
        text += exit.toString() + "\n";
    }



    /**
     * function to check an area for various events/objects.
     * @param area
     */
    public void checkArea(Area area){
        text = "";
        batPresent = false;
        exitReached = false; //should be reset when entering a new area.
        visitedRooms.add(player.getPos());

        hint();

        if(!bowPresent)
            area.checkBow();

        area.checkArrow();
        area.checkPit();

        area.checkBat(); //will set the batPresent flag.
        if(batPresent) {
            text +="\nBat Encountered!";
            batEncountered();
        }

        area.checkWumpus();
        scaredWumpus();

        if(wumpusPresent || pitPresent )
            gameOver = true;

        area.checkExit();

        clearAreas();
        setPositions();


    }

    //Give the player a hint depending on nearby Gameobjects.

    /**
     * Extension that implements the hint functionality.
     */
    private void hint(){
        int currentPos = player.getPos();
        Area adjArea;

        //square above
        adjArea = areaList.get(getUp(currentPos));//area player is on.
        adjArea.checkBat();
        adjArea.checkPit();
        adjArea.checkWumpus();

        adjArea = areaList.get(getDown(currentPos));
        adjArea.checkBat();
        adjArea.checkPit();
        adjArea.checkWumpus();

        adjArea = areaList.get(getLeft(currentPos));
        adjArea.checkBat();
        adjArea.checkPit();
        adjArea.checkWumpus();

        adjArea = areaList.get(getRight(currentPos));
        adjArea.checkBat();
        adjArea.checkPit();
        adjArea.checkWumpus();

        if(wumpusPresent)
            text += "\nsmell something";
        if(batPresent)
            text += "\nhear flapping";
        if(pitPresent)
            text += "\nfeel a breeze";

        wumpusPresent = false;
        pitPresent = false;
        batPresent = false;
    }

    /**
     * Extension that implements the scared Wumopus functionlity.
     */
    private void scaredWumpus(){
        if(wumpusPresent){
            //generate a random number, if greater than 7(16 total) the wumpus flees.
            randPos = rand.nextInt(upperBound);
            if(randPos > 7) {
                wumpusPresent = false;
                generateRandomEmpty();
                wumpus.setPos(randPos);
                clearAreas();
                setPositions();
                text = "\nWumpus Fled! You live!";
            }
        }
    }

    /**
     * Implement bat logic.
     */
    private void batEncountered(){

        generateRandomEmpty();
        player.setPos(randPos); //move the player

        if(bowPresent) { //If a bow is present move it.
            generateRandomEmpty();
            bow.setPos(randPos);
            clearAreas();
            setPositions();
        }

        //move the arrows from the 16th square back into the game.
        if(arrowCount > 0){

            if(arrow1.getPos() == 16) { //player has arrow1
                generateRandomEmpty();
                arrow1.setPos(randPos);
                clearAreas();
                setPositions();
            }

            if(arrow2.getPos() == 16) { //player has arrow2
                generateRandomEmpty();
                arrow2.setPos(randPos);
                clearAreas();
                setPositions();
            }
        }

        //reset game object positions.
        arrowCount = 0;
        arrowPresent = false;
        bowPresent = false;
        clearAreas();
        setPositions();
        checkArea(areaList.get(player.getPos())); //recursive call to check the new area. (testing)
    }

    /**
     * Generates a random empty square.
     */
    private void generateRandomEmpty(){
        randPos = rand.nextInt(upperBound);
        while(!areaList.get(randPos).isEmpty()) {
            randPos = rand.nextInt(upperBound);
        }
    }


    //An area envelopes a room and it's associated gameObjects. (Nested Class).
    public class Area{
        private List<GameObject> gameObjects;
        private boolean empty;
        private RoomView room;

        public Area(){
            empty = true;
            gameObjects = new ArrayList<GameObject>();
        }

        //Need a method to add gameobjects to an area
        public void addGameObject(GameObject gameObject){
            gameObjects.add(gameObject); //append the game object to the list.
            empty = false;
        }

        public void checkBow(){
            for(GameObject tempObject : gameObjects){
                if(tempObject.getName() == "bow") {
                    bowPresent = true;
                    text += "\nBow";
                    tempObject.setPos(16);
                }
            }
        }

        public void checkArrow(){
            for(GameObject tempObject : gameObjects){
                if(tempObject.getName() == "arrow" && bowPresent) {
                    arrowCount++;
                    tempObject.setPos(16); // move the arrow out of the game.
                    arrowPresent = true;

                    text += "\nPicked up an arrow!";
                }
            }
        }

        public void checkPit(){
            for(GameObject tempObject : gameObjects){
                if(tempObject.getName() == "pit") {

                    pitPresent = true;
                }
            }
        }

        public void checkWumpus(){
            for(GameObject tempObject : gameObjects){
                if(tempObject.getName() == "wumpus") {

                    wumpusPresent = true;
                }
            }
        }

        public void checkBat(){
            for(GameObject tempObject : gameObjects){
                if(tempObject.getName() == "bat") {

                    batPresent = true;
                }
            }
        }

        public void checkExit(){
            for(GameObject tempObject : gameObjects){
                if(tempObject.getName() == "exit") {
                    exitReached = true;
                    exitRevealed = true;
                }
            }
        }


        public boolean isEmpty(){ return empty; }

        public void clearGameObjects(){
            empty = true;
            gameObjects.clear();
        }

    }


    //region: getters and setters
    public void setWumpusPresent(boolean wumpusPresent){ this.wumpusPresent = wumpusPresent; }
    public void setPitPresent(boolean pitPresent){ this.pitPresent = pitPresent; }
    public int getPlayerPos(){
        return player.getPos();
    }
    public void setExitRevealed(boolean exitRevealed){ this.exitRevealed = exitRevealed; }
    public boolean getExitRevealed() { return this.exitRevealed; }
    public boolean getPitPresent(){ return this.pitPresent; }
    public boolean getExitReached(){ return this.exitReached; }
    public boolean arrowsSpent(){ return arrowsSpent; }
    public void setBatPresent(boolean present){ this.batPresent = present; }
    public boolean getBatPresent(){ return batPresent; }
    public void setVisitedRooms(ArrayList<Integer> visitedRooms){ this.visitedRooms = visitedRooms; }
    public ArrayList<Integer> getVisitedRooms(){ return visitedRooms; }

    public boolean isWumpusPresent(){ return wumpusPresent; }
    public void arrowPicked() { arrowPresent = false; }//player picked up the arrow.
    public boolean arrowPresent(){ return arrowPresent; }
    public int getScore(){ return score; }
    public String getText(){ return text; }

    public List<Area> getAreaList() { return areaList;}

    public void setAreaList(List<Area> areaList) { this.areaList = areaList; }

    public void setScore(int score) { this.score = score; }

    public Player getPlayer() {return player;}

    public void setPlayer(Player player) { this.player = player; }

    public Arrow getArrow1() { return arrow1; }

    public void setArrow1(Arrow arrow1) { this.arrow1 = arrow1; }

    public Arrow getArrow2() { return arrow2;}

    public void setArrow2(Arrow arrow2) { this.arrow2 = arrow2; }

    public Bat getBat() { return bat; }

    public void setBat(Bat bat) {this.bat = bat; }

    public void setBow(Bow bow) { this.bow = bow; }

    public Exit getExit() { return exit; }

    public void setExit(Exit exit) { this.exit = exit; }

    public Pit getPit() { return pit;}

    public void setPit(Pit pit) { this.pit = pit;}

    public Wumpus getWumpus() { return wumpus;}

    public void setWumpus(Wumpus wumpus) { this.wumpus = wumpus; }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isBowPresent() { return bowPresent; }

    public void setBowPresent(boolean bowPresent) { this.bowPresent = bowPresent; }

    public boolean getBowPresent(){
        return bowPresent;
    }

    public Bow getBow(){ return this.bow; }

    public int getArrowCount(){ return this.arrowCount; }

    public void setArrowCount(int arrowCount){
        this.arrowCount = arrowCount;
    }

    public boolean getGameOver(){ return gameOver;}

    public boolean wumpusAlive(){ return wumpus.isAlive(); }
    //endregion
}
