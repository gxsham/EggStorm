package com.example.gxsha.eggstorm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gxsha on 3/29/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile  boolean playing;
    int screenY;
    int countMisses;
    int score;
    int savedScores[] = new int[2];
    SharedPreferences sharedPreferences;
    private boolean isGameOver;
    private Player player;
    Context context;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Egg egg;
    private Boom boom;
    private  Thread gameThread = null;
    private Activity mParentActivity;
    public GameView(Context context, int screenX, int screenY, Activity parentActivity){
        super(context);
        mParentActivity = parentActivity;
        player = new Player(context, screenX, screenY);
        egg = new Egg(context,screenX,screenY);
        boom = new Boom(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        this.screenY = screenY;
        countMisses = 0;
        isGameOver = false;
        score = 0;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);
        savedScores[0] = sharedPreferences.getInt("lastScore",0);
        savedScores[1] = sharedPreferences.getInt("highScore",0);
        this.context = context;
    }



    @Override
    public void run() {
        while(playing)
        {
            update();
            draw();
            control();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction()& MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                player.setPosition((int)motionEvent.getX() - 100,(int)motionEvent.getY()-50);
                break;
            case MotionEvent.ACTION_UP:
                player.clearList();
                break;
        }
        if(isGameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                mParentActivity.finish();
            }
        }
        return true;
    }

    private  void update(){
        player.update();
        egg.update();

        boom.setX(-250);
        boom.setY(-250);
        if(Rect.intersects(player.getDetectCollision(),egg.getDetectCollision())){

            if(player.getSpeed() > egg.getSpeed()-10 && player.getSpeed()<egg.getSpeed()){
                egg.setY();
                score++;
                if(score%10 == 0 ){
                    egg.setSpeed(score/10);
                }
            }
            else{
                boom.setX(egg.getX());
                boom.setY(egg.getY());
                countMisses++;
                draw();
                if(countMisses == 3){
                    playing = false;
                    isGameOver = true;
                    savedScores[0] = score;
                    if(savedScores[1] <score){
                        savedScores[1] = score;
                    }

                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putInt("lastScore",savedScores[0]);
                    e.putInt("highScore",savedScores[1]);
                    e.apply();
                }
                try{
                    gameThread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                egg.setY();
            }

        }
    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(219,232,255));
            canvas.drawBitmap(player.getBitmap(),player.getX(),player.getY(),paint);
            canvas.drawBitmap(egg.getBitmap(),egg.getX(),egg.getY(),paint);
            canvas.drawBitmap(boom.getBitmap(),boom.getX(),boom.getY(),paint);

            paint.setTextSize(30);
            canvas.drawText("Score:"+score,50,50,paint);

            if(isGameOver){
                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over",canvas.getWidth()/2,canvas.getHeight()/2,paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try{
            gameThread.sleep(17);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }catch (InterruptedException e){

        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
