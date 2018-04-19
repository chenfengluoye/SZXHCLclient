package com.qqjyb.szxhcl;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ShowScreens extends View {
    Paint paint=new Paint();
    Paint paintend=new Paint();
    double t=0;
    double y=0;
    boolean drawOXY=true;
    int SW;
    int SH;
    double minx;
    double maxx;
    double miny;
    double maxy;
    double scaylex;
    double scayley;
    int T;
    int F;
    int A=1;
    float bb=0;
    float fi=0;
    double W;
    double xw=500;
    double yw=300;
    Bitmap bitmap;
    Canvas canvas;
    Canvas canvasx=new Canvas();
    Chronometer chronometer;
    boolean isresetWH=true;
    List<coordinate> xcoors=new ArrayList<>();
    List<coordinate> ycoors=new ArrayList<>();
    public ShowScreens(Context context) {
        super(context);
        paint.setColor(Color.BLUE);
        paintend.setColor(Color.BLUE);
        paintend.setTextSize(30);
        paintend.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        chronometer=new Chronometer(context);
        W=(Math.PI/180);
    }

    public ShowScreens(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        paintend.setColor(Color.BLUE);
        paintend.setTextSize(30);
        paintend.setStrokeWidth(1);
        chronometer=new Chronometer(context);
        W=(Math.PI/180);
        bitmap=Bitmap.createBitmap(3000,2000, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintend.setColor(Color.BLUE);
        paintend.setTextSize(30);
        paintend.setStrokeWidth(1);
        if(isresetWH){
            SH= getHeight();
            SW=getWidth();
            isresetWH=false;
        }
        drawXY();
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    public void drawpointLine(ArrayList<Double> a, ArrayList<Double> c){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(SW,SH, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        drawXYCore("t/s","8*DB/dB", 60,110);
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(5);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for (int i=0;i<a.size()-1;i++){
            float xx=a.get(i).floatValue()+40;
            float yy=SH-40-c.get(i).floatValue();
            float xxx=a.get(i+1).floatValue()+40;
            float yyy=SH-40-c.get(i+1).floatValue();
            canvasx.drawLine(xx,yy,xxx,yyy,painta);
        }
        invalidate();
    }


    public void drawPointHigh(ArrayList<Double> a, ArrayList<Double> c){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(SW,SH, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(5);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for (int i=0;i<a.size();i++){
            float xx=a.get(i).floatValue()+40;
            float yy=SH-40;
            float xxx=xx;
            float yyy=SH-40-c.get(i).floatValue();
            canvasx.drawLine(xx,yy,xxx,yyy,painta);
        }
        drawXYCore("t/s","8*DB/dB", 60,110);
        invalidate();
    }

    public void drawPointHighforpp(ArrayList<Float> a, ArrayList<Float> c){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(3000,2000, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        Float maxa=Collections.max(a);
        Float maxc=Collections.max(c);
        Log.e("dyx","maxa is :"+maxa);
        Log.e("dyx","maxc is :"+maxc);
        drawXYCore("f/Hz","A/m", maxa,maxc);
        Paint painta=new Paint();
        painta.setColor(Color.rgb(0, 128, 255));
        painta.setStrokeWidth(5);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for (int i=0;i<a.size();i++){
            float xx=getrealxlocation(a.get(i).floatValue());
            float yy=SH-40;
            float xxx=xx;
            float yyy=getrealylocation(c.get(i).floatValue());
            canvasx.drawLine(xx,yy,xxx,yyy,painta);
        }
        drawXYCore("f/Hz","A/m", maxa,maxc);
        canvasx.drawText(String.valueOf(maxa.intValue()),getrealxlocation(maxa.floatValue()),SH-15,paintend);
        canvasx.drawText(String.valueOf(maxc.intValue()),15,getrealylocation(maxc.floatValue()),paintend);
        invalidate();
    }
    public void drawXY(){
        Paint paint=new Paint();
        paint.setTextSize(30);
        paint.setStrokeWidth(1);
        canvasx.drawLine(0+40,SH-40,SW,SH-40, paint);
        canvasx.drawLine(0+40,0,0+40,SH-40, paint);
        if(drawOXY==true){
            canvasx.drawText("X",SW-70,SH-15,paint);
            canvasx.drawText("Y",20,20,paint);
            canvasx.drawText("O",20,SH-20,paint);
        }
        invalidate();
    }


    public void drawXYCore(String xname,String yname,double xw,double yw){
        xcoors=new ArrayList<>();
        ycoors=new ArrayList<>();
        SH= getHeight();
        SW=getWidth();
        this.xw=xw;//x轴量程
        this.yw=yw;//y轴量程
        double minx=xw/(SW-80);//x分度值，即每个像素表示的虚拟值的范围
        double miny=yw/(SH-80);//y分度值，即每个像素表示的虚拟值的范围
        double x2=0;//x轴的起始值（虚假的值）从屏幕左边开始
        double y2=0;//y轴的起始值（虚假的值）
        for(int i=40;i<=SW-40;i++){
            xcoors.add(new coordinate(i,x2));//i表示从屏幕左边开始的值，起始值为40，x2为屏幕左边开始的虚假值起始值为负数
            x2+=minx;
        }for(int j=SH-40;j>=40;j--){
            ycoors.add(new coordinate(j,y2));//i表示从屏幕顶端开始的值，起始为0，y2为从屏幕顶端开始的虚假值 从负数开始
            y2+=miny;
        }
        int k=5;
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        paint.setStrokeWidth(1);
        canvasx.drawLine(0+40,SH-40,SW,SH-40, paint);
        canvasx.drawLine(0+40,0,0+40,SH-40, paint);
        int xn=(SW-80)/100;//横轴要画的点的个数
        int yn=(SH-80)/50;//纵轴要画的点的个数
        double x1=xw/xn;//每个宽度表示的值
        double y1=yw/yn;//每个宽度表示的值
        double xs=0;
        double ys=0;
        for(int i=40;i<=SW-40;i+=100){
            int xt=(int)xs;
            canvasx.drawText(String.valueOf(xt),i,SH-15,paint);
            xs+=x1;
        }
        for(int j=SH-40;j>=40;j-=50) {
            int yt=(int)ys;
            canvasx.drawText(String.valueOf(yt), 15, j, paint);
            ys += y1;
        }
        paint.setColor(Color.RED);
        canvasx.drawText(xname,SW-70,SH-15,paint);
        canvasx.drawText(yname,20,20,paint);
        canvasx.drawText("0",20,SH-15,paint);
    }


    public void drawXYs(String xname,String yname,double xw,double yw){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(SW,SH,Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        drawXYCore(xname,yname,xw,yw);
        invalidate();
    }

    float getrealxlocation(float xvertical) {
        float xreal=xcoors.get(xcoors.size()-1).real;
        for (int k = 0; k < xcoors.size() - 1; k++) {
            if ((xvertical >= xcoors.get(k).virtual)&&(xvertical< xcoors.get(k + 1).virtual)) {
                xreal=xcoors.get(k).real;
                if (Math.abs(xvertical - xcoors.get(k).virtual) > Math.abs(xvertical - xcoors.get(k + 1).virtual))
                    xreal = xcoors.get(k + 1).real;
                break;
            }
        }
        return xreal;
    }
    float getrealylocation(float yvertical) {
        float yreal=ycoors.get(ycoors.size() - 1).real;
        for (int k = 0; k < ycoors.size() - 1; k++) {
            if ((yvertical >= ycoors.get(k).virtual)&&(yvertical< ycoors.get(k + 1).virtual)) {
                yreal=ycoors.get(k).real;
                if (Math.abs(yvertical - ycoors.get(k).virtual) > Math.abs(yvertical - ycoors.get(k + 1).virtual))
                    yreal = ycoors.get(k + 1).real;
                break;
            }
        }
        return yreal;
    }

    float getx(double vertical){
        Double x=(vertical-minx)/scaylex;
        return x.floatValue();
    }
    float gety(double vertical){
        Double y=(vertical-miny)/scayley;
        return y.floatValue();
    }
}

