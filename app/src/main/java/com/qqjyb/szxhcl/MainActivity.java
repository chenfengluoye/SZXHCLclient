package com.qqjyb.szxhcl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyWebView webView;
    FrameLayout frameLayout;
    ViewPager viewPager;
    TextView texttitle;
    List<View> viewList=new ArrayList<>();
    String [] syml=new String[]{"正余弦信号的谱分析","数字滤波器的设计及实现","语音信号滤波处理","调制解调系统的设计及实现","倒频系统的软件实现及应用"};
    static String pemission[]= new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout=(FrameLayout)findViewById(R.id.model2);
        webView=(MyWebView)findViewById(R.id.webView);
        texttitle=(TextView)findViewById(R.id.texttitle);
//
        webView.loadUrl("file:///android_asset/main.html");
        webView.addJavascriptInterface(MainActivity.this,"mainactivity");
        for(int i=0;i<pemission.length;i++){
            if(ContextCompat.checkSelfPermission(MainActivity.this, pemission[i])!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{pemission[i]},1);
            }
        }
        ImageView view1=new ImageView(this);
        view1.setImageBitmap(ImageUtils.getReverseBitmapById(MainActivity.this,R.drawable.zyx1,0.5f));
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsy1();
            }
        });
        ImageView view2=new ImageView(this);
        view2.setImageBitmap(ImageUtils.getReverseBitmapById(MainActivity.this,R.drawable.lbq1,0.5f));
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsy2();
            }
        });
        ImageView view3=new ImageView(this);
        view3.setImageBitmap(ImageUtils.getReverseBitmapById(MainActivity.this,R.drawable.syxh1,0.5f));
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsy3();
            }
        });
        ImageView view4=new ImageView(this);
        view4.setImageBitmap(ImageUtils.getReverseBitmapById(MainActivity.this,R.drawable.tzjtq,0.5f));
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsy4();
            }
        });
        ImageView view5=new ImageView(this);
        view5.setImageBitmap(ImageUtils.getReverseBitmapById(MainActivity.this,R.drawable.dpxt,0.5f));
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsy5();
            }
        });
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
//        viewList.add(view4);
//        viewList.add(view5);
        MyViewPageAdpter adpter=new MyViewPageAdpter(viewList);
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.color);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adpter);
        colorAnimationView.setmViewPager(viewPager, viewList.size());
        viewPager.setPageTransformer(true,new GalleryPageTransformer());
        colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                texttitle.setText(syml[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @JavascriptInterface
    public  void startsy1(){
        Intent intent=new Intent(MainActivity.this,SY1.class);
        startActivity(intent);
    }
    @ JavascriptInterface
    public void startsy2(){
        Intent intent=new Intent(MainActivity.this,SY2.class);
        startActivity(intent);
    }
    @ JavascriptInterface
    public void startsy3(){
        Intent intent=new Intent(MainActivity.this,SY3.class);
        startActivity(intent);
    }
    @ JavascriptInterface
    public void startsy4(){
        Intent intent=new Intent(MainActivity.this,SY4.class);
        startActivity(intent);
    }
    @ JavascriptInterface
    public void startsy5(){
        Intent intent=new Intent(MainActivity.this,SY5.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mains,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(frameLayout.getVisibility()==View.VISIBLE){
            frameLayout.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }else {
            webView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }
        return true;
    }
}
