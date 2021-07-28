package edu.sdsmt.Singh_Ranjun;

/**
 * Wumpus gameObject.
 */
public class Wumpus extends GameObject{

    private boolean wumpusAlive;

    public Wumpus(){
        super("wumpus");
        wumpusAlive = true;
    }

    public boolean isAlive(){ return wumpusAlive; }

    public void kill(){
        wumpusAlive = false;
    }
    public void revive(){ wumpusAlive = true; }//used in reset
}
