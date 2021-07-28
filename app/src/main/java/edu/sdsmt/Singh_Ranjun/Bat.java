package edu.sdsmt.Singh_Ranjun;

/**
 * bat Game Object.
 */
public class Bat extends GameObject{

    private boolean batAlive;

    public Bat(){
        super("bat");
        batAlive = true;
    }

    public boolean isAlive(){ return batAlive; }

    public void kill(){
        batAlive = false;
    }
}
