package com.qqjyb.user;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.qqjyb.szxhcl.R;

import org.json.JSONException;
import org.json.JSONObject;


public class TeacherRegister extends AppCompatActivity implements View.OnClickListener{
    EditText admine;
    EditText pass;
    EditText repass;
    BootstrapButton regist;
    BootstrapLabel sturegist;
    BootstrapLabel teclogin;

    MyHandler tecregisthandler=new MyHandler("tecregisthandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(TeacherRegister.this,"操作成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(TeacherRegister.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(TeacherRegister.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        admine=(EditText)findViewById(R.id.admin);
        pass=(EditText)findViewById(R.id.pass);
        repass=(EditText)findViewById(R.id.repass);
        regist=(BootstrapButton)findViewById(R.id.regist);
        sturegist=(BootstrapLabel)findViewById(R.id.sturegist);
        teclogin=(BootstrapLabel)findViewById(R.id.teclogin);
        regist.setOnClickListener(this);
        sturegist.setOnClickListener(this);
        teclogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.regist){
            String adminmsg=admine.getText().toString();
            String passmsg=pass.getText().toString();
            String repassmsg=repass.getText().toString();
            if(!passmsg.equals(repassmsg)){
                Toast.makeText(TeacherRegister.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("teacherId",adminmsg);
                jsonObject.put("tecpass",passmsg);
                jsonObject.put("bltschool","未设置");
                jsonObject.put("tecname","未设置");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Net.connect(Net.host+"/szxhcl/tecregist",jsonObject.toString(),tecregisthandler);
        }else if(id==R.id.sturegist){
            Intent intent=new Intent(TeacherRegister.this,StudentRegister.class);
            startActivity(intent);
        }else if(id==R.id.teclogin){
            Intent intent=new Intent(TeacherRegister.this,TeacherLogin.class);
            startActivity(intent);
        }
    }
}
