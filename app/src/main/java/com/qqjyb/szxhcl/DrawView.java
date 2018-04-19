package com.qqjyb.szxhcl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

/**
 * Created by chenkaiju on 2017/10/23.
 */

public class DrawView extends View {
    float minx;
    float maxx;
    float miny;
    float maxy;
    float distancex=20;//距离屏幕左右两边的距离
    float distancey=20;//距离屏幕顶部和底部的距离
    float scaylex;//X轴每个像素表示的宽度
    float scayley;//Y轴每个像素表示的宽度
    float Ox;//原点的横坐标
    float Oy;//原点的纵坐标
    static int SW;
    static int SH;
    boolean drawxy=true;
    boolean resetSWandSH=true;
    Bitmap bitmap=Bitmap.createBitmap(3000,2000, Bitmap.Config.ARGB_8888);
    Canvas mCanvas=new Canvas();
    Paint paint=new Paint();
    Paint bluepaint=new Paint();
    Paint redpaint=new Paint();
    Paint greenpaint=new Paint();
    Paint yellowpaint=new Paint();

    public DrawView(Context context) {
        super(context);

        setPaint();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(resetSWandSH){
            SH= getHeight();
            SW=getWidth();
            resetSWandSH=false;
        }if(drawxy){
            clearView();
            init(-10,2000,-10,300);
            drawOXY();
            drawxy=false;
        }

        canvas.drawBitmap(bitmap,0,0,paint);
    }

    public void clearView(){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap= Bitmap.createBitmap(3000,2000, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(bitmap);
    }
    public void drawOXY(){
        mCanvas.drawLine(0,Oy,SW,Oy,redpaint);
        mCanvas.drawLine(Ox,0,Ox,SH,redpaint);
        float  xtext=minx;
        float  ytext=miny;
        for(int i=0;i<=SW;i+=80){
            int xt=(int)xtext;
            mCanvas.drawText(String.valueOf(xt),getRealX(xtext),Oy+distancey,redpaint);
            xtext+=80*scaylex;
        }
        for(int i=0;i<=SH;i+=60){
            int yt=(int)ytext;
            mCanvas.drawText(String.valueOf(yt),Ox-distancex,getRealY(ytext),redpaint);
            ytext+=60*scayley;
        }
        mCanvas.drawText(String.valueOf(maxx),getRealX(maxx),Oy+distancey,redpaint);
        mCanvas.drawText(String.valueOf(maxy),Ox-distancex,getRealY(maxy),redpaint);
    }

    //初始化，按照比例映射像素点
    public void init(float minx,float maxx,float miny,float maxy){
        this.minx=minx;
        this.maxx=maxx;
        this.miny=miny;
        this.maxy=maxy;
        scaylex=(maxx-minx)/(SW-2*distancex);
        scayley=(maxy-miny)/(SH-2*distancey);
        Ox=-minx/scaylex+distancex;
        Oy=SH-distancey-(-miny/scayley);
    }

    public float getRealX(float vertical){
        return distancex+(vertical-minx)/scaylex;
    }

    public float getRealY(float vertical){
        return SH-distancey-(vertical-miny)/scayley;
    }

    public void drawPointHight(List<Float> x,List<Float>y){
        for(int i=0;i<x.size();i++){
            mCanvas.drawLine(getRealX(x.get(i)),Oy,getRealX(x.get(i)),getRealY(y.get(i)),bluepaint);
        }
    }

    public void drawPointDoubleHight(List<Float> x,List<Float>y){
        mCanvas.setBitmap(bitmap);
        for(int i=0;i<x.size();i++){
            float x1=getRealX(x.get(i));
            float y1=getRealY(-y.get(i));
            float y2=getRealY(y.get(i));
            mCanvas.drawLine(x1,y1,x1,y2,bluepaint);
        }
    }

    public void drawPointtoLine(List<Float> x,List<Float>y){
        for(int i=0;i<x.size()-1;i++){
            mCanvas.drawLine(getRealX(x.get(i)),getRealY(y.get(i)),getRealX(x.get(i+1)),getRealY(y.get(i+1)),bluepaint);
        }
    }

    public void drawPointtoLine(List<zhuobiao> x){
        for(int i=0;i<x.size()-1;i++){
            mCanvas.drawLine(getRealX(x.get(i).x),getRealY(x.get(i).y),getRealX(x.get(i+1).x),getRealY(x.get(i+1).y),bluepaint);
        }
    }

    public void setPaint(){
        bluepaint.setAntiAlias(true);
        bluepaint.setStrokeWidth(6);
        bluepaint.setTextSize(30);
        bluepaint.setColor(Color.BLUE);
        bluepaint.setStrokeJoin(Paint.Join.ROUND);
        bluepaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bluepaint.setStrokeCap(Paint.Cap.ROUND);

        redpaint.setAntiAlias(true);
        redpaint.setStrokeCap(Paint.Cap.ROUND);
        redpaint.setColor(Color.RED);
        redpaint.setTextSize(30);
        redpaint.setStrokeJoin(Paint.Join.ROUND);
        redpaint.setStyle(Paint.Style.FILL_AND_STROKE);

        greenpaint.setAntiAlias(true);
        greenpaint.setStrokeWidth(6);
        greenpaint.setTextSize(30);
        greenpaint.setColor(Color.GREEN);
        greenpaint.setStrokeJoin(Paint.Join.ROUND);
        greenpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        greenpaint.setStrokeCap(Paint.Cap.ROUND);


        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setTextSize(30);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
