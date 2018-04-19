package com.qqjyb.user;

import android.os.*;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.qqjyb.szxhcl.R;

import org.json.JSONException;
import org.json.JSONObject;


public class ChangeMySelf extends AppCompatActivity {
    static String mynamemsg;
    static String myschoolorclassmsg;
    BootstrapLabel schoolorclass;
    BootstrapEditText schoolorclssmsg;
    BootstrapEditText myname;
    BootstrapButton update;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        User.myself.name=mynamemsg;
                        if(User.myself.role.equals("教师")){
                            User.myself.nowschool=myschoolorclassmsg;
                        }
                        else {
                            User.myself.bltclass=myschoolorclassmsg;
                        }
                        Toast.makeText(ChangeMySelf.this,"操作成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ChangeMySelf.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ChangeMySelf.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_my_self);
        schoolorclass=(BootstrapLabel)findViewById(R.id.schoolorclass);
        schoolorclssmsg=(BootstrapEditText)findViewById(R.id.schoolorclassmsg);
        myname=(BootstrapEditText)findViewById(R.id.myname);
        update=(BootstrapButton)findViewById(R.id.update);
        if(User.myself.role.equals("教师")){
            schoolorclass.setText("学校名称");
            schoolorclssmsg.setText(User.myself.nowschool);
            if(User.myself.nowschool==null||User.myself.nowschool.equals("")){
                schoolorclssmsg.setText("未设置");
            }
            myname.setText(User.myself.name);
            mynamemsg=User.myself.name;
            myschoolorclassmsg=User.myself.nowschool;
        }else if(User.myself.role.equals("学生")){
            schoolorclass.setText("班级名称");
            schoolorclssmsg.setText(User.myself.bltclass);
            if(User.myself.bltclass==null||User.myself.bltclass.equals("")){
                schoolorclssmsg.setText("未设置");
            }
            myname.setText(User.myself.name);
            if(User.myself.name==null||User.myself.name.equals("")){
                myname.setText("未设置");
            }
            mynamemsg=User.myself.name;
            myschoolorclassmsg=User.myself.bltclass;
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name= myname.getText().toString();
               String sorc= schoolorclssmsg.getText().toString();
                if(name.equals("")||sorc.equals("")){
                    Toast.makeText(ChangeMySelf.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                mynamemsg=name;
                myschoolorclassmsg=sorc;
                JSONObject jsonObject=new JSONObject();
                try {
                    if(User.myself.role.equals("学生")){
                        jsonObject.put("stuId",User.myself.admin);
                        jsonObject.put("stuname",name);
                        jsonObject.put("stuclass",sorc);
                        Net.connect(Net.host+"/szxhcl/changestu",jsonObject.toString(),handler);
                    }
                    else if(User.myself.role.equals("教师")){
                        jsonObject.put("teacherId",User.myself.admin);
                        jsonObject.put("tecname",name);
                        jsonObject.put("bltschool",sorc);
                        Net.connect(Net.host+"/szxhcl/changetec",jsonObject.toString(),handler);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
