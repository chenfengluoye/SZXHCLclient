package com.qqjyb.user;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.qqjyb.szxhcl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SY3Result extends AppCompatActivity {

    ListView relistView;
    JSONArray syrearray=new JSONArray();
    SY3ReAdapter readapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    syrearray=new JSONArray(result);
                    readapter=new SY3ReAdapter(syrearray,"sy3scores");
                    relistView.setAdapter(readapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(SY3Result.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy1_result);
        relistView=(ListView)findViewById(R.id.reListView);
        reflash();
    }

    public void reflash(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",User.myself.admin);
            jsonObject.put("classId",ClassMsg.classId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Net.connect(Net.host+"/szxhcl/getsy3data",jsonObject.toString(),handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reflash();
    }
}
