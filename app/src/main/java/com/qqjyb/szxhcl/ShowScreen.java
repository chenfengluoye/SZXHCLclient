package com.qqjyb.szxhcl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.text.NumberFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.sin;

public class ShowScreen extends View {
    Paint paint=new Paint();
    double t=0;
    double y=0;
    int SW;
    int SH;
    int T;
    int F;
    int A=1;
    double bb=0;
    double fi=0;
    double W;
    double xw=500;
    double yw=300;
    Bitmap bitmap;
    Canvas canvasx=new Canvas();
    Chronometer chronometer;
    List<coordinate> xcoors=new ArrayList<>();
    List<coordinate> ycoors=new ArrayList<>();
    public ShowScreen(Context context) {
        super(context);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        chronometer=new Chronometer(context);
        W=(Math.PI/180);
        SH= getHeight();
        SW=getWidth();
    }

    public ShowScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        chronometer=new Chronometer(context);
        W=(Math.PI/180);
        SH= getHeight();
        SW=getWidth();
        bitmap=Bitmap.createBitmap(1300,2000, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY();
        canvas.drawBitmap(bitmap,0,0,paint);
    }
    public void drawPointline(ArrayList<Double> a, ArrayList<Double>c){
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(20);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for (int i=0;i<a.size()-1;i++){
            double xx=a.get(i);
            double yy=c.get(i);
            double xxx=a.get(i+1);
            double yyy=c.get(i+1);
            double xx1=getrealxlocation(xx);
            double yy1=getrealylocation(yy);
            double xxx1=getrealxlocation(xxx);
            double yyy1=getrealylocation(yyy);
            canvasx.drawLine((float) xx1,(float)yy1,(float)xxx1,(float)yyy1,painta);
        }
        invalidate();
    }

