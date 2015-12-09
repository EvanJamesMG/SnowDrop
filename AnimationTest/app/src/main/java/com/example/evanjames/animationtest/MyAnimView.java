package com.example.evanjames.animationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Created by EvanJames on 2015/12/2.
 */
public class MyAnimView extends View {

    private static ArrayList<Point> Points ;

    private static Paint mPaint;
    private int flakeCount = 200;
    private boolean Label = true;
    private Point currentPoint;


    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(Label) {
             Points = new ArrayList<>();
             for (int i = 0; i < flakeCount; i++) {
                 float x = (float) Math.floor(Math.random() * canvas.getWidth());//初始X坐标
                 float y = (float) Math.floor(Math.random() * canvas.getHeight());//初始Y坐标

                 float RADIUS = (float) ((Math.random() * 8f) + 6f);//初始半径
                 float speed = (float) ((Math.random() * 200) + 100);
                 int alfa = (int) Math.floor(255 * Math.random());
                 currentPoint = new Point(x,y,speed,RADIUS,alfa,false);
                 Points.add(currentPoint);
                 mPaint.setAlpha(alfa);
                 canvas.drawCircle(x, y, RADIUS, mPaint);
                 startAnimation(x, y,speed,RADIUS,alfa, i,false);

                 Label = false;

             }
         }else{
                for (int i = 0; i < flakeCount; i++) {
                    currentPoint = Points.get(i);
                    float x = currentPoint.getX();
                    float y = currentPoint.getY();

                    float RADIUS = currentPoint.getRadius();
                    float speed = currentPoint.getSpeed();
                    int alfa = currentPoint.getAlfa();

                    Boolean repeatlabel = currentPoint.getRepeatLabel();
                    mPaint.setAlpha(alfa);


                    if(repeatlabel){
                        canvas.drawCircle(x, y, RADIUS, mPaint);
                        startAnimation(x, y, speed, RADIUS,alfa, i,false);
                    }else{
                        canvas.drawCircle(x, y, RADIUS, mPaint);
                    }
                }
         }
    }


    private void startAnimation(float x,float y, float speed, final float Radius,int alfa, final int label,boolean RepeatLabel) {
        Point startPoint = new Point(x,y,speed,Radius,alfa,RepeatLabel);
        final Point endPoint = new Point(x,1.05f*getHeight(),speed,Radius,alfa,false);

        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point tempoint = (Point) animation.getAnimatedValue();

                if (tempoint.getY() >= getHeight() || tempoint.getX() >= getWidth()) {
                    float newy = -50;//-(float) Math.floor(Math.random() * getHeight());
                    float newx = (float) Math.floor(Math.random() * getWidth());
                    float newspeed = (float) ((Math.random() * 200) + 100);
                    float newRADIUS = (float) ((Math.random() * 8f) + 6f);//初始半径
                    int newalfa = (int) Math.floor(255 * Math.random());

                    Points.set(label, new Point(newx, newy, newspeed, newRADIUS,newalfa, true));
                    invalidate();
                } else {
                    float y = tempoint.getY();
                    float x = tempoint.getX();
                    float RADIUS = (tempoint.getRadius());//初始半径
                    float speed = tempoint.getSpeed();
                    int alfa = tempoint.getAlfa();

                    Points.set(label, new Point(x, y, speed,  RADIUS,alfa,false));
                    invalidate();
                }
            }
        });
        anim.setRepeatMode(1);
        anim.setRepeatCount(-1);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(10000);
        anim.start();
    }

}
