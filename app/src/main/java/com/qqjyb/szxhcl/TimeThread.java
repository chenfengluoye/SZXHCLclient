package com.qqjyb.szxhcl;

import android.os.Handler;
import android.os.Message;

/**
 * Created by chenkaiju on 2017/11/15.
 */

public class TimeThread extends Thread {
    Handler handler;
    TimeThread(Handler handler){
        this.handler=handler;
    }
    @Override
    public void run() {
        while (true){
            handler.sendMessage(new Message());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