    public void drawPointHigh(ArrayList<Double> a, ArrayList<Double>c){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(1300,2000, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(20);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for (int i=0;i<a.size();i++){
            double xx=a.get(i);
            double yy=SH/2;
            double xxx=a.get(i);
            double yyy=SH/2-c.get(i);
            canvasx.drawLine((float)xx,(float)yy,(float)xxx,(float)yyy,painta);
        }
        invalidate();
    }

    public void drawMutlPoints(ArrayList<Double>a,ArrayList<Double>b){
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(20);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for(int i=0;i<a.size();i++){
            double x=  a.get(i)+SW/2;
            double y=SH/2-b.get(i);
            canvasx.drawPoint((float)x,(float)y,painta);
        }
        invalidate();
    }

    public void drawMutlPoints(ArrayList<zhuobiao>a){
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(20);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for(int i=0;i<a.size();i++){
            double x=  a.get(i).x+SW/2;
            double y=SH/2-a.get(i).y;
            canvasx.drawPoint((float)x,(float)y,painta);
        }
        invalidate();
    }

    public  void drawSins(List<Double> frequeces){
        double t=0;
        double y=0;
        Paint painta=new Paint();
        painta.setColor(Color.GREEN);
        painta.setStrokeWidth(5);
        painta.setAntiAlias(true);
        painta.setStrokeCap(Paint.Cap.ROUND);
        painta.setStrokeJoin(Paint.Join.ROUND);
        for(t=-xw/2;t<xw/2;t+=0.1){
            y=0;
            for(int i=0;i<frequeces.size();i++){
                y+=100*sin(frequeces.get(i)*t);
            }
            double xx= (double) (t);
            xx=getrealxlocation(xx);
            double yy= (double) y;
            yy=getrealylocation(yy);
            canvasx.drawPoint((float)xx, (float)yy, painta);
        }
        invalidate();
    }

    public void drawSin(){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(1300,2000, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        drawXY();
        t=-2*SW;
        while (t<2*SW){
            y = A * 200 * sin(W * (t+fi))+bb;
            double xx= (double) (t-fi+SW/2);
            double yy= (double) (SH/2-y);
            canvasx.drawPoint((float)xx, (float)yy, paint);
            t+=0.1;
        }
        invalidate();
    }

    public void drawXYs(String xname,String yname,double xw,double yw){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap=Bitmap.createBitmap(1300,2000, Bitmap.Config.ARGB_8888);
        canvasx.setBitmap(bitmap);
        xcoors=new ArrayList<>();
        ycoors=new ArrayList<>();
        SH= getHeight();
        SW=getWidth();
        this.xw=xw;
        this.yw=yw;
        double minx=xw/SW;
        double miny=yw/SH;
        double x2=-xw/2.0;//x轴的起始值（虚假的值）从屏幕左边开始
        double y2=-yw/2.0;//y轴的起始值（虚假的值）
        for(int i=0;i<=SW;i++){
            xcoors.add(new coordinate(i,x2));//i表示从屏幕左边开始的值，起始值为0，x2为屏幕左边开始的虚假值起始值为负数
            x2+=minx;
        }for(int i=SH;i>=0;i--){
            ycoors.add(new coordinate(i,y2));//i表示从屏幕顶端开始的值，起始为0，y2为从屏幕顶端开始的虚假值 从负数开始
            y2+=miny;
        }
        int k=5;
        Paint paint=new Paint();
        paint.setTextSize(30);
        paint.setStrokeWidth(1);
        canvasx.drawLine(0,SH/2,SW,SH/2, paint);
        canvasx.drawLine(SW/2,0,SW/2,SH, paint);
        canvasx.drawText(xname,SW-40,SH/2+40,paint);
        canvasx.drawText(yname,SW/2-40,40,paint);
        int xn=SW/100;
        int yn=SH/50;
        double x1=xw/xn;
        double y1=yw/yn;
        double xs=-xw/2.0;
        double ys=-yw/2.0;
        for(int i=-SW/2;i<SW/2-80;i+=100){
            NumberFormat ddf1=NumberFormat.getNumberInstance() ;
            double xss=Math.abs(xs);
            if(xss<0){
                ddf1.setMaximumFractionDigits(4);
            }else if(xss<10){
                ddf1.setMaximumFractionDigits(3);
            }else if(xss<100){
                ddf1.setMaximumFractionDigits(2);
            }else if(xss<1000){
                ddf1.setMaximumFractionDigits(1);
            }else{
                ddf1.setMaximumFractionDigits(0);
            }
            String x0= ddf1.format(xs) ;
            canvasx.drawText(x0,SW/2+i,SH/2+40,paint);
            xs+=x1;
        }
        for(int j=-SH/2;j<SH/2-50;j+=50){
            NumberFormat ddf1=NumberFormat.getNumberInstance() ;
            double yss=Math.abs(ys);
            if(yss<0){
                ddf1.setMaximumFractionDigits(4);
            }else if(yss<10){
                ddf1.setMaximumFractionDigits(3);
            }else if(yss<100){
                ddf1.setMaximumFractionDigits(2);
            }else if(yss<1000){
                ddf1.setMaximumFractionDigits(1);
            }else{
                ddf1.setMaximumFractionDigits(0);
            }
            String y0= ddf1.format(ys) ;
            canvasx.drawText(y0,SW/2-60,SH/2-j,paint);
            ys+=y1;
        }
        invalidate();
    }
    public void drawXY(){
        SH= getHeight();
        SW=getWidth();
        int k=5;
        String xxs;
        String yys;
        Paint paint=new Paint();
        paint.setTextSize(30);
        paint.setStrokeWidth(1);
        canvasx.drawLine(0,SH/2,SW,SH/2, paint);
        canvasx.drawLine(SW/2,0,SW/2,SH, paint);
        canvasx.drawText("X",SW-40,SH/2+40,paint);
        canvasx.drawText("Y",SW/2-40,40,paint);
        canvasx.drawText("O",SW/2-40,SH/2+40,paint);
        invalidate();
    }
//    public void drawAngel(){
//        data.Xx=new ArrayList<>();
//        data.Yy=new ArrayList<>();
//        bb=0;
//        A=1;
////        t=-2*SW;
//        int i=0;
//        double xx;
//        double yy;
//        for(int t=-2*SW;t<2*SW;t+=80) {
//            if (i %2 == 0) {
//                xx=t+fi;
//                y=300;
//                yy= (double) (y+bb);
//                data.Xx.add(xx);
//                data.Yy.add((double) yy);
//            }
//            else {
//                xx=t+fi;
//                y=-300;
//                yy= (double) (y+bb);
//                data.Xx.add((double) xx);
//                data.Yy.add((double) yy);
//            }
//            i++;
//        }
//    }
//    public  void drawSqure(){
//        data.Xx=new ArrayList<>();
//        data.Yy=new ArrayList<>();
//        bb=0;
//        A=1;
////        t=-2*SW;
//        int i=0;
//        double xx;
//        double yy;
//        for(int t=-2*SW;t<2*SW;t+=80) {
//            if(i==0){
//                t=t-80;
//                xx=t+fi;
//                yy=300+bb;
//                data.Xx.add(xx);
//                data.Yy.add(yy);
//            }
//
//            if(i==1)
//            {
//                xx=t+fi;
//                yy=300+bb;
//                data.Xx.add(xx);
//                data.Yy.add(yy);
//            }
//            if(i==2){
//                t=t-80;
//                xx=t+fi;
//                yy=-300+bb;
//                data.Xx.add(xx);
//                data.Yy.add(yy);
//            }
//            if(i==3)
//            {
//                xx=t+fi;
//                yy=-300+bb;
//                data.Xx.add(xx);
//                data.Yy.add(yy);
//            }
//            i++;
//            if(i==4){
//                i=0;
//            }
//        }
//    }

    double getrealxlocation(double xvertical) {
        double xreal=-20;
        for (int k = 0; k < xcoors.size() - 1; k++) {
            if ((xvertical >= xcoors.get(k).virtual)&&(xvertical< xcoors.get(k + 1).virtual)) {
                xreal=xcoors.get(k).real;
                if (Math.abs(xreal - xcoors.get(k).virtual) > Math.abs(xreal - xcoors.get(k + 1).virtual))
                    xreal = xcoors.get(k + 1).real;
                break;
            }
        }
        return xreal;
    }

    double getrealylocation(double yvertical) {
        double yreal=-20;
        for (int k = 0; k < ycoors.size() - 1; k++) {
            if ((yvertical >= ycoors.get(k).virtual)&&(yvertical< ycoors.get(k + 1).virtual)) {
                yreal=ycoors.get(k).real;
                if (Math.abs(yreal - ycoors.get(k).virtual) > Math.abs(yreal - ycoors.get(k + 1).virtual))
                    yreal = ycoors.get(k + 1).real;
                break;
            }
        }
        return yreal;
    }

}
