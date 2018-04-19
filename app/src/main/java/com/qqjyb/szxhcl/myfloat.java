package com.qqjyb.szxhcl;

import android.webkit.JavascriptInterface;

/**
 * Created by chenkaiju on 2017/10/17.
 */

public class myfloat {
    public double number;
    myfloat(double f) {
        this.number = f;
    }
    @JavascriptInterface
    public double getNumber() {
        return number;
    }
}
