package com.qqjyb.szxhcl;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenkaiju on 2017/8/28.
 */

public class data {
   static List<symlitem> symlitemList=new ArrayList<>();
   static  String sylr[]=new String[14];

    public   int order=0;
    @JavascriptInterface
    public int getOrder() {
        return order;
    }

    //本组为实时录音获取的数据值,需要画在屏幕上的值
   static ArrayList<Double>Xx=new ArrayList<>();
   static ArrayList<Double>Yy=new ArrayList<>();


    //本组为需要进行傅里叶计算的值，即等间隔采样的数据，正玹信号值
    ArrayList<Float>sinx=new ArrayList<>();
    ArrayList<Float>siny=new ArrayList<>();

    @JavascriptInterface
    public double getsin(int i){
        return siny.get(i);
    }
    @JavascriptInterface
    public int getsize(){
        return siny.size();
    }

    //本组为实时录音获取的数据值,需要画在屏幕上的值
    static ArrayList<Double>sinxShow=new ArrayList<>();
    static ArrayList<Double>sinyShow=new ArrayList<>();


    ArrayList<zhuobiao> sin=new ArrayList<>();

    @JavascriptInterface
    public double getsinY(int i){
        return sin.get(i).y;
    }

    @ JavascriptInterface
    public double getsinX(int i){
        return sin.get(i).x;
    }

    @ JavascriptInterface
    public int getsinsize(){
        return sin.size();
    }
    @ JavascriptInterface
    public void debug(String s){
        Log.e("dyx",s);
    }
    //本组为需要进行傅里叶计算的值，即等间隔采样的数据
    static ArrayList<Float> Xxs=new ArrayList<>();
    static ArrayList<Float> Yys=new ArrayList<>();

    //本组为实时播放获取的数据值,需要画在屏幕上的值
    static ArrayList<Float> Xxss=new ArrayList<>();
    static ArrayList<Float> Yyss=new ArrayList<>();


}
