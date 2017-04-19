package com.example.gxsha.eggstorm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by gxsha on 3/29/2017.
 */

public class Egg {

    private Bitmap bitmap;

    private int x;
    private int y;

    private int speed = 1;
    private int speedConstant = 15;
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private Rect detectCollision;

    public Egg(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(speedConstant) + 10;
        y = minY;
        x = generator.nextInt(screenX - bitmap.getWidth());

        detectCollision = new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update () {
        y+=speed;
        if( y > maxY){
            Random generator = new Random();
            speed = generator.nextInt(speedConstant) + 10;
            y = minY;
            x = generator.nextInt(maxX - bitmap.getWidth());
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x+bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }
    public void setY(){
        this.y = minY;
        Random generator = new Random();
        x = generator.nextInt(maxX - bitmap.getWidth());
    }

    public Rect getDetectCollision(){
        return  detectCollision;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int value){
        this.speedConstant += value;
    }
}
