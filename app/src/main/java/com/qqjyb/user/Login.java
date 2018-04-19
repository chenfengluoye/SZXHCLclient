package com.qqjyb.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.qqjyb.szxhcl.MainActivity;
import com.qqjyb.szxhcl.R;

public class Login extends AppCompatActivity {
    BootstrapButton teacherLogin;
    BootstrapButton studentLogin;
    BootstrapButton touristmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        teacherLogin=(BootstrapButton)findViewById(R.id.teacherlogin);
        studentLogin=(BootstrapButton)findViewById(R.id.studentlogin);
        touristmodel=(BootstrapButton)findViewById(R.id.touristmodel);
        teacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,TeacherLogin.class);
                startActivity(intent);
            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,StudentLogin.class);
                startActivity(intent);
            }
        });
        touristmodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
