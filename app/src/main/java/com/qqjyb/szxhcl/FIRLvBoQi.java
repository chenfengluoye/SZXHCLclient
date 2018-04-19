package com.qqjyb.szxhcl;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

public class FIRLvBoQi extends AppCompatActivity implements View.OnClickListener {
    View classView;
    ListView classlistview;
    JSONArray classlist;
    ClassAdapter classAdapter;
    AlertDialog.Builder classbuilder;
    AlertDialog classDialog;
    LoadingView loading;

    DecimalFormat df=new DecimalFormat("#.###");
    DrawView srctimewave;
    DrawView destimewave;
    DrawView srcratewave;
    DrawView desratewave;
    Button xinnengbiao;
    Button design;
    Button design1;
    Button design2;
    Button design3;
    Button srctimexl;
    Button srcratexl;
    Button destimexl;
    Button desratexl;
    Button srcratefx;
    Button xhset;
    Button analyse1;
    Button xhset1;
    Button xhset2;
    Button xhset3;
    Button apply;
    Button apply1;
    Button upload;
    Button yssy;
    Button yspy;
    Button mdsy;
    Button mdpy;
    LinearLayout yssyview;
    LinearLayout yspyview;
    LinearLayout mdsyview;
    LinearLayout mdpyview;
    AlertDialog dialog;
    View sinset;
    TextView savesinset;
    TextView cancelsinset;
    Button xinhaoyuanset;
    EditText zfxltext;
    EditText plxltext;
    EditText fxdstext;
    AlertDialog dialogshowsrc;
    List<zhuobiao> srctime=new ArrayList<>();
    static List<Float>time=new ArrayList<>();
    List<zhuobiao> srcrate=new ArrayList<>();
    List<Float> destime=new ArrayList<>();//目标时域信号
    List<Float> srcrate1=new ArrayList<>();//原始频域信号
    List<Float> desrate=new ArrayList<>();//
    List<Float> desamplite=new ArrayList<>();
    List<Float> srcamplite=new ArrayList<>();

    List<String>desTimeStr=new ArrayList<>();
    List<String>desRateStr=new ArrayList<>();
    List<String>srcTimeStr=new ArrayList<>();//已填充
    List<String>srcRateStr=new ArrayList<>();//已填充

