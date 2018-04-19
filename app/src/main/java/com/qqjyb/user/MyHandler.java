package com.qqjyb.user;

import android.os.Handler;

import java.util.Hashtable;

/**
 * Created by chengkaiju on 2018/4/6.
 */

public class MyHandler extends Handler{
    static Hashtable<String,MyHandler> handlers=new Hashtable();

    String name;
    public MyHandler(String name){
        super();
        this.name=name;
    }
}
