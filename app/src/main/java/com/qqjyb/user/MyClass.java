package com.qqjyb.user;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.qqjyb.szxhcl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyClass extends AppCompatActivity {
    BootstrapProgressBar progressBar;
    ListView myclasslistview;
    static JSONArray myclassarray;
    ClassAdapter classAdapter;
    MyHandler getMyclassHandler=new MyHandler("getMyclassHandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            progressBar.setVisibility(View.GONE);
            if(code==1){
                String result= (String) msg.obj;
                try {
                    myclassarray=new JSONArray(result);
                    classAdapter=new ClassAdapter(R.layout.classitem,myclassarray);
                    myclasslistview.setAdapter(classAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(MyClass.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        progressBar=(BootstrapProgressBar)findViewById(R.id.progress);
        myclasslistview=(ListView)findViewById(R.id.myclasslist);
        myclasslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyClass.this,ClassMsg.class);
                try {
                    intent.putExtra("form","MyClass");
                    intent.putExtra("selectclass",myclassarray.getJSONObject(position).toString());
                    intent.putExtra("classId",myclassarray.getJSONObject(position).optString("classId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        reflash();
    }

    public void reflash(){
        JSONObject jsonObject=new JSONObject();
        progressBar.setVisibility(View.VISIBLE);
        try {
            jsonObject.put("userId",User.myself.admin);
            Net.connect(Net.host+"/szxhcl/getclassbyadmin",jsonObject.toString(),getMyclassHandler);
        } catch (JSONException e) {
            progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }
}
