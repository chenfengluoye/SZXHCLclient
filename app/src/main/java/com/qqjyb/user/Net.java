package com.qqjyb.user;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by chengkaiju on 2018/4/4.
 */

public class Net {

//   public static String host="http://ckjchen.free.ngrok.cc";
   public static String host="http://39.106.34.156:8080";
//    public static String host="http://10.14.4.138:8080";
//    public static String host="http://192.168.43.238:8080";

    //连接网络，发送数据，接收数据
   public static void connect(final String strurl, final String msg,final Handler handler){
       new Thread(new Runnable() {
           @Override
           public void run() {
               String res="";
               try {
                   URL url = new URL(strurl);
                   HttpURLConnection con=(HttpURLConnection) url.openConnection();
                   con.setDoInput(true);
                   con.setDoOutput(true);
                   con.setRequestMethod("POST");
                   if(msg!=null){
                       OutputStream out=con.getOutputStream();
                       BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
                       writer.write(msg);
                       writer.flush();
                   }
                   InputStream in=con.getInputStream();
                   BufferedReader reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
                   StringBuilder builder=new StringBuilder();
                   String line=null;
                   while ((line=reader.readLine())!=null){
                       builder.append(line);
                   }
                   res = builder.toString();
                   if(handler!=null){
                       Message message=new Message();
                       message.what=1;
                       message.obj=res;
                       handler.sendMessage(message);
                   }
               } catch (Exception e) {
                   if(handler!=null){
                       Message message=new Message();
                       message.what=0;
                       handler.sendMessage(message);
                   }
                   e.printStackTrace();
               }
           }
       }
       ).start();
    }


    //连接网络，发送数据，接收数据
    public static void connect(final String strurl, final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(strurl);
                    HttpURLConnection con=(HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    if(msg!=null){
                        OutputStream out=con.getOutputStream();
                        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
                        writer.write(msg);
                        writer.flush();
                    }
                    con.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }
}
