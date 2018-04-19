package com.qqjyb.szxhcl;

import android.webkit.JavascriptInterface;

/**
 * Created by chenkaiju on 2017/10/16.
 */

public class zhuobiao {
    public float x=0;
    public float y=0;
    zhuobiao(float x,float y){
        this.x=x;
        this.y=y;
    }
    @ JavascriptInterface
    public float getX() {
        return x;
    }
    @ JavascriptInterface
    public float getY() {
        return y;
    }
}
