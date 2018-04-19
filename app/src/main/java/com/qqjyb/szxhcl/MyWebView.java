package com.qqjyb.szxhcl;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by chenkaiju on 2017/10/17.
 */

public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
        startset();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        startset();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startset();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startset();
    }

    public void startset(){
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        //支持App内部javascript交互
        settings.setDefaultTextEncodingName("utf-8"); //设置文本编码
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDisplayZoomControls(false);
        //允许webview对文件的操作
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        this.setHorizontalScrollBarEnabled(false);
        this.setVerticalScrollBarEnabled(false);
        this.setWebChromeClient(new WebChromeClient());
        this.setWebViewClient(new WebViewClient());
//        this.loadUrl("file:///android_asset/show.html");
    }
}
