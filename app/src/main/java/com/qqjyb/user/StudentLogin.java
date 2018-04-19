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


public class StudentLogin extends AppCompatActivity implements View.OnClickListener{
    String adminmsg="null";
    String passmsg="null";
    EditText admine;
    EditText pass;
    BootstrapButton login;
    BootstrapLabel teclogin;
    BootstrapLabel sturegist;
    BootstrapProgressBar progressBar;
    MyHandler stuloghander=new MyHandler("techloghander"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            progressBar.setVisibility(View.INVISIBLE);
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.optString("stuId").equals(adminmsg)){
                        Toast.makeText(StudentLogin.this,"登录成功",Toast.LENGTH_SHORT).show();
                        User.myself=new User();
                        User.myself.role="学生";
                        User.myself.admin=object.optString("stuId");
                        User.myself.name=object.optString("stuname");
                        User.myself.bltclass=object.optString("stuclass");
                        User.myself.isloging=true;
                        Intent intent=new Intent(StudentLogin.this,Choose.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(StudentLogin.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(StudentLogin.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        progressBar=(BootstrapProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        admine=(EditText)findViewById(R.id.admin);
        pass=(EditText)findViewById(R.id.pass);
        login=(BootstrapButton)findViewById(R.id.login);
        teclogin=(BootstrapLabel)findViewById(R.id.teclogin);
        sturegist=(BootstrapLabel)findViewById(R.id.sturegist);
        login.setOnClickListener(this);
        teclogin.setOnClickListener(this);
        sturegist.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.login){
            adminmsg=admine.getText().toString();
            passmsg=pass.getText().toString();
            if(adminmsg.equals("")||passmsg.equals("")){
                Toast.makeText(StudentLogin.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject object=new JSONObject();
            try {
                object.put("stuId",adminmsg);
                object.put("stupass",passmsg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.VISIBLE);
            Net.connect(Net.host+"/szxhcl/stulogin",object.toString(),stuloghander);
        }else if(id==R.id.teclogin){
            Intent intent=new Intent(StudentLogin.this,TeacherLogin.class);
            startActivity(intent);
        }else if(id==R.id.sturegist){
            Intent intent=new Intent(StudentLogin.this,StudentRegister.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
