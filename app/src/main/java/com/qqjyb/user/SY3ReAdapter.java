package com.qqjyb.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by chengkaiju on 2018/4/9.
 */

public class SY3ReAdapter extends BaseAdapter{
    JSONArray array;
    String syId;
    SY3ReAdapter(JSONArray array,String syId){
        this.array=array;
        this.syId=syId;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            try {
                final Context context=parent.getContext();
                final JSONObject jsonObject=array.getJSONObject(position);
                LinearLayout view=new LinearLayout(context);
                view.setOrientation(LinearLayout.VERTICAL);
                Iterator<String> keys=jsonObject.keys();
                while (keys.hasNext()){
                    LinearLayout linear=new LinearLayout(context);
                    String key=keys.next();
                    String value=jsonObject.getString(key);
                    BootstrapLabel label=new BootstrapLabel(context);
                    label.setText(key);
                    TextView textView=new TextView(context);
                    textView.setMaxLines(10);
                    textView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setText(value);
                    linear.addView(label);
                    linear.addView(textView);
                    view.addView(linear);
                }
                LinearLayout linear=new LinearLayout(context);
                BootstrapButton button=new BootstrapButton(context);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20,10,20,10);
                if(jsonObject.optString("isshengyue").equals("yes")){
                    button.setText("已审阅");
                }else {
                    if(ClassMsg.classobject.optString("teacherId").equals(User.myself.admin)){
                        button.setText("审阅");
                    }else {
                        button.setEnabled(false);
                        button.setText("无权审阅");
                    }
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ShengYue.class);
                        intent.putExtra("syId",syId);
                        intent.putExtra("scoreId",jsonObject.optString("scoreId"));
                        intent.putExtra("comment",jsonObject.optString("comment"));
                        intent.putExtra("scoremark",jsonObject.optString("scoremark"));
                        context.startActivity(intent);
                    }
                });
                linear.addView(button,params);
                view.addView(linear);
                return view;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
    }
}

