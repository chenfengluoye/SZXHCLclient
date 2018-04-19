package com.qqjyb.szxhcl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SY1 extends AppCompatActivity implements View.OnClickListener {
    MyWebView webView;
    Button back;
    Button go;
    Button start;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy1);
        webView=(MyWebView)findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/sy1.html");
        back=(Button)findViewById(R.id.back);
        go=(Button)findViewById(R.id.go);
        start=(Button)findViewById(R.id.start);
        back.setOnClickListener(this);
        go.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String temp= item.getTitle().toString();
        if(temp.equals("开始实验")) {
            Intent intent = new Intent(SY1.this, SineApply.class);
            startActivity(intent);
        }
        return true;
    }

    public void goaback(){
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:goback()");
            }
        });
    }

    public void goahead(){
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:goahead()");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.go){
            goahead();
        }else if(id==R.id.back){
            goaback();
        }else if(id==R.id.start){
            Intent intent = new Intent(SY1.this, SineApply.class);
            startActivity(intent);
        }
    }
}
