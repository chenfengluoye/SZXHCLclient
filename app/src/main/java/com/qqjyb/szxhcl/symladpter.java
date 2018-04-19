package com.qqjyb.szxhcl;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenkaiju on 2017/8/23.
 */

public class symladpter extends ArrayAdapter {
    int resource;
    List<symlitem> symlitemList=new ArrayList<>();
    public symladpter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        symlitemList=objects;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        TextView syorder=(TextView)view.findViewById(R.id.syorder);
        TextView syname=(TextView)view.findViewById(R.id.syname);
        syorder.setText(symlitemList.get(position).syorder);
        syname.setText(symlitemList.get(position).syname);
        return view;
    }
}
