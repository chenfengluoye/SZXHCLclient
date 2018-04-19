package com.qqjyb.user;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.qqjyb.szxhcl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateClass extends AppCompatActivity {
    BootstrapProgressBar progressBar;
    BootstrapButton create;
    BootstrapButton cancel;
    EditText classname;
    EditText classId;
    MyHandler createClssHandler=new MyHandler("createClssHandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            progressBar.setVisibility(View.GONE);
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.optBoolean("re")){
                        Toast.makeText(CreateClass.this,"创建成功,到我的班级查看详情",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(CreateClass.this,"创建失败，"+object.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(CreateClass.this,"网络错误",Toast.LENGTH_SHORT).show();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        progressBar=(BootstrapProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        create=(BootstrapButton)findViewById(R.id.create);
        cancel=(BootstrapButton)findViewById(R.id.cancel);
        classname=(EditText)findViewById(R.id.classname);
        classId=(EditText)findViewById(R.id.classId);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namemsg=classname.getText().toString();
                String idmsg=classId.getText().toString();
                if(namemsg.equals("")||idmsg.equals("")){
                    Toast.makeText(CreateClass.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!User.myself.role.equals("教师")){
                    Toast.makeText(CreateClass.this,"只有教师才能进行此操作",Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject object=new JSONObject();
                try {
                    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                    String time=df.format(new Date());
                    object.put("teacherId",User.myself.admin);
                    object.put("classId",idmsg);
                    object.put("classname",namemsg);
                    object.put("createtime",time);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.VISIBLE);
                Net.connect(Net.host+"/szxhcl/create",object.toString(),createClssHandler);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