    MyHandler uploadfir=new MyHandler("uploadfir"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;

                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(FIRLvBoQi.this,"操作成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FIRLvBoQi.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(FIRLvBoQi.this,"网络错误",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FIRLvBoQi.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_lv_bo_qi);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        classView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.chooseclass,null);
        classlistview=(ListView)classView.findViewById(R.id.choose);
        loading=(LoadingView)classView.findViewById(R.id.loading);
        classbuilder=new AlertDialog.Builder(this);
        classbuilder.setView(classView);
        classDialog=classbuilder.create();
        apply=(Button)findViewById(R.id.apply);
        apply.setOnClickListener(this);
        apply1=(Button)findViewById(R.id.apply1);
        apply1.setOnClickListener(this);
        upload=(Button)findViewById(R.id.upload);
        yssy=(Button)findViewById(R.id.yssy);
        yspy=(Button)findViewById(R.id.yspy);
        mdsy=(Button)findViewById(R.id.mdsy);
        mdpy=(Button)findViewById(R.id.mdpy);
        analyse1=(Button)findViewById(R.id.analyse1) ;
        srctimexl=(Button)findViewById(R.id.srctimexl);
        srcratexl=(Button)findViewById(R.id.srcratexl);
        destimexl=(Button)findViewById(R.id.destimexl);
        desratexl=(Button)findViewById(R.id.desratexl);
        srctimexl.setOnClickListener(this);
        srcratexl.setOnClickListener(this);
        destimexl.setOnClickListener(this);
        desratexl.setOnClickListener(this);
        analyse1.setOnClickListener(this);
        upload.setOnClickListener(this);
        yssy.setOnClickListener(this);
        yspy.setOnClickListener(this);
        mdsy.setOnClickListener(this);
        mdpy.setOnClickListener(this);
        srcratefx=(Button)findViewById(R.id.analyse);
        srcratefx.setOnClickListener(this);
        srctimewave=(DrawView)findViewById(R.id.srctime);
        srcratewave=(DrawView)findViewById(R.id.srcrate);
        destimewave=(DrawView)findViewById(R.id.destime);
        desratewave=(DrawView)findViewById(R.id.desrate);
        yssyview=(LinearLayout)findViewById(R.id.srctimearea);
        yspyview=(LinearLayout)findViewById(R.id.srcratearea);
        mdsyview=(LinearLayout)findViewById(R.id.destimearea);
        mdpyview=(LinearLayout)findViewById(R.id.desratearea);
        xinnengbiao=(Button)findViewById(R.id.xinnengbiao);
        design=(Button)findViewById(R.id.design);
        design1=(Button)findViewById(R.id.design1);
        design2=(Button)findViewById(R.id.design2);
        design3=(Button)findViewById(R.id.design3);
        design.setOnClickListener(this);
        design1.setOnClickListener(this);
        design2.setOnClickListener(this);
        design3.setOnClickListener(this);
        xinnengbiao.setOnClickListener(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog.Builder builder1=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.xinnengbiao,null);
        Button back=(Button)view.findViewById(R.id.back);
        back.setOnClickListener(this);
        builder.setView(view);
        dialog= builder.create();
        sinset=LayoutInflater.from(getApplicationContext()).inflate(R.layout.getsindataset,null);
        savesinset=(TextView)sinset.findViewById(R.id.savesinset);
        cancelsinset=(TextView)sinset.findViewById(R.id.cancelsinset);
        fxdstext=(EditText)sinset.findViewById(R.id.fbl);
        zfxltext=(EditText)sinset.findViewById(R.id.zfxl);
        plxltext=(EditText)sinset.findViewById(R.id.plxl);
        fxdstext.setText(String.valueOf(FirData.Numb));
        savesinset.setOnClickListener(this);
        cancelsinset.setOnClickListener(this);
        TextView title = new TextView(this);
        title.setBackgroundColor(0xff3B99FF);
        title.setTextSize(30);
        title.setTextColor(0xffffffff);
        title.setText("信号源设置");
        title.setGravity(Gravity.CENTER);
        builder1.setCustomTitle(title);
        builder1.setView(sinset);
        dialogshowsrc=builder1.create();
        xhset=(Button)findViewById(R.id.xhset);
        xhset.setOnClickListener(this);
        xhset1=(Button)findViewById(R.id.xhset1);
        xhset1.setOnClickListener(this);
        xhset2=(Button)findViewById(R.id.xhset2);
        xhset2.setOnClickListener(this);
        xhset3=(Button)findViewById(R.id.xhset3);
        xhset3.setOnClickListener(this);
        FirData.frequeces=new ArrayList<>();
        FirData.amplitudes=new ArrayList<>();
        FirData.frequeces.add(15.0);
        FirData.amplitudes.add(20.0);
        for(int i=0;i<FirData.amplitudes.size();i++){
            zfxltext.setText("[");
            zfxltext.append(String.valueOf(FirData.amplitudes.get(i)));
            zfxltext.append("]");
        }
        for(int i=0;i<FirData.frequeces.size();i++){
            plxltext.setText("[");
            plxltext.append(String.valueOf(FirData.frequeces.get(i)));
            plxltext.append("]");
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
        if(id==R.id.yssy)
            {
                mdsyview.setVisibility(View.GONE);
                mdpyview.setVisibility(View.GONE);
                yspyview.setVisibility(View.GONE);
                yssyview.setVisibility(View.VISIBLE);
                mdsy.setBackgroundColor(0x00000000);
                mdpy.setBackgroundColor(0x00000000);
                yspy.setBackgroundColor(0x00000000);
                yssy.setBackgroundColor(0xff5679FF);
            }
        else if(id==R.id.yspy)
            {
                mdsyview.setVisibility(View.GONE);
                mdpyview.setVisibility(View.GONE);
                yssyview.setVisibility(View.GONE);
                yspyview.setVisibility(View.VISIBLE);
                mdsy.setBackgroundColor(0x00000000);
                mdpy.setBackgroundColor(0x00000000);
                yspy.setBackgroundColor(0xff5679FF);
                yssy.setBackgroundColor(0x00000000);
            }
        else if(id==R.id.mdpy)
            {
                mdsyview.setVisibility(View.GONE);
                yssyview.setVisibility(View.GONE);
                yspyview.setVisibility(View.GONE);
                mdpyview.setVisibility(View.VISIBLE);
                mdsy.setBackgroundColor(0x00000000);
                mdpy.setBackgroundColor(0xff5679FF);
                yspy.setBackgroundColor(0x00000000);
                yssy.setBackgroundColor(0x00000000);
            }
        else if(id==R.id.mdsy)
            {
                yssyview.setVisibility(View.GONE);
                yspyview.setVisibility(View.GONE);
                mdpyview.setVisibility(View.GONE);
                mdsyview.setVisibility(View.VISIBLE);
                mdpy.setBackgroundColor(0x00000000);
                mdsy.setBackgroundColor(0xff5679FF);
                yspy.setBackgroundColor(0x00000000);
                yssy.setBackgroundColor(0x00000000);
            }
        else if(id==R.id.xinnengbiao)
            {
                dialog.show();
            }
        else if(id==R.id.srctimexl)//检查正确
        {

            StringBuilder builder=new StringBuilder();

            for(int i=0;i<time.size();i++){
                String f1=df.format(srctime.get(i).y);
                String a1=df.format(time.get(i));
//                double  f1=new BigDecimal(srctime.get(i).y).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(time.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                String re=f1+"#"+a1;
                srcTimeStr.add(re);
                builder.append(re+"\n");
            }
            showDialog("振幅/时间",builder);
        }else if(id==R.id.srcratexl)//检查正确
        {
            StringBuilder builder=new StringBuilder();
            for(int i=0;i<srcrate1.size();i++){
                String f1=df.format(srcrate1.get(i));
                String a1=df.format(srcamplite.get(i));
//                double  f1=new BigDecimal(srcrate1.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(srcamplite.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                String re=f1+"#"+a1;
                srcRateStr.add(re);
                builder.append(re+"\n");
            }
            showDialog("频率/振幅",builder);

        }else if(id==R.id.destimexl)//检查正确
        {
            StringBuilder builder=new StringBuilder();
            for(int i=0;i<time.size();i++){
//                double  f1=new BigDecimal(destime.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(time.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                String f1=df.format(destime.get(i));
                String a1=df.format(time.get(i));
                String re=f1+"#"+a1;
                desTimeStr.add(re);
                builder.append(re+"\n");
            }
            showDialog("振幅/时间",builder);

        }else if(id==R.id.desratexl)//检查正确
        {
            StringBuilder builder=new StringBuilder();
            for(int i=0;i<desrate.size();i++){
//                double  f1=new BigDecimal(desrate.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(desamplite.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                String f1=df.format(desrate.get(i));
                String a1=df.format(desamplite.get(i));
                String re=f1+"#"+a1;
                desRateStr.add(re);
                builder.append(re+"\n");
            }
            showDialog("频率/振幅",builder);

        }
        else if(id==R.id.analyse)//原频谱分析
        {
            if(srctime.size()<=0){
                Toast.makeText(FIRLvBoQi.this,"请先获取数据源",Toast.LENGTH_SHORT).show();
                return;
            }
            Complexs[]temp=new Complexs[FirData.Numb];
            for(int i=0;i<temp.length;i++){
                temp[i]=new Complexs(srctime.get(i).y,0);
            }
            Complexs[] result= Complexs.fft(temp);
            srcrate1=new ArrayList<>();
            srcamplite=new ArrayList<>();
            int k;
            for(k=0;k<result.length;k++){
                srcrate1.add((float)FirData.frequecerate*k);
                srcamplite.add((float)result[k].getMod()/FirData.Numb);
            }
            srcratewave.clearView();
            srcratewave.init(0, (float) (k*FirData.frequecerate),0, Collections.max(srcamplite));
            srcratewave.drawPointHight(srcrate1,srcamplite);
            srcratewave.drawOXY();
            srcratewave.invalidate();
//            填充数据到要上传的数据域中
            srcRateStr=new ArrayList<>();
            for(int i=0;i<srcrate1.size();i++){
                String f1=df.format(srcrate1.get(i));
                String a1=df.format(srcamplite.get(i));
                String re=f1+"#"+a1;
                srcRateStr.add(re);
            }

        }else if(id==R.id.analyse1)//过滤后频谱分析
        {
            float []firresut=  getFirtimeresult();
            FFTAnlyse(firresut);
        }
        else if(id==R.id.apply1)//过滤后时域谱分析
        {
            desTimeStr=new ArrayList<>();
            float [] firresut= getFirtimeresult();
            destime=new ArrayList<>();
            for(int i=0;i<time.size();i++){
                destime.add(firresut[i]);
            }
            destimewave.clearView();
            destimewave.init(0,Collections.max(time),-Collections.max(destime),Collections.max(destime));
            destimewave.drawPointtoLine(time,destime);
            destimewave.drawOXY();
            destimewave.invalidate();

            for(int i=0;i<time.size();i++){
//                double  f1=new BigDecimal(destime.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(time.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                String f1=df.format(destime.get(i));
                String a1=df.format(time.get(i));
                String re=f1+"#"+a1;
                desTimeStr.add(re);
            }
        }
        else if(id==R.id.apply)//原时域谱分析
            {
                srcTimeStr=new ArrayList<>();
                srcRateStr=new ArrayList<>();
                desTimeStr=new ArrayList<>();
                desRateStr=new ArrayList<>();
                srctime=FirData.getSinSingnal();
                srctimewave.clearView();
                float a=0;
                for(double i:FirData.amplitudes){
                    a+=i;
                }
                srctimewave.init(0,(float)FirData.alltime,-a,a);
                srctimewave.drawPointtoLine(srctime);
                srctimewave.drawOXY();
                srctimewave.invalidate();

                for(int i=0;i<time.size();i++){
                    String f1=df.format(srctime.get(i).y);
                    String a1=df.format(time.get(i));
//                double  f1=new BigDecimal(srctime.get(i).y).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(time.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                    String re=f1+"#"+a1;
                    srcTimeStr.add(re);
                }
            }
        else if(id==R.id.design||id==R.id.design1||id==R.id.design2||id==R.id.design3) {
                Intent intent = new Intent(FIRLvBoQi.this, FIRDtDesign.class);
                startActivity(intent);
            }
        else if(id==R.id.upload){
            if(!User.myself.isloging){
                Toast.makeText(FIRLvBoQi.this,"请先登录，在进行此操作",Toast.LENGTH_SHORT).show();
                return;
            }
            if(srcRateStr.size()==0||srcTimeStr.size()==0||desRateStr.size()==0||desTimeStr.size()==0){
                Toast.makeText(FIRLvBoQi.this,"请先完成实验",Toast.LENGTH_SHORT).show();
                return;
            }
            classDialog.show();
            loadClss();
        }
        else if(id==R.id.back)
            {
                dialog.hide();
            }
        else if(id==R.id.xhset||id==R.id.xhset1||id==R.id.xhset2||id==R.id.xhset3)
            {
                dialogshowsrc.show();
            }
        else if(id==R.id.cancelsinset)
            {
                dialogshowsrc.hide();
            }
        else if(id==R.id.savesinset)
            {
                getFrequces();
                getAmplitudes();
                String text=fxdstext.getText().toString();
                text.replaceAll(" ","");
                FirData.Numb=Integer.valueOf(text);
                if(FirData.frequeces.size()!=FirData.amplitudes.size()){
                    Toast.makeText(this,"保存失败，请确保振幅序列和频率序列个数一致",Toast.LENGTH_SHORT).show();
                    FirData.frequeces=new ArrayList<>();
                    FirData.amplitudes=new ArrayList<>();
                    return;
                }
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                dialogshowsrc.hide();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.designfir,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(FIRLvBoQi.this,FIRDtDesign.class);
        startActivity(intent);
        return true;
    }

    public void getAmplitudes(){
        try {
            FirData.amplitudes=new ArrayList<>();
            String fs=zfxltext.getText().toString();
            String temp=fs.replaceAll(" ","");
            while(true){
                String numbs= temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
                double zf=Double.valueOf(numbs);
                FirData.amplitudes.add(zf);
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
            FirData.frequeces=new ArrayList<>();
            String fs=plxltext.getText().toString();
            String temp=fs.replaceAll(" ","");
            while(true){
                String numbs= temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
                double ft=Double.valueOf(numbs);
                FirData.frequeces.add(ft);
                if(temp.indexOf("]")+1==temp.length()){
                    break;
                }
                temp=temp.substring(temp.indexOf("]")+1,temp.length());
            }
        }catch (Exception e){
            Toast.makeText(this,"请正确填入频率信息",Toast.LENGTH_SHORT).show();
        }
    }

    //获取卷积序列
    public  float[] getFirtimeresult(){
        float[] src=new float[srctime.size()];
        int len=srctime.size();
        for(int i=0;i<len;i++){
            src[i]=srctime.get(i).y;
        }
        //设计滤波器，得到滤波系数
        float [] fir=FIR.getHh();
        //计算卷积，src为原始时域离散序列，fir为滤波系数
        float[] destimeresult= FIR.convolution(src,fir);
        return destimeresult;
    }

    //对过滤后的序列做FFT分析
    public void FFTAnlyse(float timearea[]){
        desRateStr=new ArrayList<>();
        if(timearea.length<=0){
            Toast.makeText(FIRLvBoQi.this,"请先获取数据源",Toast.LENGTH_SHORT).show();
            return;
        }
        Complexs[]temp=new Complexs[FirData.Numb];
        for(int i=0;i<temp.length;i++){
            temp[i]=new Complexs(timearea[i],0);
        }
        Complexs[] result= Complexs.fft(temp);
        desrate=new ArrayList<>();
        desamplite=new ArrayList<>();
        int k;
        for(k=0;k<result.length;k++){
            desrate.add((float)FirData.frequecerate*k);
            desamplite.add((float)(result[k].getMod()/FirData.Numb));
        }
        desratewave.clearView();
        desratewave.init(0, (float) (k*FirData.frequecerate),0, Collections.max(desamplite));
        desratewave.drawPointHight(desrate,desamplite);
        desratewave.drawOXY();
        desratewave.invalidate();

        for(int i=0;i<desrate.size();i++){
//                double  f1=new BigDecimal(desrate.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//                double  a1=new BigDecimal(desamplite.get(i)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
            String f1=df.format(desrate.get(i));
            String a1=df.format(desamplite.get(i));
            String re=f1+"#"+a1;
            desRateStr.add(re);

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

    public void dataupload(String classId){
        try{

            JSONObject data=new JSONObject();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sytime=format.format(new Date());
            data.put("实验时间",sytime);
            data.put("实验名称","数字滤波器的设计与实现");
            data.put("bltstuId", User.myself.admin);
            data.put("bltclassId",classId);
            data.put("采样点数",FirData.Numb);
            data.put("最大频率",FirData.maxfrequece);
            data.put("采样时长",FirData.alltime);
            data.put("采样间隔",FirData.jgtime);
            data.put("频率分辨率",FirData.frequecerate);
            data.put("采样频率",FirData.samplerate);
            data.put("频率序列",FirData.frequeces);
            data.put("振幅序列",FirData.amplitudes);
            data.put("滤波时域",desTimeStr);
            data.put("滤波频域",desRateStr);
            data.put("原始频域",srcRateStr);
            data.put("原始时域",srcTimeStr);

            JSONObject firConfig=new JSONObject();
            firConfig.put("阻带衰减", FIR.As);
//            firConfig.put("Rp",FIR.Rp);
            firConfig.put("通带边缘频率",FIR.Wp);
            firConfig.put("阻带起始频率",FIR.Ws);
            firConfig.put("截止频率",FIR.Wc);
            firConfig.put("通带波纹",FIR.Qp);
            firConfig.put("滤波器阶数",FIR.n);
            firConfig.put("滤波器类型",FIR.band);
            firConfig.put("通带频率衰减",FIR.fln);
            firConfig.put("阻带频率衰减",FIR.fhn);
            firConfig.put("窗函数的类型",FIR.wn);
//            firConfig.put("beta",FIR.beta);
            firConfig.put("采样频率",FIR.fs);
            data.put("滤波器配置",firConfig.toString());//待实现
            Net.connect(Net.host+"/szxhcl/addsy2data",data.toString(),uploadfir);
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
