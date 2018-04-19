package com.qqjyb.user;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.qqjyb.szxhcl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ClassMsg extends AppCompatActivity {
    static JSONObject classobject;
    ListView stulistview;
    StuAdapter stuAdapter;
    static JSONArray studentArray;
    static String classId;
    BootstrapButton jion;
    BootstrapButton quit;
    BootstrapButton dismiss;

    TextView classname;
    TextView teachername;
    TextView classnumber;
    TextView starttime;

    MyHandler loasclass=new MyHandler("loasclass"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    classobject=new JSONObject(result);

                    classname.setText("课名："+classobject.optString("classname"));
                    teachername.setText("教师："+classobject.optString("tecname"));
                    classnumber.setText("学生人数："+classobject.optString("stunumber"));
                    starttime.setText("成立时间："+classobject.optString("createtime"));
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("classId",classId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Net.connect(Net.host+"/szxhcl/getStubyclassId",jsonObject.toString(),loadStuHandler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ClassMsg.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    MyHandler loadStuHandler=new MyHandler("loadStuHandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONArray stujsonarray=new JSONArray(result);
                    List<JSONObject> stus=T.jsonArrayToList(stujsonarray);
                    JSONObject teacher=new JSONObject();
                    teacher.put("stuname",classobject.optString("tecname"));
                    teacher.put("stuId",classobject.optString("teacherId"));
                    teacher.put("stuclass",classobject.optString("bltschool"));
                    stus.add(0,teacher);
                    studentArray=T.listToJsonArray(stus);
                    stuAdapter=new StuAdapter(R.layout.stuitem,studentArray);
                    stulistview.setAdapter(stuAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ClassMsg.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    MyHandler jionhandler=new MyHandler("jionhandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(ClassMsg.this,"操作成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ClassMsg.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ClassMsg.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    MyHandler quihandler=new MyHandler("quihandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;

                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(ClassMsg.this,"操作成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ClassMsg.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ClassMsg.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    MyHandler dismisshandler=new MyHandler("dismisshandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(ClassMsg.this,"操作成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ClassMsg.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(ClassMsg.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_msg);
        stulistview=(ListView)findViewById(R.id.stuListview);
        jion=(BootstrapButton)findViewById(R.id.jion);
        quit=(BootstrapButton)findViewById(R.id.quit);
        dismiss=(BootstrapButton)findViewById(R.id.dismiss);
        classname=(TextView)findViewById(R.id.classname);
        teachername=(TextView)findViewById(R.id.teachername);
        classnumber=(TextView)findViewById(R.id.classnumber);
        starttime=(TextView)findViewById(R.id.starttime);
        if(User.myself.role.equals("教师")){
            quit.setVisibility(View.GONE);
            jion.setVisibility(View.GONE);
        }if(User.myself.role.equals("学生")){
            dismiss.setVisibility(View.GONE);
        }
        final Intent intent=getIntent();
        classId=intent.getStringExtra("classId");
        JSONObject load=new JSONObject();
        try {
            load.put("classId",classId);
            Net.connect(Net.host+"/szxhcl/getclassandTeacherbyclassId",load.toString(),loasclass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        stulistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(ClassMsg.this,ScoreList.class);
                try {
                    intent1.putExtra("stuId",studentArray.getJSONObject(position).optString("stuId"));
                    intent1.putExtra("position",position);
                    intent1.putExtra("classId",classId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent1);
            }
        });
        jion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!User.myself.role.equals("学生")){
                    Toast.makeText(ClassMsg.this,"您无权进行此操作",Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("classId",classId);
                    jsonObject.put("stuId",User.myself.admin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Net.connect(Net.host+"/szxhcl/jointo",jsonObject.toString(),jionhandler);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!User.myself.role.equals("学生")){
                    Toast.makeText(ClassMsg.this,"您无权进行此操作",Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("classId",classId);
                    jsonObject.put("stuId",User.myself.admin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Net.connect(Net.host+"/szxhcl/tuichu",jsonObject.toString(),quihandler);
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!User.myself.role.equals("教师")){
                    Toast.makeText(ClassMsg.this,"您无权进行此操作",Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("classId",classId);
                    jsonObject.put("userId",User.myself.admin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Net.connect(Net.host+"/szxhcl/dismiss",jsonObject.toString(),dismisshandler);
            }
        });


    }
}
