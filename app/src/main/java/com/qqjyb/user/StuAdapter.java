package com.qqjyb.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.qqjyb.szxhcl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by chengkaiju on 2018/4/7.
 */

public class StuAdapter extends BaseAdapter {

    int resource;
    JSONArray array;

    StuAdapter(int resource, JSONArray array){
        this.resource=resource;
        this.array=array;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView!=null){
            return convertView;
        }else {
            View view=null;
            try {
                view= LayoutInflater.from(parent.getContext()).inflate(resource,null);
                BootstrapLabel role=(BootstrapLabel)view.findViewById(R.id.role);
                if(position==0){
                    role.setText("教师");
                }else {
                    role.setText("学生");
                }
                TextView stuname=(TextView)view.findViewById(R.id.stuname);
                stuname.setText(array.getJSONObject(position).optString("stuname"));
                TextView stuId=(TextView)view.findViewById(R.id.stuId);
                stuId.setText(array.getJSONObject(position).optString("stuId"));
                TextView bltclassname=(TextView)view.findViewById(R.id.bltclassname);
                bltclassname.setText(array.getJSONObject(position).optString("stuclass"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return array.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
