package com.qqjyb.szxhcl;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class PlayAudioer {
    AudioTrack audioTrack;
    boolean isPlaying = false;
    int frequency = 44100;
    int channelConfiguration = AudioFormat.CHANNEL_OUT_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    File files= new File(Environment.getExternalStorageDirectory(),"MainMicRecord.pcm");
    BaseVoiceActivity e;
    int SW;
    int SH;
    PlayAudioer (BaseVoiceActivity e){
        this.e=e;
        SW=1000;
        SH=1080;
    }
    public void stopAudio(Handler handler){
        isPlaying=false;
        Message message5=new Message();
        message5.what=5;
        handler.sendMessage(message5);
    }

    public Void playAudio( final Handler handler, final File file)
    {

        isPlaying = true;
        data.Xxss=new ArrayList<>();
        data.Yyss=new ArrayList<>();

        final int bufferSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        final short[] audiodata = new short[bufferSize / 2];

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    DataInputStream dis;
                    try {
                        dis = new DataInputStream(
                                new BufferedInputStream(new FileInputStream(file)));

                    }catch (Exception v){
                        Message message1=new Message();
                        message1.what=1;
                        handler.sendMessage(message1);
                        return;
                    }
                    float z=0;
                    audioTrack = new AudioTrack(
                            AudioManager.STREAM_MUSIC, frequency,
                            channelConfiguration, audioEncoding, bufferSize,
                            AudioTrack.MODE_STREAM);
                    try {
                        audioTrack.play();
                    }catch (Exception g){
                        Message message2=new Message();
                        message2.what=2;
                        handler.sendMessage(message2);
                        return;
                    }
                    Message message6=new Message();
                    message6.what=6;
                    handler.sendMessage(message6);
                    while (isPlaying && dis.available() > 0)
                    {
                        float v=0;
                        int i = 0;
                        while (dis.available() > 0 && i < audiodata.length)
                        {
                            audiodata[i] = dis.readShort();
                            v+=audiodata[i]*audiodata[i];
                            i++;
                        }
                        float readSize=audioTrack.write(audiodata, 0, audiodata.length);
                        float mean = v / readSize;
                        float amplitude= (float) Math.sqrt(mean);//实时振幅值
                        data.Xxss.add(z);
                        z+=6;
                        data.Yyss.add(amplitude);
                        Message message3=new Message();
                        message3.what=3;
                        handler.sendMessage(message3);
                        if(z>BaseVoiceActivity.drawView.SW-10){
                            z=0;
                            data.Xxss=new ArrayList<Float>();
                            data.Yyss=new ArrayList<Float>();
                        }
                    }
                    audioTrack.stop();
                    audioTrack.release();
                    audioTrack=null;
                    dis.close();
                    Message message5=new Message();
                    message5.what=5;
                    handler.sendMessage(message5);

                } catch (Exception t) {
                    Message message4=new Message();
                    message4.what=4;
                    handler.sendMessage(message4);
                }
            }
        }).start();

        return null;
    }

}
