package com.qqjyb.szxhcl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SinSet extends AppCompatActivity implements View.OnClickListener {
    TextView srue;
    TextView cancel;
    EditText xw;
    EditText yw;
    EditText fxxw;
    EditText fxyw;
    EditText time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_set);
        srue=(TextView)findViewById(R.id.srue);
        cancel=(TextView)findViewById(R.id.cancel);
        xw=(EditText)findViewById(R.id.xw);
        yw=(EditText)findViewById(R.id.yw);
        fxxw=(EditText)findViewById(R.id.fxxw);
        fxyw=(EditText)findViewById(R.id.fxyw);
        time=(EditText)findViewById(R.id.time) ;
        time.setText(String.valueOf(Sin.samplerate));
        fxxw.setText(String.valueOf(SineApply.showdes.xw));
        fxyw.setText(String.valueOf(SineApply.showdes.yw));
        srue.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.srue){
            Sin.samplerate=Double.valueOf(time.getText().toString());
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            finish();
        }else if(v.getId()==R.id.cancel){
            finish();
        }
    }
}
