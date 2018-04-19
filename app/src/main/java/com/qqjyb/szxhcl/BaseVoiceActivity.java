package com.qqjyb.szxhcl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.widget.LoadingView;
import com.qqjyb.user.ClassAdapter;
import com.qqjyb.user.MyHandler;
import com.qqjyb.user.Net;
import com.qqjyb.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class BaseVoiceActivity extends AppCompatActivity implements View.OnClickListener {

    View classView;
    ListView classlistview;
    JSONArray classlist;
    ClassAdapter classAdapter;
    AlertDialog.Builder classbuilder;
    AlertDialog classDialog;
    LoadingView loading;

    Button ksly;
    Button tzly;
    Button bfly;
    Button tzbf;
    Button plfx;
    Button timeara;
    Button plarea;
    LinearLayout timearas;
    LinearLayout plareas;
    EditText numb;
    TextView savenumb;
    TextView upload;
    TextView cacelnumb;
    AlertDialog dialog;
    Button srctimeresult;
    TextView plDialog;
    TextView syts;
    TextView syorder;
    TextView syset;
    Button fxnumb;
    StringBuilder srcresultbuider=new StringBuilder();
    StringBuilder resultStringBuilder=new StringBuilder();
    static ShowScreens plshow;
    static int N=32;
    static int maxn=5000;
    View setnumbview;
    static  DrawView drawView;
    getWave wave;
    static int me=0;
    PlayAudioer playAudioer;
    ArrayList<Float> plresult=new ArrayList<>();
    static ArrayList<Float> frequece=new ArrayList<>();
    static ArrayList<Float> amplitude=new ArrayList<>();
    DecimalFormat df=new DecimalFormat("#.###");
    ArrayList<String> srcTimeStr=new ArrayList<>();
    ArrayList<String> srcRateStr=new ArrayList<>();

   public Handler recodhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                syts.setText("文件创建失败");
            }
            else if(msg.what==1){
                syts.setText("录音中。。。");
            }
            else if (msg.what==2){
                drawView.clearView();
                drawView.drawPointDoubleHight(data.Xxs,data.Yys);
                drawView.drawOXY();
                drawView.invalidate();
            }
            else if(msg.what==3){
                syts.setText("录音完毕");
                for(int i=0;i<data.Yys.size();i++){
                    String f1=df.format(data.Yys.get(i));
                    String a1=df.format(data.Xxs.get(i));
//                    double f1=new BigDecimal(data.Yys.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                    double a1=new BigDecimal(data.Xxs.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                    String re=f1+"#"+a1;
                    srcTimeStr.add(re);
                }
            }
        }
    };
    Handler playhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1||msg.what==2){
                syts.setText("失败，缺少音频文件");
                Toast.makeText(BaseVoiceActivity.this,"文件不存在，请先录取音频信号,或者检查权限设置",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==3) {
                drawView.clearView();
                drawView.drawPointDoubleHight(data.Xxss,data.Yyss);
                drawView.drawOXY();
                drawView.invalidate();
            }
            else if(msg.what==4){
                syts.setText("异常");
                Toast.makeText(BaseVoiceActivity.this, "播放异常，请检查读取文件权限设置", Toast.LENGTH_SHORT).show();
            }else if(msg.what==5){
                syts.setText("播放已停止");
            }else if(msg.what==6){
                syts.setText("播放中。。。");
            }
        }
    };

    MyHandler uploadvoice=new MyHandler("uploadvoice"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;

                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(BaseVoiceActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(BaseVoiceActivity.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(BaseVoiceActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);
        }
    };
    Handler loadclassHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    classlist=new JSONArray(result);
                    classAdapter=new ClassAdapter(R.layout.classitem,classlist);
                    classlistview.setAdapter(classAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(BaseVoiceActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        setContentView(R.layout.activity_base_voice);
        classView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.chooseclass,null);
        classlistview=(ListView)classView.findViewById(R.id.choose);
        loading=(LoadingView)classView.findViewById(R.id.loading);
        classbuilder=new AlertDialog.Builder(this);
        classbuilder.setView(classView);
        classDialog=classbuilder.create();


        setnumbview= LayoutInflater.from(getApplicationContext()).inflate(R.layout.fxsetlayout,null);
        numb=(EditText)setnumbview.findViewById(R.id.numb);
        numb.setText(String.valueOf(N));
        savenumb=(TextView)setnumbview.findViewById(R.id.savenumb);
        cacelnumb=(TextView)setnumbview.findViewById(R.id.cacelnumb);
        ksly=(Button)findViewById(R.id.ksly);
        tzly=(Button)findViewById(R.id.tzly);
        timeara=(Button)findViewById(R.id.timearea);
        srctimeresult=(Button)findViewById(R.id.srctimeresult);
        plarea=(Button)findViewById(R.id.plarea);
        drawView=(DrawView)findViewById(R.id.drawview);
        timearas=(LinearLayout)findViewById(R.id.timeareas) ;
        plareas=(LinearLayout)findViewById(R.id.plareas);
        plshow=(ShowScreens)findViewById(R.id.plshow);
        bfly=(Button)findViewById(R.id.bfly);
        plfx=(Button)findViewById(R.id.plfx);
        plDialog=(TextView)findViewById(R.id.pllist);
        syorder=(TextView)findViewById(R.id.shyyanorder);
        syset=(TextView)findViewById(R.id.shyyanset);
        syts=(TextView)findViewById(R.id.shyyants);
        upload=(TextView)findViewById(R.id.upload);
        fxnumb=(Button) findViewById(R.id.fxnumb) ;
        tzbf=(Button)findViewById(R.id.tzbf);
        tzbf.setOnClickListener(this);
        syset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BaseVoiceActivity.this,RecodSet.class);
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder=new AlertDialog.Builder(this);
        TextView title= new TextView(this);
        title.setBackgroundColor(0xff3B99FF);
        title.setTextSize(30);
        title.setTextColor(0xffffffff);
        title.setText("分析设置");
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setView(setnumbview);
        dialog=builder.create();
        timeara.setOnClickListener(this);
        plarea.setOnClickListener(this);
        plDialog.setOnClickListener(this);
        ksly.setOnClickListener(this);
        tzly.setOnClickListener(this);
        bfly.setOnClickListener(this);
        plfx.setOnClickListener(this);
        upload.setOnClickListener(this);
        tzly.setOnClickListener(this);
        srctimeresult.setOnClickListener(this);
        fxnumb.setOnClickListener(this);
        savenumb.setOnClickListener(this);
        cacelnumb.setOnClickListener(this);
        playAudioer=new PlayAudioer(this);
        wave=new getWave(this);
        for(int i=0;i<MainActivity.pemission.length;i++){
            if(ContextCompat.checkSelfPermission(this, MainActivity.pemission[i])!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{MainActivity.pemission[i]},1);
            }
        }
        classlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classid= null;
                try {
                    classid =classlist.getJSONObject(position).optString("classId");
                    loading.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dataupload(classid);
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.tzly){
           wave.stopRecording();
        }else if(id==R.id.ksly){
            srcRateStr=new ArrayList<>();
            srcTimeStr=new ArrayList<>();
            drawView.drawxy=false;
            drawView.init(0,drawView.SW,-2000,2000);
            wave.startRecord();
        }else if(id==R.id.fxnumb){
            dialog.show();
        }else if(id==R.id.srctimeresult){
            srcresultbuider=new StringBuilder();
            for(int i=0;i<data.Yys.size();i++){
                String f1=df.format(data.Yys.get(i));
                String a1=df.format(data.Xxs.get(i));
                String re=f1+"#"+a1;
//                double f1=new BigDecimal(data.Yys.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double a1=new BigDecimal(data.Xxs.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                srcresultbuider.append(re+"\n");
            }
            showDialog("振幅/时间",srcresultbuider);
        }else if(id==R.id.pllist){
            showDialog("频率/振幅",resultStringBuilder);
        }else if(id==R.id.bfly){
            drawView.drawxy=false;
            playAudioer.playAudio(playhandler,getWave.mSampleFile);
        }else if(id==R.id.tzbf){
            playAudioer.stopAudio(playhandler);
        }else if(id==R.id.plfx){
            analysefft();
        }else if(v.getId()==R.id.savenumb){
            N=Integer.valueOf(numb.getText().toString());
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            dialog.hide();
        }else if(v.getId()==R.id.cacelnumb){
            dialog.hide();
        }else if(v.getId()==R.id.timearea){
            drawView.drawxy=false;
            plareas.setVisibility(View.GONE);
            timearas.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.plarea){
            drawView.drawxy=false;
            timearas.setVisibility(View.GONE);
            plareas.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.upload){
            if(!User.myself.isloging){
                Toast.makeText(BaseVoiceActivity.this,"请先登录，在进行此操作",Toast.LENGTH_SHORT).show();
                return;
            }
            if(srcTimeStr.size()==0||srcTimeStr.size()==0){
                Toast.makeText(BaseVoiceActivity.this,"请先完成实验",Toast.LENGTH_SHORT).show();
                return;
            }
            classDialog.show();
            loadClss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lyset,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name=item.getTitle().toString();
        if(name.equals("设置")){
            Intent intent=new Intent(BaseVoiceActivity.this,RecodSet.class);
            startActivity(intent);
        }
        return true;
    }

    void showDialog(String ti,StringBuilder buid){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setBackgroundColor(0xff3B99FF);
        title.setTextSize(30);
        title.setTextColor(0xffffffff);
        title.setText(ti);
        title.setGravity(Gravity.CENTER);
        EditText text=new EditText(this);
        text.setBackgroundColor(0x00ffffff);
        text.setGravity(Gravity.CENTER);
        text.setText(buid.toString());
        builder.setCustomTitle(title);
        builder.setView(text);
        builder.show();
    }

    public void analysefft(){
        srcRateStr=new ArrayList<>();
        if(data.Yy.size()<=0){
            Toast.makeText(BaseVoiceActivity.this,"请先获取数据源",Toast.LENGTH_SHORT).show();
            return;
        }

        for(int x=0;x<=20;x++){
            if((Math.pow(2,x))<=getWave.Numb&&getWave.Numb<(Math.pow(2,x+1))){
                me=x;
                break;
            }
        }
        Complexs[]temp=new Complexs[(int) Math.pow(2,me)];
        for(int i=0;i<temp.length;i++){
            temp[i]=new Complexs(data.Yy.get(i),0);
        }
        Complexs[] result= Complexs.fft(temp);
        ArrayList<Float>f=new ArrayList<>();
        plresult=new ArrayList<>();
        for(int k=0;k<result.length;k++){
            f.add((float)getWave.frequecerate*k);
            plresult.add((float)result[k].getMod()/getWave.Numb);
        }
        Log.e("dyx","Collections.max(plresult):"+ Collections.max(plresult));
        plshow.drawOXY=false;

        plshow.drawPointHighforpp(f,plresult);
        resultStringBuilder=new StringBuilder();

        for(int i=0;i<plresult.size();i++){
            String f1=df.format(f.get(i));
            String a1=df.format(plresult.get(i));
//            double f1=new BigDecimal(f.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//            double a1=new BigDecimal(plresult.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
            String re=f1+"#"+a1;
            srcRateStr.add(re);
            resultStringBuilder.append(re+"\n");
        }
    }


    public void dataupload(String classId){
        try{

            JSONObject data=new JSONObject();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sytime=format.format(new Date());
            data.put("实验时间",sytime);
            data.put("实验名称","语音信号的滤波处理");
            data.put("bltstuId", User.myself.admin);
            data.put("bltclassId",classId);//此处待确定
            data.put("采样频率",getWave.SAMPLE_RATE);
            data.put("间隔时间",getWave.jgtime);
            data.put("采样点数",getWave.Numb);
            data.put("采样时长",getWave.alltime);
            data.put("频率分辨率",getWave.frequecerate);
            data.put("原始时域",srcTimeStr);
            data.put("目标频域",srcRateStr);
            Net.connect(Net.host+"/szxhcl/addsy3data",data.toString(),uploadvoice);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadClss(){
        loading.setVisibility(View.VISIBLE);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",User.myself.admin);
            Net.connect(Net.host+"/szxhcl/getclassbyadmin",jsonObject.toString(),loadclassHandler);
        } catch (JSONException e) {
            loading.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

}
