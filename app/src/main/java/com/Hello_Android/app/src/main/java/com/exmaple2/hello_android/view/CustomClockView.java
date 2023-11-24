package com.exmaple2.hello_android.view;

import android.content.Context;
import android.content.pm.Attribution;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.ListFormatter;
import android.util.AttributeSet;
import android.view.View;

import com.exmaple2.hello_android.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CustomClockView extends View {
    private final Timer timer;
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radius;
    private int hour;
    private int minute;
    private int second;
    //导入表盘图像
    Bitmap dialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dial);

    public CustomClockView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = Math.min(getWidth(),getHeight()) / 2;

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                Date date = new Date(currentTime);
                // 使用postInvalidate确保在UI线程中调用invalidate
                CustomClockView.this.post(new Runnable() {
                    @Override
                    public void run() {
                        CustomClockView.this.setTime(date.getHours(), date.getMinutes(), date.getSeconds());
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //draw clock face
        float startX = centerX - (dialBitmap.getWidth() / 2);
        float startY = centerY - (dialBitmap.getHeight() / 2);
        canvas.drawBitmap(dialBitmap, startX, startY, paint);

        // 计算时针的角度并绘制
        float hourAngle = (hour % 12 * 30) + (minute * 0.5f);
        float hourX = (float) (centerX + radius * 0.5 * Math.cos(Math.toRadians(hourAngle - 90)));
        float hourY = (float) (centerY + radius * 0.5 * Math.sin(Math.toRadians(hourAngle - 90)));
        canvas.drawLine(centerX, centerY, hourX, hourY, paint);

        // 计算分针的角度并绘制
        float minuteAngle = minute * 6;
        float minuteX = (float) (centerX + radius * 0.7 * Math.cos(Math.toRadians(minuteAngle - 90)));
        float minuteY = (float) (centerY + radius * 0.7 * Math.sin(Math.toRadians(minuteAngle - 90)));
        canvas.drawLine(centerX, centerY, minuteX, minuteY, paint);

        // 计算秒针的角度并绘制
        float secondAngle = second * 6;
        float secondX = (float) (centerX + radius * 0.9 * Math.cos(Math.toRadians(secondAngle - 90)));
        float secondY = (float) (centerY + radius * 0.9 * Math.sin(Math.toRadians(secondAngle - 90)));
        canvas.drawLine(centerX, centerY, secondX, secondY, paint);
    }
    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        centerX = width / 2;
        centerY = height / 2;
        radius = Math.min(width, height) / 2;

        int originalWidth = dialBitmap.getWidth();
        int originalHeight = dialBitmap.getHeight();

        // 计算缩放比例
        float scale = Math.min((float) width / originalWidth, (float) height / originalHeight);

        // 根据缩放比例计算新的宽度和高度
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);

        // 重新调整dialBitmap的大小
        dialBitmap = Bitmap.createScaledBitmap(dialBitmap, scaledWidth, scaledHeight, true);

    }
    private Bitmap scaleBitmap(Bitmap bitmap, float scale) {
        int scaledWidth = (int) (bitmap.getWidth() * scale);
        int scaledHeight = (int) (bitmap.getHeight() * scale);
        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
    }
    public void setTime(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        invalidate();
    }
}
