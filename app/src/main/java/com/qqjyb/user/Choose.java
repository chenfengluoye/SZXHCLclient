package com.qqjyb.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.qqjyb.szxhcl.MainActivity;
import com.qqjyb.szxhcl.R;

public class Choose extends AppCompatActivity implements View.OnClickListener {
    BootstrapButton createclass;
    BootstrapButton joinclass;
    BootstrapButton myclass;
    BootstrapButton startsy;
    BootstrapLabel myname;
    BootstrapLabel myId;
    BootstrapLabel schoolorclass;
    BootstrapLabel update;
    BootstrapLabel sorclablel;
    BootstrapLabel role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        createclass=(BootstrapButton)findViewById(R.id.createclass);
        joinclass=(BootstrapButton)findViewById(R.id.jionclass);
        myclass=(BootstrapButton)findViewById(R.id.myclass);
        startsy=(BootstrapButton)findViewById(R.id.startsy);
        myname=(BootstrapLabel)findViewById(R.id.myname);
        myId=(BootstrapLabel)findViewById(R.id.myId);
        schoolorclass=(BootstrapLabel)findViewById(R.id.schoolorclass);
        update=(BootstrapLabel)findViewById(R.id.update);
        sorclablel=(BootstrapLabel)findViewById(R.id.sorclable);
        role=(BootstrapLabel)findViewById(R.id.role);
        role.setText(User.myself.role);
        update.setOnClickListener(this);
        createclass.setOnClickListener(this);
        joinclass.setOnClickListener(this);
        myclass.setOnClickListener(this);
        startsy.setOnClickListener(this);
        if(User.myself.role.equals("教师")){
            joinclass.setVisibility(View.GONE);
            sorclablel.setText("学校名称");
            myname.setText(User.myself.name);
            if(User.myself.name==null||User.myself.name.equals("")){
                myname.setText("未设置");
            }
            myId.setText(User.myself.admin);
            if(User.myself.nowschool==null||User.myself.nowschool.equals("")){
                schoolorclass.setText("未设置");
            }
            else {
                schoolorclass.setText(User.myself.nowschool);
            }
        }if(User.myself.role.equals("学生")){
            createclass.setVisibility(View.GONE);
            sorclablel.setText("班级名称");
            myname.setText(User.myself.name);
            if(User.myself.name==null||User.myself.name.equals("")){
                myname.setText("未设置");
            }
            myId.setText(User.myself.admin);
            if(User.myself.bltclass==null||User.myself.bltclass.equals("")){
                schoolorclass.setText("未设置");
            }
            else {
                schoolorclass.setText(User.myself.bltclass);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.createclass){
            Intent intent=new Intent(Choose.this,CreateClass.class);
            startActivity(intent);
        }else if(id==R.id.jionclass){
            Intent intent=new Intent(Choose.this,JoinClass.class);
            startActivity(intent);
        }else if(id==R.id.myclass){
            Intent intent=new Intent(Choose.this,MyClass.class);
            startActivity(intent);
        }else if(id==R.id.startsy){
            Intent intent=new Intent(Choose.this,MainActivity.class);
            startActivity(intent);
        }else if(id==R.id.update){
            Intent intent=new Intent(Choose.this,ChangeMySelf.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        myname.setText(User.myself.name);
        if(User.myself.role.equals("教师")){
            schoolorclass.setText(User.myself.nowschool);
        }if(User.myself.role.equals("学生")){
            schoolorclass.setText(User.myself.bltclass);
        }
    }
}
