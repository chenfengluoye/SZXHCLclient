package com.qqjyb.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qqjyb.szxhcl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClassAdapter extends BaseAdapter {

    int resource;
    JSONArray array;

    public  ClassAdapter(int resource, JSONArray array){
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
                TextView classname=(TextView)view.findViewById(R.id.classname);
                classname.setText(array.getJSONObject(position).optString("classname"));
                TextView classId=(TextView)view.findViewById(R.id.classId);
                classId.setText(array.getJSONObject(position).optString("classId"));
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
