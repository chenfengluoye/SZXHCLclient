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

public class MsgAdapter extends BaseAdapter {

    JSONArray msgarray;

    MsgAdapter(JSONArray array){
        this.msgarray=array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msgitem,null);
        BootstrapLabel fromUserRole= (BootstrapLabel) view.findViewById(R.id.fromUserRole);
        TextView fromUserName=(TextView)view.findViewById(R.id.fromUserName);
        TextView fromUserId=(TextView)view.findViewById(R.id.fromUserId);
        TextView msgcontent=(TextView)view.findViewById(R.id.msgcontent);
        TextView msgtime=(TextView)view.findViewById(R.id.msgtime);
        try {
            JSONObject jsonObject=msgarray.getJSONObject(position);
            fromUserRole.setText(jsonObject.optString("fromUserRole"));
            fromUserName.setText(jsonObject.optString("fromUserName"));
            fromUserId.setText(jsonObject.optString("fromUserId"));
            msgcontent.setText(jsonObject.optString("msgcontent"));
            msgtime.setText(jsonObject.optString("msgtime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public int getCount() {
        return msgarray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        JSONObject object= null;
        try {
            object = msgarray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
