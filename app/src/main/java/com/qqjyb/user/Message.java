package com.qqjyb.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.qqjyb.szxhcl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message extends AppCompatActivity implements View.OnClickListener {

    ListView msglistview;
    JSONArray msgarray;
    MsgAdapter msgAdapter;
    EditText msgcontent;
    String classId;
    String stuId;
    BootstrapButton leavemsg;
    MyHandler leavemsghandler=new MyHandler("leavemsghandler"){
        @Override
        public void handleMessage(android.os.Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    JSONObject re=new JSONObject(result);
                    if(re.optBoolean("re")){
                        Toast.makeText(Message.this,"操作成功",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Message.this,"操作失败，"+re.optString("why"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(Message.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    MyHandler loadleavemsghandler=new MyHandler("loadleavemsghandler"){
        @Override
        public void handleMessage(android.os.Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    msgarray= new JSONArray(result);
                    msgAdapter=new MsgAdapter(msgarray);
                    msglistview.setAdapter(msgAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(Message.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        msglistview=(ListView)findViewById(R.id.msglistview);
        msgcontent=(EditText)findViewById(R.id.msgcontent);
        leavemsg=(BootstrapButton)findViewById(R.id.leavemsg);
        classId=getIntent().getStringExtra("classId");
        if(classId==null){
            classId=ClassMsg.classId;
        }
        leavemsg.setOnClickListener(this);
        msgarray=new JSONArray();
        msgAdapter=new MsgAdapter(msgarray);
        msglistview.setAdapter(msgAdapter);
        stuId=getIntent().getStringExtra("stuIdmsg");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",stuId);
            jsonObject.put("classId",classId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Net.connect(Net.host+"/szxhcl/getleavemsg",jsonObject.toString(),loadleavemsghandler);

    }

    @Override
    public void onClick(View v) {
        JSONObject msg=new JSONObject();
        String msgcontentmsg=msgcontent.getText().toString();
        if(msgcontentmsg.equals("")){
            Toast.makeText(Message.this,"请输入留言信息",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String time=f.format(new Date());
            msg.put("msgcontent",msgcontentmsg);
            msg.put("fromUserRole",User.myself.role);
            msg.put("msgtime",time);
            msg.put("fromUserId",User.myself.admin);
            msg.put("touserId",stuId);
            msg.put("fromUserName",User.myself.name);
            msg.put("bltclassId",classId);
            msgarray.put(msg);
            Net.connect(Net.host+"/szxhcl/addleavemsg",msg.toString(),leavemsghandler);
            msgAdapter.notifyDataSetChanged();
            msgcontent.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
