package com.example.evanjames.animationtest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by EvanJames on 2015/12/2.
 */
public class MyAnimView extends SurfaceView implements SurfaceHolder.Callback{

    private static ArrayList<Point> Points ;

    private static Paint mPaint;
    private final SurfaceHolder mHolder;
    private final Bitmap bmp;
    private int flakeCount = 125;
    private boolean Label = true;
    private Point currentPoint;
    private DrawThread mDrawThread;


    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        Resources res=getResources();
        bmp= BitmapFactory.decodeResource(res, R.drawable.back);
        initialize();
    }

    private void initialize() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        Points = new ArrayList<>();
        for (int i = 0; i < flakeCount; i++) {
            float x = (float) Math.floor(Math.random() * dm.widthPixels);//初始X坐标;//初始X坐标
            float y = (float) Math.floor(Math.random() * dm.heightPixels);//初始X坐标;//初始Y坐标

            float RADIUS = (float) ((Math.random() * 8f) + 6f);//初始半径
            float speed = (float) ((Math.random() * 4) + 2);
            int alfa = (int) Math.floor(255 * Math.random());
            currentPoint = new Point(x,y,speed,RADIUS,alfa,false);
            Points.add(currentPoint);
        }
    }


    //在创建时激发，一般在这里调用画图的线程
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(null == mDrawThread) {

            mDrawThread = new DrawThread();
            mDrawThread.start();
        }
    }

    //在surface的大小发生改变时激发
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //销毁时激发，一般在这里将画图的线程停止、释放
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(null != mDrawThread) {
            mDrawThread.stopThread();
        }
    }

    private class DrawThread extends Thread{

        public boolean isRunning = false;
        private Canvas canvas;


        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            boolean workIsNotFinish = true;
            while (workIsNotFinish) {
                try {
                    this.join();// 保证run方法执行完毕
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                workIsNotFinish = false;
            }
        }

        public void run(){
            while (isRunning) {
                try {
                    synchronized (mHolder) {
                        canvas = mHolder.lockCanvas();
                        //清屏操作
                        drawSprite(canvas);

                        //下面进行绘图操作
                            for (int i = 0; i < flakeCount; i++) {
                                currentPoint = Points.get(i);

                                float RADIUS = currentPoint.getRadius();
                                float speed = currentPoint.getSpeed();
                                int alfa = currentPoint.getAlfa();
                                float x = currentPoint.getX();
                                float y = currentPoint.getY()+speed;
                                if(y>=canvas.getHeight()||x>=canvas.getWidth()){
                                    y = 0;
                                    x = (float) Math.floor(Math.random() * canvas.getWidth());//初始X坐标
                                }
                                mPaint.setAlpha(alfa);

                                canvas.drawCircle(x, y, RADIUS, mPaint);
                                Points.set(i,new Point(x,y,speed, RADIUS,alfa,false));

                            }

                        }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                        mHolder.unlockCanvasAndPost(canvas);
                }

            }
        }
    }

    private void drawSprite(Canvas canvas) {
        //清屏操作
        canvas.drawBitmap(bmp,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);

    }
}
