package edu.sdsmt.Singh_Ranjun;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Parent class for the normal and exit rooms.
 */
public class RoomView extends View {
    public RoomView(Context context) {
        super(context);
    }

    public RoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void onDraw(Canvas canvas){
    }

}
