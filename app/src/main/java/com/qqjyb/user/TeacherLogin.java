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
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.qqjyb.szxhcl.R;

import org.json.JSONException;
import org.json.JSONObject;


public class TeacherLogin extends AppCompatActivity implements View.OnClickListener{

    String adminmsg="null";
    String passmsg="null";
    BootstrapProgressBar progressBar;
    EditText admine;
    EditText pass;
    BootstrapButton login;
    BootstrapLabel stulogin;
    BootstrapLabel tecregist;
    MyHandler techloghander=new MyHandler("techloghander"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            progressBar.setVisibility(View.INVISIBLE);
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.optString("teacherId").equals(adminmsg)){
                        Toast.makeText(TeacherLogin.this,"登录成功",Toast.LENGTH_SHORT).show();
                        User.myself=new User();
                        User.myself.role="教师";
                        User.myself.admin=object.optString("teacherId");
                        User.myself.name=object.optString("tecname");
                        User.myself.nowschool=object.optString("bltschool");
                        User.myself.isloging=true;
                        Intent intent=new Intent(TeacherLogin.this,Choose.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(TeacherLogin.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(TeacherLogin.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        progressBar=(BootstrapProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        admine=(EditText)findViewById(R.id.admin);
        pass=(EditText)findViewById(R.id.pass);
        login=(BootstrapButton)findViewById(R.id.login);
        stulogin=(BootstrapLabel)findViewById(R.id.stulogin);
        tecregist=(BootstrapLabel)findViewById(R.id.tecregist);
        login.setOnClickListener(this);
        stulogin.setOnClickListener(this);
        tecregist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.login){
            adminmsg=admine.getText().toString();
            passmsg=pass.getText().toString();
            if(adminmsg.equals("")||passmsg.equals("")){
                Toast.makeText(TeacherLogin.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject object=new JSONObject();
            try {
                object.put("teacherId",adminmsg);
                object.put("tecpass",passmsg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.VISIBLE);
            Net.connect(Net.host+"/szxhcl/teclogin",object.toString(),techloghander);
        }else if(id==R.id.tecregist){
            Intent intent=new Intent(TeacherLogin.this,TeacherRegister.class);
            startActivity(intent);
        }else if(id==R.id.stulogin){
            Intent intent=new Intent(TeacherLogin.this,StudentLogin.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
