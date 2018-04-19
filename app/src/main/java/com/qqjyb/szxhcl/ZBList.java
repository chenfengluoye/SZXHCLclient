package com.qqjyb.szxhcl;

import android.webkit.JavascriptInterface;

import java.util.ArrayList;

/**
 * Created by chenkaiju on 2017/10/18.
 */

public class ZBList<N> extends ArrayList<zhuobiao> {
    @JavascriptInterface
    @Override
    public int size() {
        return super.size();
    }

    @ JavascriptInterface
    @Override
    public zhuobiao get(int index) {
        return super.get(index);
    }
}
