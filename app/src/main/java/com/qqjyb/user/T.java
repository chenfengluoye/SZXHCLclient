package com.qqjyb.user;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengkaiju on 2018/4/11.
 */

public class T {
    public static JSONArray listToJsonArray(List list){
        JSONArray array=new JSONArray();
        int size=list.size();
        for(int i=0;i<size;i++){
            array.put(list.get(i));
        }
        return array;
    }

    public static List jsonArrayToList(JSONArray list){
        List array=new ArrayList();
        int size=list.length();
        try {
            for(int i=0;i<size;i++){
                array.add(list.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
}
