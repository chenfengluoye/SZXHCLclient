package com.qqjyb.szxhcl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FFTApply extends AppCompatActivity implements View.OnClickListener {
    TextView sineapply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fftapply);
        sineapply=(TextView)findViewById(R.id.sineapply);
        sineapply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.sineapply){
            Intent intent=new Intent(FFTApply.this,SineApply.class);
            startActivity(intent);
        }
    }
}
