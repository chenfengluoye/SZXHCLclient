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


public class StudentRegister extends AppCompatActivity implements View.OnClickListener{
    EditText admine;
    EditText pass;
    EditText repass;
    BootstrapButton regist;
    BootstrapLabel tecregist;
    BootstrapLabel stulogin;

    MyHandler sturegisthandler=new MyHandler("sturegisthandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(StudentRegister.this,"操作成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(StudentRegister.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(StudentRegister.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        admine=(EditText)findViewById(R.id.admin);
        pass=(EditText)findViewById(R.id.pass);
        repass=(EditText)findViewById(R.id.repass);
        regist=(BootstrapButton)findViewById(R.id.regist);
        tecregist=(BootstrapLabel)findViewById(R.id.tecregist);
        stulogin=(BootstrapLabel)findViewById(R.id.stulogin);
        tecregist.setOnClickListener(this);
        stulogin.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.regist){
            String adminmsg=admine.getText().toString();
            String passmsg=pass.getText().toString();
            String repassmsg=repass.getText().toString();
            if(!passmsg.equals(repassmsg)){
                Toast.makeText(StudentRegister.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("stuId",adminmsg);
                jsonObject.put("stupass",passmsg);
                jsonObject.put("stuclass","未设置");
                jsonObject.put("stuname","未设置");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Net.connect(Net.host+"/szxhcl/sturegist",jsonObject.toString(),sturegisthandler);
        }else if(id==R.id.stulogin){
            Intent intent=new Intent(StudentRegister.this,StudentLogin.class);
            startActivity(intent);
        }else if(id==R.id.tecregist){
            Intent intent=new Intent(StudentRegister.this,TeacherRegister.class);
            startActivity(intent);
        }
    }
}
