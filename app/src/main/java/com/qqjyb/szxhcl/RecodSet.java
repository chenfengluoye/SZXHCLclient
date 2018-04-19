package com.qqjyb.szxhcl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecodSet extends AppCompatActivity implements View.OnClickListener {
    EditText standf;
    EditText simplef;
    EditText errorf;
    EditText timetext;
    EditText maxn;
    EditText fbltext;
    TextView savechange;
    TextView quxiaochange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recod_set);
        standf=(EditText)findViewById(R.id.standf) ;
        simplef=(EditText)findViewById(R.id.simplef);
        errorf=(EditText)findViewById(R.id.errorf);
        timetext=(EditText)findViewById(R.id.time) ;
        fbltext=(EditText)findViewById(R.id.fbl);
        maxn=(EditText)findViewById(R.id.maxn);
        savechange=(TextView)findViewById(R.id.savechange);
        quxiaochange=(TextView)findViewById(R.id.quxiaochange);
//        timetext.setText(String.valueOf(BaseVoiceActivity.time));
        maxn.setText(String.valueOf(BaseVoiceActivity.maxn));
        fbltext.setText(String.valueOf(getWave.Numb));
        simplef.setText(String.valueOf(getWave.SAMPLE_RATE));
//        standf.setText(String.valueOf(getWave.FREQUENCY));
        savechange.setOnClickListener(this);
        quxiaochange.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.savechange){
//            BaseVoiceActivity.time=Double.valueOf(timetext.getText().toString());
//            BaseVoiceActivity.maxn=Integer.valueOf(maxn.getText().toString());
            getWave.Numb=Integer.valueOf(fbltext.getText().toString());
            getWave.SAMPLE_RATE=Integer.valueOf(simplef.getText().toString());
//            getWave.FREQUENCY=Double.valueOf(standf.getText().toString());
            Toast.makeText(this,"设置已生效",Toast.LENGTH_SHORT).show();
            finish();
        }else if(id==R.id.quxiaochange){
            Toast.makeText(this,"设置已取消",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
