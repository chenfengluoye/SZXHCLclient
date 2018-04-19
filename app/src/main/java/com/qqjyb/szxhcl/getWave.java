package com.qqjyb.szxhcl;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;

public class getWave {
    static  String FILE_NAME = "MainMicRecord.wav";
    static  int SAMPLE_RATE = 44100;//Hz，采样频率，需要由实验人员设定
    static  double jgtime=2.2675737E-5;//间隔时间
    static  int Numb=256;//采样点数，必须为2的整数次幂
    static  double alltime=0.0464399094;//采样时间
    static  double frequecerate=100;//频率分别率，需要由实验人员设定
    static File mSampleFile;
    static int bufferSize=0;
    static boolean isrecoding=false;
    static AudioRecord mAudioRecord;
    BaseVoiceActivity e;
    getWave(BaseVoiceActivity context){
       e=context;
    }
    public void startRecord() {
        Calendar c = Calendar.getInstance();
        if(isrecoding)
            return;
        isrecoding=true;
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);
        try {
            mSampleFile = new File(Environment.getExternalStorageDirectory(),"MainMicRecord.pcm");
            if(mSampleFile.exists()){
                {
                    mSampleFile.delete();
                    mSampleFile.createNewFile();
                }
            }
            else if(!mSampleFile.exists()){
                mSampleFile.createNewFile();
            }
        } catch(IOException g) {
            //文件创建失败
            Message message0=new Message();
            message0.what=0;
            e.recodhandler.sendMessage(message0);
            g.printStackTrace();
        }
        //为了方便，这里只录制单声道
        //如果是双声道，得到的数据是一左一右，注意数据的保存和处理
        try {
            mAudioRecord.startRecording();
        }catch (Exception v){
            v.printStackTrace();
        }
        new Thread(new AudioRecordThread()).start();
    }

      class  AudioRecordThread implements Runnable{
        int x=0;
        @Override
        public void run() {
            //将录音数据写入文件
            short[] audiodata = new short[bufferSize/2];
            int s= audiodata.length;
            DataOutputStream fos = null;
            try {
                double ix=0;
                //录音开始
                data.Xx.removeAll(data.Xx);
                data.Yy.removeAll(data.Yy);
                data.Xxs.removeAll(data.Xxs);
                data.Yys.removeAll(data.Yys);
                fos = new DataOutputStream( new FileOutputStream(mSampleFile));//创建音频流
                int readSize;
                long temptime=System.currentTimeMillis();
                double t=0;
                float ts=0;
                //开始录音
                Message message1=new Message();
                message1.what=1;
                e.recodhandler.sendMessage(message1);
                //初始化设置
                startset();
                while (data.Yy.size()<Numb&&(mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING)){
                    readSize = mAudioRecord.read(audiodata,0,audiodata.length);
                    long v = 0;
                    if(AudioRecord.ERROR_INVALID_OPERATION != readSize){
                        for(int i = 0;i<readSize;i++){
                            v+= audiodata[i]*audiodata[i];
                            fos.writeShort(audiodata[i]);
                            fos.flush();
                        }
                        float mean = v / readSize;
                        float amplitude= (float) Math.sqrt(mean);//实时振幅值
                        long time=System.currentTimeMillis();
                        data.Xxs.add(ts);
                        data.Yys.add(amplitude);
                        ts+=6;
                        Message message2=new Message();
                        message2.what=2;
                        e.recodhandler.sendMessage(message2);
                        if(ts>BaseVoiceActivity.drawView.SW-10){
                            ts=0;
                            data.Xxs.removeAll(data.Xxs);
                            data.Yys.removeAll(data.Yys);
                        }
                        if(time-temptime>=jgtime){
                            temptime=time;
                            data.Xx.add(t);
                            data.Yy.add((double) amplitude);
                            t+=jgtime;
                        }
                    }
                }
                stopRecording();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //在这里release
                mAudioRecord.release();
                //mAudioRecord = null;
            }
        }
    }
    //在这里stop的时候先不要release
    public  void stopRecording() {
        try {
            mAudioRecord.stop();
            isrecoding=false;
            Message message3=new Message();
            message3.what=3;
            e.recodhandler.sendMessage(message3);
        }catch (Exception e){
        }
    }

    public void startset(){

        jgtime=1.0/SAMPLE_RATE;

        alltime=jgtime*Numb;

        frequecerate=1.0/alltime;        //由采样的总时间确定采样点数

    }
}

