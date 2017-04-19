package com.example.gxsha.eggstorm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.test.espresso.core.deps.guava.collect.EvictingQueue;
import android.view.Display;
import android.view.WindowManager;


import java.util.Queue;

/**
 * Created by gxsha on 3/29/2017.
 */

public class Player {

    private Bitmap bitmap;
    private Context mContext;

    private int x;
    private int y;
    private Rect detectCollision;
    private int speed = 0;
    Queue<Integer> pointList = EvictingQueue.create(10);
    public Player(Context context, int screenX, int screenY)
    {
        x = screenX/2;
        y = screenY - 200;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.hat);
        detectCollision = new Rect(x,y, bitmap.getWidth(),bitmap.getHeight());
        this.mContext = context;
    }


    public void update()
    {
        //detect collision only from top
        // adjust speed, make the game interesting
        //redraw had. make it smaller.
        //draw multiple color eggs
        //add score
        //add end of game, replay
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x+ bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision(){
        return detectCollision;
    }
    public void setPosition(int touchX, int touchY)
    {
        x = touchX;
        y = touchY;
        pointList.add(y);
    }


    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX()
    {
        return x;
    }
    public int getY(){
        return y;
    }

    public int getSpeed(){
        speed = (y - pointList.peek())/pointList.size();
        return speed;
    }
    public void clearList()
    {

        for (int element:pointList) {
            element = 0;
        }
        speed = 0;
    }
}
