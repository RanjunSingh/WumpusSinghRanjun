package edu.sdsmt.Singh_Ranjun;

//objects like bow/arrow, wumpus, player.

/**
 * Parent gameObject class.
 */
public class GameObject {
    private String name;
    private int pos;

    //An empty GameObject (forward)
    public GameObject(){
        this("empty");
    }

    //Construct a game object with a name.
    public GameObject(String name){
//        this.name = name;
        this(name,-1);
    }

    public GameObject(String name, int pos){
        this.name = name;
        this.pos = pos;
    }

    @Override
    public String toString(){
        if (pos > 15)
            return "";
        return "[" + pos / 4 + ", " + pos % 4 + "] " + name;
    }


    public String getName(){ return name; }
    public int getPos(){ return pos; }
    public void setPos(int newPos){
        pos = newPos;
    }

}
