package com.example.evanjames.animationtest;

import android.animation.TypeEvaluator;

/**
 * Created by EvanJames on 2015/12/2.
 *  fraction = t / duration

 */
public class PointEvaluator implements TypeEvaluator {

    private float x;
    private float y;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        x = startPoint.getX();
        y = startPoint.getY()+startPoint.getSpeed()*fraction * 10;
        Point point = new Point(x, y,startPoint.getSpeed(),startPoint.getRadius(),startPoint.getAlfa(),startPoint.getRepeatLabel());
        return point;
    }

}