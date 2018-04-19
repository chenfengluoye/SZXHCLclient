package com.qqjyb.szxhcl;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

/**
 * Created by chenkaiju on 2017/10/17.
 */

public class FloatArrylist<M> extends ArrayList<myfloat> {
    @JavascriptInterface
    @Override
    public int size() {
        return super.size();
    }

    @ JavascriptInterface
    @Override
    public myfloat get(int index) {
        return super.get(index);
    }
    @ JavascriptInterface
    public void getsindatafromjs(String str){
//        data.siny=a;
        Log.e("dyx",str+"OK，I AM HERE.......................sfjls");
    }
}
