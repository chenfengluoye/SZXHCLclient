package com.qqjyb.szxhcl;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

/**
 * Created by chenkaiju on 2017/10/17.
 */

public class MyArrayList<Z> extends ArrayList<Object> {
    @ JavascriptInterface
    @Override
    public int size() {
        return super.size();
    }

    @ JavascriptInterface
    @Override
    public Z get(int index) {
        return (Z)super.get(index);
    }

    @ JavascriptInterface
    public void a(String c)
    {
        Log.e("dyx",c);
    }
}
