package com.qqjyb.szxhcl;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class SineApply extends AppCompatActivity implements View.OnClickListener {
    View classView;
    ListView classlistview;
    JSONArray classlist;
    ClassAdapter classAdapter;
    AlertDialog.Builder classbuilder;
    AlertDialog classDialog;
    LoadingView loading;
    //time为正弦信号的采样间隔
    static double time=1;
    //N为采样个数
    static int N=32;
    View setnumbview;
    Button apply;
    Button stop;
    Button timeara;
    Button plarea;
    DrawView showsinsrc1;
    LinearLayout timearas;
    LinearLayout plareas;
    View sinset;
    TextView savesinset;
    TextView cancelsinset;
    Button xinhaoyuanset;
    EditText zfxltext;
    EditText plxltext;
    EditText fbltext;
    EditText cypl;
    Button analyse;
    Button fxset;
    Button srcresult;
    EditText numb;
    AutoCompleteTextView method;
    String[] mthods=new String[]{"fft","dft"};
    String usewho="fft";
    TextView savenumb;
    TextView cacelnumb;
    TextView upload;
    Button rates;
    MyWebView myWebView;
    TextView title;
    AlertDialog.Builder builder;
    AlertDialog sinsetdialog;
    AlertDialog dialog;
    StringBuilder dessquaresultstring=new StringBuilder();
    StringBuilder srctimeresultstring=new StringBuilder();
    static data mydata=new data();
    ArrayList<Float> plresult=new ArrayList<>();
    ArrayList<String> desSresult=new ArrayList<>();
    ArrayList<String> srcTresult=new ArrayList<>();
    static ShowScreens showdes;
    Handler drawSinHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            float a=0;
            for(double i:Sin.amplitudes){
                a+=i;
            }
            showsinsrc1.clearView();
            showsinsrc1.init(0,(float)Sin.alltime,-a,a);
            showsinsrc1.drawPointtoLine(mydata.sinx,mydata.siny);
            showsinsrc1.drawOXY();
            showsinsrc1.invalidate();
            srcTresult=new ArrayList<>();
            srctimeresultstring=new StringBuilder();
            DecimalFormat df=new DecimalFormat("#.###");
            for(int i=0;i<mydata.siny.size();i++){
                String f1=df.format(mydata.siny.get(i));
                String a1=df.format(mydata.sinx.get(i));
                String re=f1+"#"+a1;
                srcTresult.add(re);
                srctimeresultstring.append(re+"\n");
            }
        }
    };

    MyHandler uploadsin=new MyHandler("uploadsin"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;

                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(SineApply.this,"操作成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SineApply.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(SineApply.this,"网络错误",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SineApply.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);
        }
    };
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sine_apply);
        getSupportActionBar().hide();
        classView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.chooseclass,null);
        classlistview=(ListView)classView.findViewById(R.id.choose);
        loading=(LoadingView)classView.findViewById(R.id.loading);
        classbuilder=new AlertDialog.Builder(this);
        classbuilder.setView(classView);
        classDialog=classbuilder.create();

        apply=(Button) findViewById(R.id.apply);
        stop=(Button) findViewById(R.id.stop);
        setnumbview=LayoutInflater.from(getApplicationContext()).inflate(R.layout.fxsetlayout,null);
        sinset=LayoutInflater.from(getApplicationContext()).inflate(R.layout.getsindataset,null);
        savesinset=(TextView)sinset.findViewById(R.id.savesinset);
        cancelsinset=(TextView)sinset.findViewById(R.id.cancelsinset);
        fbltext=(EditText)sinset.findViewById(R.id.fbl);
        fbltext.setText(String.valueOf(Sin.Numb));
        zfxltext=(EditText)sinset.findViewById(R.id.zfxl);
        cypl=(EditText)sinset.findViewById(R.id.cypl);
        cypl.setText(String.valueOf(Sin.samplerate));
        timeara=(Button)findViewById(R.id.sy);
        srcresult=(Button)findViewById(R.id.srcresult);
        srcresult.setOnClickListener(this);
        plarea=(Button)findViewById(R.id.py);
        showsinsrc1=(DrawView) findViewById(R.id.showsinsrc1);
        timearas=(LinearLayout)findViewById(R.id.sys) ;
        plareas=(LinearLayout)findViewById(R.id.pys);
        Sin.frequeces=new ArrayList<>();
        Sin.amplitudes=new ArrayList<>();
        Sin.frequeces.add(15.0);
        Sin.amplitudes.add(20.0);
        for(int i=0;i<Sin.amplitudes.size();i++){
            zfxltext.setText("[");
            zfxltext.append(String.valueOf(Sin.amplitudes.get(i)));
            zfxltext.append("]");
        }
        plxltext=(EditText)sinset.findViewById(R.id.plxl);
        for(int i=0;i<Sin.frequeces.size();i++){
            plxltext.setText("[");
            plxltext.append(String.valueOf(Sin.frequeces.get(i)));
            plxltext.append("]");
        }
        upload=(TextView)findViewById(R.id.upload);
        numb=(EditText)setnumbview.findViewById(R.id.numb);
        numb.setText(String.valueOf(N));
        savenumb=(TextView)setnumbview.findViewById(R.id.savenumb);
        cacelnumb=(TextView)setnumbview.findViewById(R.id.cacelnumb);
        method=(AutoCompleteTextView) setnumbview.findViewById(R.id.method);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mthods);
        method.setAdapter(adapter);
        analyse=(Button)findViewById(R.id.analyse) ;
        rates=(Button)findViewById(R.id.rates);
        fxset=(Button)findViewById(R.id.fxset);
        fxset.setOnClickListener(this);
