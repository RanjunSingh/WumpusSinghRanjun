package edu.sdsmt.Singh_Ranjun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Draws a normal room.
 */
public class NormalRoom extends RoomView {


    private Paint paint = new Paint();

    public NormalRoom(Context context) {
        super(context);
        init(context);
    }

    public NormalRoom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NormalRoom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NormalRoom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void onDraw(Canvas canvas){
        //Draw a shape that denotes a room.
//        Paint p = new Paint();
        int radius = getWidth() / 2;
//        gameView
        GameView gameView = findViewById(R.id.gameView);
//        int height = gameView.getHeight();



        canvas.drawCircle(getWidth()/2,getHeight()/2,radius, paint);
    }

    public void setVisited(){
        paint.setColor(Color.GREEN);
        invalidate();
    }

    public void setUnvisited(){
        paint.setColor(Color.BLACK);
        invalidate();
    }



}
