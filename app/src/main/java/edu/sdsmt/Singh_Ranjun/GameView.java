package edu.sdsmt.Singh_Ranjun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Handle the game view and plater drawing.
 */
public class GameView extends View {

    private int xPlayer = 50;
    private int yPlayer = 50;
    private int x;
    private int y;
    Context context;
    private boolean arrowsEnabled = false;
    private boolean circle;
    private Paint paint = new Paint();



    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        circle = true;
    }


    public void onDraw(Canvas canvas){

        int dim = getWidth();
        int playerSize = dim / 16;

        this.xPlayer = x * dim/4 + dim/8;
        this.yPlayer = y * dim/4 + dim/8;


        if(circle)
            canvas.drawCircle(xPlayer, yPlayer, playerSize, paint); //player circle
        else
            canvas.drawRect(xPlayer - playerSize, yPlayer - playerSize,
                    xPlayer + playerSize, yPlayer + playerSize, paint);
    }

    public void drawPlayer(int x, int y) {

        this.x = x;
        this.y = y;

        invalidate();
    }

    public void enableArrows(){

        arrowsEnabled = true;
    }

    public void disableArrows(){

        arrowsEnabled = false;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public boolean getArrowsEnabled(){
        return arrowsEnabled;
    }
    public void setArrowsEnabled(boolean enabled){
        this.arrowsEnabled = enabled;
    }

    public boolean getCircle(){ return circle; }
    public void setCircle(boolean circle){ this.circle = circle; }

    public void reset(){
        circle = true;
    }



}
