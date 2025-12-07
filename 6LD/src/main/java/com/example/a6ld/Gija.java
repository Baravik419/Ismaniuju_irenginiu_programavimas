package com.example.a6ld;

import android.util.Log;
import android.widget.TextView;

public class Gija extends Thread {
    TextView textView;
    String result, finalResult;
    MainActivity mainActivity;

    Boolean ifSleep = false, ifFinish = false;

    Gija(String result, TextView textView, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.result = result;
        this.textView = textView;
    }

    public void setToSleep(boolean ifSleep){
        this.ifSleep = ifSleep;
    }

    public void setToFinish(boolean ifSleep){
        this.ifFinish = ifSleep;
    }

    @Override
    public void run() {
        super.run();
        finalResult = "";
        for (int i = 0; i < result.length(); i++) {
            if(ifSleep) {
                ifSleep = false;
                try {
                    Thread.sleep((long)5e3);
                } catch (InterruptedException e) {
                }
            }

            if (ifFinish) {
                ifFinish = true;
                return;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }

            Log.d("EB", "Gijos rezultatas, i = " + i);
            int finalI = i;
            finalResult = finalResult + result.charAt(finalI);
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(finalResult);
                }
            });
        }
    }
}
