package com.qqjyb.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qqjyb.szxhcl.R;

import org.json.JSONException;
import org.json.JSONObject;


public class ScoreList extends AppCompatActivity implements View.OnClickListener {

    TextView stuname;
    TextView stuId;
    TextView bltclassname;
    LinearLayout zyx;
    LinearLayout lbq;
    LinearLayout yyxh;
    LinearLayout dqxt;
    LinearLayout tzjtq;
    LinearLayout ly;
    static String stuIdmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
        stuname=(TextView)findViewById(R.id.stuname);
        stuId=(TextView)findViewById(R.id.stuId);
        bltclassname=(TextView)findViewById(R.id.bltclassname);
        zyx=(LinearLayout)findViewById(R.id.zyx);
        lbq=(LinearLayout)findViewById(R.id.lbq);
        yyxh=(LinearLayout)findViewById(R.id.yyxh);
        dqxt=(LinearLayout)findViewById(R.id.dpxt);
        tzjtq=(LinearLayout)findViewById(R.id.tzjtq);
        ly=(LinearLayout)findViewById(R.id.ly);
        Intent intent=getIntent();
        stuIdmsg=intent.getStringExtra("stuId");
        int position=intent.getIntExtra("position",0);
        try {
            JSONObject student=ClassMsg.studentArray.getJSONObject(position);
            stuname.setText(student.optString("stuname"));
            stuId.setText(student.optString("stuId"));
            bltclassname.setText(student.optString("stuclass"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        zyx.setOnClickListener(this);
        lbq.setOnClickListener(this);
        yyxh.setOnClickListener(this);
        dqxt.setOnClickListener(this);
        tzjtq.setOnClickListener(this);
        ly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.zyx){
            Intent intent=new Intent(ScoreList.this,SY1Result.class);
            startActivity(intent);
        }else if(id==R.id.lbq){
            Intent intent=new Intent(ScoreList.this,SY2Result.class);
            startActivity(intent);
        }else if(id==R.id.yyxh){
            Intent intent=new Intent(ScoreList.this,SY3Result.class);
            startActivity(intent);
        }else if(id==R.id.dpxt){
            Toast.makeText(ScoreList.this,"开发中，敬请期待。。。",Toast.LENGTH_SHORT).show();
        }else if(id==R.id.tzjtq){
            Toast.makeText(ScoreList.this,"开发中，敬请期待。。。",Toast.LENGTH_SHORT).show();
        }else if(id==R.id.ly){
            Intent intent=new Intent(ScoreList.this,Message.class);
            intent.putExtra("stuIdmsg",stuIdmsg);
            startActivity(intent);
        }
    }
}
