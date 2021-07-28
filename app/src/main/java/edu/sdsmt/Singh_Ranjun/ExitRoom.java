package edu.sdsmt.Singh_Ranjun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Exit room view.
 */
public class ExitRoom extends RoomView {
    private Paint paint = new Paint();
    private int x;
    private int y;
    private int xExit = 50;
    private int yExit = 350;
    private int dim;
    private boolean revealed;

    public ExitRoom(Context context) {
        super(context);
        init(context);
    }

    public ExitRoom(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init(context);
    }

    public ExitRoom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ExitRoom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        x = 0;
        y = 3;
        revealed = false;

        dim = getWidth();
        xExit = dim/8;
        yExit = 3 * dim/4 + dim/8;
    }

    public void onDraw(Canvas canvas){
        //Draw a circle/rectangle that signifies the exit room
        dim = getWidth();

        if(revealed)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.BLACK);

        this.xExit = x * dim/4 + dim/8;
        this.yExit = y * dim/4 + dim/8;

        canvas.drawCircle(xExit, yExit, dim/8, paint); //player circle
    }
    public void drawExit(int x, int y){
        this.x = x;
        this.y = y;

        invalidate();
    }

    public void setRevealed(){
        revealed = true;
    }

    public void reset(){
        revealed = false;
    }

}
