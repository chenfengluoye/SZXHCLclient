package com.qqjyb.szxhcl;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FIRDtDesign extends AppCompatActivity implements View.OnClickListener {
    Spinner firtype;
    Spinner wintype;
    AlertDialog dialog;
    AlertDialog shuomingdialog;
    ImageView winshow;
    EditText n;
    EditText fln;
    EditText fhn;
    EditText B;
    EditText fs;
    EditText Qp;
    EditText Qs;
    TextView save;
    TextView shuoming;
    TextView cancel;
   static int band=0;
   static int wn=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firdesign);
        firtype=(Spinner)findViewById(R.id.firtype);
        wintype=(Spinner)findViewById(R.id.wintype);
        shuoming=(TextView)findViewById(R.id.shuoming);
        cancel=(TextView)findViewById(R.id.cancel);
        Qp=(EditText)findViewById(R.id.Qp);
        Qs=(EditText)findViewById(R.id.Qs);
        Qp.setText(String.valueOf(FIR.Qp));
        Qs.setText(String.valueOf(FIR.Qs));
        cancel.setOnClickListener(this);
        shuoming.setOnClickListener(this);
        band=FIR.band-1;
        wn=FIR.wn-1;
        wintype.setSelection(wn);
        firtype.setSelection(band);
        winshow=(ImageView)findViewById(R.id.winshow);
        save=(TextView)findViewById(R.id.save);
        n=(EditText)findViewById(R.id.n);
        fln=(EditText)findViewById(R.id.fln);
        fhn=(EditText)findViewById(R.id.fhn);
        B=(EditText)findViewById(R.id.B);
        B.setText(String.valueOf(FIR.beta));
        n.setText(String.valueOf(FIR.n));
        fs=(EditText)findViewById(R.id.fs);
        fs.setText(String.valueOf(FIR.fs));
        fln.setText(String.valueOf(FIR.fln));
        fhn.setText(String.valueOf(FIR.fhn));
        save.setOnClickListener(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.xinnengbiao,null);
        Button back=(Button)view.findViewById(R.id.back);
        back.setOnClickListener(this);
        builder.setView(view);
        dialog= builder.create();

        View views= LayoutInflater.from(getApplicationContext()).inflate(R.layout.firshuoming,null);
        Button backs=(Button)views.findViewById(R.id.back1);
        backs.setOnClickListener(this);
        AlertDialog.Builder builders=new AlertDialog.Builder(this);
        builders.setView(views);
        shuomingdialog=builders.create();
        firtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=(TextView)view;
                try {
                    tv.setGravity(Gravity.CENTER);
                }catch (Exception e){
                    e.printStackTrace();
                }
                tv.setTextColor(0xffffffff);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        wintype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=(TextView)view;
                try {
                    tv.setGravity(Gravity.CENTER);
                }catch (Exception e){
                    e.printStackTrace();
                }
                tv.setTextColor(0xffffffff);
                String tp=tv.getText().toString();
                if(tp.equals("矩形窗")){
                    winshow.setImageResource(R.drawable.jxc);
                }else if(tp.equals("图基窗")){
                    winshow.setImageResource(R.drawable.gsc);
                }else if(tp.equals("三角窗")){
                    winshow.setImageResource(R.drawable.sjc);
                }
                else if(tp.equals("汉宁窗")){
                    winshow.setImageResource(R.drawable.hlc);
                }else if(tp.equals("海明窗")){
                    winshow.setImageResource(R.drawable.hmc);
                }else if(tp.equals("布拉克曼窗")){
                    winshow.setImageResource(R.drawable.blkmc);
                }else if(tp.equals("凯撒窗")){
                    winshow.setImageResource(R.drawable.ksc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xinnengmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dialog.show();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back||v.getId()==R.id.back1){
            shuomingdialog.hide();
            dialog.hide();
        }else if(v.getId()==R.id.shuoming){
            shuomingdialog.show();
        }else if(v.getId()==R.id.cancel){
            finish();
        }
        else if(v.getId()==R.id.save){
            try{
                band=firtype.getSelectedItemPosition();
                FIR.band=band+1;
                wn=wintype.getSelectedItemPosition();
                FIR.wn=wn+1;
                FIR.Qp=Double.valueOf(Qp.getText().toString());
                FIR.Qs=Double.valueOf(Qs.getText().toString());
                String n1=n.getText().toString();
                String n2=n1.replaceAll(" ","");
                FIR.n=Integer.valueOf(n2);
                String fln1=fln.getText().toString();
                String fln2=fln1.replaceAll(" ","");
                FIR.fln=Double.valueOf(fln2);
                String fhn1=fhn.getText().toString();
                String fhn2=fhn1.replaceAll(" ","");
                FIR.fhn=Double.valueOf(fhn2);
                String B1= B.getText().toString();
                String B2=B1.replaceAll(" ","");
                FIR.beta=Double.valueOf(B2);
                String fs1=fs.getText().toString();
                String fs2=fs1.replaceAll(" ","");
                FIR.fs=Double.valueOf(fs2);
                Toast.makeText(FIRDtDesign.this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }catch (Exception e){
                Toast.makeText(FIRDtDesign.this,"部分信息保存失败，请检查信息是否正确",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
