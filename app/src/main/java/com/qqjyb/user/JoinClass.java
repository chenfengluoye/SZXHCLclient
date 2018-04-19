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

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class JoinClass extends AppCompatActivity {
    SearchView search;
    ListView classlistview;
    static JSONArray myclassarray;
    ClassAdapter classAdapter;
    BootstrapProgressBar progressBar;
    MyHandler searchHandler=new MyHandler("searchHandler"){
        @Override
        public void handleMessage(Message msg) {
            int code=msg.what;
            if(code==1){
                String result= (String) msg.obj;
                try {
                    myclassarray=new JSONArray(result);
                    classAdapter=new ClassAdapter(R.layout.classitem,myclassarray);
                    classlistview.setAdapter(classAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(JoinClass.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);
        progressBar=(BootstrapProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        search=(SearchView)findViewById(R.id.seach);
        classlistview=(ListView)findViewById(R.id.classlistview);
        search.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("value",string);
                    Net.connect(Net.host+"/szxhcl/getclassbyvalue",jsonObject.toString(),searchHandler);
                    progressBar.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        });
        search.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }

        });
        classlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JoinClass.this,ClassMsg.class);
                try {
                    intent.putExtra("form","JoinClass");
                    intent.putExtra("selectclass",myclassarray.getJSONObject(position).toString());
                    intent.putExtra("classId",myclassarray.getJSONObject(position).optString("classId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
