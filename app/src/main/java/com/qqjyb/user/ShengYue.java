package com.qqjyb.user;

import android.content.Intent;
import android.os.*;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.qqjyb.szxhcl.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ShengYue extends AppCompatActivity {

    BootstrapEditText score;
    BootstrapEditText comment;
    BootstrapButton suresy;
    String syId;
    String scoreId;
    String scoremark;
    String commentmsg;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(ShengYue.this,"操作成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ShengYue.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ShengYue.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheng_yue);
        score=(BootstrapEditText)findViewById(R.id.score);
        comment=(BootstrapEditText)findViewById(R.id.comment);
        suresy=(BootstrapButton)findViewById(R.id.suresy);
        Intent intent=getIntent();
        syId=intent.getStringExtra("syId");
        scoreId=intent.getStringExtra("scoreId");
        commentmsg=intent.getStringExtra("comment");
        scoremark=intent.getStringExtra("scoremark");
        score.setText(scoremark);
        comment.setText(commentmsg);
        suresy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scoremsg=score.getText().toString();
                scoremsg=scoremsg.replaceAll(" ","");
                try{
                    Double d=Double.valueOf(scoremsg);
                }catch (Exception e){
                    Toast.makeText(ShengYue.this,"您输入的分数不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                String commentmsg=comment.getText().toString();
                if(scoremsg.equals("")||commentmsg.equals("")){
                    Toast.makeText(ShengYue.this,"请数据评价内容及分数",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("syId",syId);
                        jsonObject.put("scoreId",scoreId);
                        jsonObject.put("comment",commentmsg);
                        jsonObject.put("scoremark",scoremsg);
                        jsonObject.put("isshengyue","yes");
                        Net.connect(Net.host+"/szxhcl/changesy",jsonObject.toString(),handler);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
