package edu.sdsmt.Singh_Ranjun;

/**
 * Arrow Game Object.
 */
public class Arrow extends GameObject{
    private boolean pickedUp;
    private boolean spent;

    public Arrow(){

        super("arrow");
        pickedUp = false;
        spent = false;
    }

    public void setPicked(){
        pickedUp = true;
    }

    public boolean getPicked(){
        return this.pickedUp;
    }
    public boolean getSpent(){ return this.spent; }
}
