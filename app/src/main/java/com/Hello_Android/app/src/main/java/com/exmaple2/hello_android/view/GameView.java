package com.exmaple2.hello_android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.exmaple2.hello_android.R;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int NOT_A_VALIDATE_POSITION = -1;
    private GameLoopThread gameLoopThread;
    private float touchY = NOT_A_VALIDATE_POSITION;
    private float touchX = NOT_A_VALIDATE_POSITION;
    private ArrayList<GameSpriter> gameSpriters = new ArrayList<>();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    private boolean areAllSpritesInvisible() {
        for (GameSpriter spriter : gameSpriters) {
            if (spriter.isVisible) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoopThread = new GameLoopThread();
        gameLoopThread.start();
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        gameLoopThread.end();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: //触摸按下事件
                this.touchX = NOT_A_VALIDATE_POSITION;
                this.touchY = NOT_A_VALIDATE_POSITION;
                break;
            case MotionEvent.ACTION_MOVE://触摸移动事件

                break;
            case MotionEvent.ACTION_UP://触摸抬起事件
                this.touchX = event.getX() / this.getWidth();
                this.touchY = event.getY() / this.getHeight();
                break;

        }
        return true;
    }

    private class GameLoopThread extends Thread{
        boolean isLive = true;
        @Override
        public void run() {
            super.run();
            int hitNumber = 0;
            int iloop;
            for(iloop = 0; iloop < 3; ++iloop){
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(),R.drawable.book_1));
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(),R.drawable.book_2));
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(),R.drawable.book_no_name));

            }
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setTextSize(72);
            paint.setStyle(Paint.Style.FILL);

            while(isLive){
                Canvas canvas = null;
                try{
                    canvas = GameView.this.getHolder().lockCanvas();
                    canvas.drawColor(Color.GRAY);
                    canvas.drawText("You learn"+ hitNumber,100,100,paint);
                    for (GameSpriter gameSpriter : gameSpriters) {
                        if (gameSpriter.detectCollision()) {
                            hitNumber++;
                        }
                        gameSpriter.move(canvas);
                    }
                    for (GameSpriter gameSrpiter:
                            gameSpriters) {
                        gameSrpiter.draw(canvas);
                    }
                    if (areAllSpritesInvisible()) {
                        canvas.drawText("You Win!", canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                        isLive = false; // 停止游戏循环
                    }
                }finally {
                    if (canvas != null) {
                        GameView.this.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                try{
                    Thread.sleep(300);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        public void end() {
            isLive = false;
        }
    }

    private class GameSpriter {
        private double relatedX;
        private double relatedY;
        private double direction;
        private final int imageResourceId;
        private boolean isVisible;

        public GameSpriter(double relatedX, double relatedY, int imageResourceId) {
            this.isVisible = true;
            this.relatedX = relatedX;
            this.relatedY = relatedY;
            this.imageResourceId = imageResourceId;
            this.direction = Math.random() * 2 * Math.PI;

        }

        public void draw(Canvas canvas){
            if (!isVisible) return;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), this.imageResourceId);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200,200,true);

            canvas.drawBitmap(scaledBitmap,(int)(canvas.getWidth()* this.relatedX)
                    ,(int)(canvas.getHeight()* this.relatedY),null);

        }

        public void move(Canvas canvas) {
            this.relatedY += Math.sin(this.direction) * 0.05;
            this.relatedX += Math.cos(this.direction) * 0.05;
            if(this.relatedY > 1) this.relatedY = 0;
            if(this.relatedY < 0) this.relatedY = 1;
            if(this.relatedX > 1) this.relatedX = 0;
            if(this.relatedX < 0) this.relatedX = 1;
            if(Math.random() < 0.1) this.direction = Math.random() * 2 * Math.PI;
        }

        public boolean detectCollision() {
            double distanceX = Math.abs(this.relatedX - GameView.this.touchX);
            double distanceY = Math.abs(this.relatedY - GameView.this.touchY);
            if (distanceX < 0.05 && distanceY < 0.05) { // 调整阈值以适合您的游戏
                isVisible = false; // 如果检测到碰撞，设置为不可见
                return true;
            }
            return false;
        }
        public void resetVisibility() {
            isVisible = true;
        }

    }

}
