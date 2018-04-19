package com.qqjyb.szxhcl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.sin;

/**
 * Created by chengkaiju on 2018/4/8.
 */

public class FirData {

    static  boolean isgetdata;

    //采样点数，必须为2的整数次幂
    static int Numb=32;
    //输入的最大频率
    static double maxfrequece=100;
    //采样总时间
    static double alltime=0.16;
    //采样间隔时间
    static double jgtime=0.005;
    //频率分辨率F
    static double frequecerate=4;
    //采样频率
    static double samplerate=200;
    //一系列的频率值
    static ArrayList<Double> frequeces=new ArrayList<>();
    //一系列的振幅值
    static ArrayList<Double>amplitudes=new ArrayList<>();


    //初始化设置，需要人为输入一系类频率值和对应的振幅值，同时确定取样个数
    static  void startset(){

        //总的采样时间,值为所给频率分辨率的倒数


        //从输入的一系列频率值中获取最大的频率值
        maxfrequece= Collections.max(frequeces);

        //采样频率为所有输入频率中最大频率值的30倍，保证信号不失真
        samplerate=30*maxfrequece;

        //采样时间间隔，即每隔多少秒采一个点，该值等于采样频率的倒数
        jgtime=1.0/samplerate;

        alltime=jgtime*Numb;

         frequecerate=1.0/alltime;


    }



    static List<zhuobiao> getSinSingnal(){
        startset();
        List<zhuobiao> temp=new ArrayList<>();
        isgetdata=true;
        SineApply.mydata.siny.removeAll(SineApply.mydata.siny);
        FIRLvBoQi.time=new ArrayList<>();
        //t为时间值，从零时刻开始取样
        double t=0;
        int numb=0;
        //每隔一定的时间取样，该值等于采样频率的倒数
        while (numb<Numb&&isgetdata==true){
            double y=0;
            for(int i=0;i<frequeces.size();i++){
                double f=frequeces.get(i);
                double A=amplitudes.get(i);
                y+=A* sin(2*Math.PI*f*t);
            }
            temp.add(new zhuobiao((float) t,(float) y));
            FIRLvBoQi.time.add((float) t);
            t+=jgtime; //每隔jgtime秒采一个点
            numb++;
        }
        return temp;
    }

    //停止获取信号源
    static void stopsindata(){
        isgetdata=false;
    }


}