//        myWebViewdes=(MyWebView)findViewById(R.id.showsindes);
        myWebView=(MyWebView)findViewById(R.id.showsinsrc);
        myWebView.loadUrl("file:///android_asset/show.html");
        myWebView.addJavascriptInterface(mydata,"mydata");
        myWebView.setInitialScale(200);
        myWebView.scrollTo(600,400);
        xinhaoyuanset=(Button)findViewById(R.id.xhyset);
//        showsrc=(ShowScreen)findViewById(R.id.showsrc);
        showdes=(ShowScreens)findViewById(R.id.showdes);
        method.setText(usewho);
        builder=new AlertDialog.Builder(this);
        title= new TextView(this);
        title.setBackgroundColor(0xff3010ec);
        title.setTextSize(30);
        title.setTextColor(0xffffffff);
        title.setText("分析设置");
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setView(setnumbview);
        dialog=builder.create();
        createsindialog();
        cancelsinset.setOnClickListener(this);
        upload.setOnClickListener(this);
        savesinset.setOnClickListener(this);
        apply.setOnClickListener(this);
        analyse.setOnClickListener(this);
        savenumb.setOnClickListener(this);
        timeara.setOnClickListener(this);
        plarea.setOnClickListener(this);
        cacelnumb.setOnClickListener(this);
        xinhaoyuanset.setOnClickListener(this);
        rates.setOnClickListener(this);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sin.stopsindata();
            }
        });
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
        if(v.getId()==R.id.apply){
            desSresult=new ArrayList<>();
            srcTresult=new ArrayList<>();
            Sin.getSinSingnal(drawSinHander);
        }if(v.getId()==R.id.analyse){
                analysefft();
        }if(v.getId()==R.id.fxset){
            dialog.show();
        }if(v.getId()==R.id.srcresult){
            showDialog("振幅/时间",srctimeresultstring);
        }if(v.getId()==R.id.sy){
            plareas.setVisibility(View.GONE);
            timearas.setVisibility(View.VISIBLE);
        }if(v.getId()==R.id.py){
            timearas.setVisibility(View.GONE);
            plareas.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.rates){
            showDialog("频率/振幅",dessquaresultstring);
        }if(v.getId()==R.id.savenumb){
            if(method.getText().toString().equals("dft")){
                usewho="dft";
            }else if(method.getText().toString().equals("fft")){
                usewho="fft";
            }
            N=Integer.valueOf(numb.getText().toString());
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            dialog.hide();
        }else if(v.getId()==R.id.cacelnumb){
            dialog.hide();
        }else if(v.getId()==R.id.savesinset){
            String sample= cypl.getText().toString();
            sample=sample.replace(" ","");
            Sin.samplerate=Double.valueOf(sample);
            getAmplitudes();
            getFrequces();
            String text=fbltext.getText().toString();
            text.replaceAll(" ","");
            Sin.Numb=Integer.valueOf(text);
            if(Sin.frequeces.size()!=Sin.amplitudes.size()){
                Toast.makeText(this,"保存失败，请确保振幅序列和频率序列个数一致",Toast.LENGTH_SHORT).show();
                Sin.frequeces=new ArrayList<>();
                Sin.amplitudes=new ArrayList<>();
                return;
            }
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            sinsetdialog.hide();
        }else if(v.getId()==R.id.cancelsinset){
            sinsetdialog.hide();
        }else if(v.getId()==R.id.xhyset){
            sinsetdialog.show();
        }else if(id==R.id.upload){
            if(!User.myself.isloging){
                Toast.makeText(SineApply.this,"请先登录，在进行此操作",Toast.LENGTH_SHORT).show();
                return;
            }
            if(srcTresult.size()==0||desSresult.size()==0){
                Toast.makeText(SineApply.this,"请先完成实验",Toast.LENGTH_SHORT).show();
                return;
            }
            classDialog.show();
            loadClss();

        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent=new Intent(SineApply.this,SinSet.classroom);
//        startActivity(intent);
//        return true;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sinmenu,menu);
//        return true;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getFrequces();
    }



    public void analysefft(){
        desSresult=new ArrayList();
        if(mydata.siny.size()<=0){
            Toast.makeText(SineApply.this,"请先获取数据源",Toast.LENGTH_SHORT).show();
            return;
        }
        Complexs[]temp=new Complexs[Sin.Numb];
        for(int i=0;i<temp.length;i++){
            temp[i]=new Complexs(mydata.siny.get(i),0);
        }
        Complexs[] result= Complexs.fft(temp);
        ArrayList<Float>f=new ArrayList<>();
        plresult=new ArrayList<>();
        for(int k=0;k<result.length;k++){
            f.add((float)Sin.frequecerate*k);
            plresult.add((float)result[k].getMod()/Sin.Numb);
        }
        Log.e("dyx","Collections.max(plresult):"+Collections.max(plresult));
        showdes.drawOXY=false;
        showdes.drawPointHighforpp(f,plresult);
        dessquaresultstring=new StringBuilder();
        DecimalFormat df=new DecimalFormat("#.###");
        for(int i=0;i<plresult.size();i++){
            String f1=df.format(f.get(i));
            String a1=df.format(plresult.get(i));
            String re=f1+"#"+a1;
            desSresult.add(re);
            dessquaresultstring.append(re+"\n");
        }
    }

    public void getAmplitudes(){
        try {
            Sin.amplitudes=new ArrayList<>();
            String fs=zfxltext.getText().toString();
            String temp=fs.replaceAll(" ","");
            while(true){
                String numbs= temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
                double zf=Double.valueOf(numbs);
                Sin.amplitudes.add(zf);
                if(temp.indexOf("]")+1==temp.length()){
                    break;
                }
                temp=temp.substring(temp.indexOf("]")+1,temp.length());
            }
        }catch (Exception e){
            Toast.makeText(this,"请正确填入频率信息",Toast.LENGTH_SHORT).show();
        }
    }

    public void getFrequces(){
        try {
            Sin.frequeces=new ArrayList<>();
            String fs=plxltext.getText().toString();
            String temp=fs.replaceAll(" ","");
            while(true){
                String numbs= temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
                double ft=Double.valueOf(numbs);
                Sin.frequeces.add(ft);
                if(temp.indexOf("]")+1==temp.length()){
                    break;
                }
                temp=temp.substring(temp.indexOf("]")+1,temp.length());
            }
        }catch (Exception e){
            Toast.makeText(this,"请正确填入频率信息",Toast.LENGTH_SHORT).show();
        }
    }
    public void showDialog(String ti,StringBuilder strbuilder){
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
        text.setText(strbuilder.toString());
        builder.setCustomTitle(title);
        builder.setView(text);
        builder.show();
    }

    public void createsindialog(){
       AlertDialog.Builder sinsetbuilder=new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setBackgroundColor(0xff3B99FF);
        title.setTextSize(30);
        title.setTextColor(0xffffffff);
        title.setText("信号源设置");
        title.setGravity(Gravity.CENTER);
        sinsetbuilder.setCustomTitle(title);
        sinsetbuilder.setView(sinset);
        sinsetdialog=sinsetbuilder.create();
    }

   public void dataupload(String classId){
        try {
            JSONObject data=new JSONObject();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sytime=format.format(new Date());
            data.put("实验时间",sytime);
            data.put("实验名称","正余弦信号的谱分析");
            data.put("bltstuId", User.myself.admin);
            data.put("bltclassId",classId);//此处待确定
            data.put("采样点数",Sin.Numb);
            data.put("最大频率",Sin.maxfrequece);
            data.put("采样时长",Sin.alltime);
            data.put("采样间隔",Sin.jgtime);
            data.put("频率分辨率",Sin.frequecerate);
            data.put("采样频率",Sin.samplerate);
            data.put("频率序列",Sin.frequeces);
            data.put("振幅序列",Sin.amplitudes);
            data.put("目标频域",desSresult);
            data.put("原始时域",srcTresult);
            Net.connect(Net.host+"/szxhcl/addsy1data",data.toString(),uploadsin);
        }catch (Exception e){
            Toast.makeText(SineApply.this,"错误,"+e.getMessage(),Toast.LENGTH_SHORT).show();
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
