package com.example.evanjames.animationtest;

/**
 * Created by EvanJames on 2015/12/2.
 */
public class Point {


    private final int alfa;
    private float x;
    private float y;
    private float speed;
    private float Radius;
    private Boolean RepeatLabel;

    public Point(float x, float y,float speed,float Radius,int alfa,boolean RepeatLabel) {
        this.x = x;
        this.y = y;

        this.speed = speed;

        this.Radius = Radius;

        this.alfa = alfa;
        this.RepeatLabel = RepeatLabel;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public float getSpeed(){
        return speed;
    }

    public int getAlfa(){
        return alfa;
    }

    public float getRadius() {
        return Radius;
    }
    public Boolean getRepeatLabel(){
        return RepeatLabel;
    }

}